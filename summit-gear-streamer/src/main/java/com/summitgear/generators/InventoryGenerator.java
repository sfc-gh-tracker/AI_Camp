package com.summitgear.generators;

import com.snowflake.ingest.streaming.SnowflakeStreamingIngestChannel;
import com.summitgear.model.SummitGearData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryGenerator implements Runnable {

    private final SnowflakeStreamingIngestChannel channel;
    private final long intervalMs;
    private final AtomicLong offsetCounter = new AtomicLong(0);
    private final Random rand = new Random();

    public InventoryGenerator(SnowflakeStreamingIngestChannel channel, long intervalMs) {
        this.channel = channel;
        this.intervalMs = intervalMs;
    }

    @Override
    public void run() {
        System.out.println("[INV] Generator started - interval " + intervalMs + "ms");
        long count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Each tick sends a batch of inventory snapshots for a random store
                String[] store = SummitGearData.randomStore();
                int productsToReport = 5 + rand.nextInt(10);

                List<Map<String, Object>> rows = new ArrayList<>();
                for (int i = 0; i < productsToReport; i++) {
                    rows.add(generateRow(store));
                }

                for (Map<String, Object> row : rows) {
                    channel.appendRow(row, String.valueOf(offsetCounter.incrementAndGet()));
                }
                count += rows.size();

                if (count % 100 == 0) {
                    System.out.println("[INV] Streamed " + count + " inventory snapshots");
                }
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[INV] Error: " + e.getMessage());
            }
        }
        System.out.println("[INV] Generator stopped after " + count + " snapshots");
    }

    private Map<String, Object> generateRow(String[] store) {
        Object[] product = SummitGearData.randomProduct();
        String sku = (String) product[0];
        String cleanName = (String) product[1];

        int qtyOnHand = rand.nextInt(150) - 5; // allows negative (~3%)
        LocalDate snapshotDate = LocalDate.now().minusDays(rand.nextInt(3));

        // Messy dates: some null, some wrong format
        String dateStr;
        double r = rand.nextDouble();
        if (r < 0.05) {
            dateStr = null; // missing date
        } else if (r < 0.15) {
            dateStr = snapshotDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            dateStr = snapshotDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        // 5% chance SKU doesn't match catalog (orphan)
        if (rand.nextDouble() < 0.05) {
            sku = "SKU-9" + (900 + rand.nextInt(100));
        }

        Map<String, Object> row = new HashMap<>();
        row.put("store_id", store[0]);
        row.put("sku", sku);
        row.put("product_name", cleanName);
        row.put("quantity_on_hand", qtyOnHand);
        row.put("snapshot_date", dateStr);
        return row;
    }
}
