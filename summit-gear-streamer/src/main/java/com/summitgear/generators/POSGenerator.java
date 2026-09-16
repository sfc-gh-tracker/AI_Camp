package com.summitgear.generators;

import com.snowflake.ingest.streaming.SnowflakeStreamingIngestChannel;
import com.summitgear.model.SummitGearData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class POSGenerator implements Runnable {

    private final SnowflakeStreamingIngestChannel channel;
    private final long intervalMs;
    private final AtomicLong offsetCounter = new AtomicLong(0);
    private final Random rand = new Random();

    private static final DateTimeFormatter[] DATE_FORMATS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"),
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
    };

    public POSGenerator(SnowflakeStreamingIngestChannel channel, long intervalMs) {
        this.channel = channel;
        this.intervalMs = intervalMs;
    }

    @Override
    public void run() {
        System.out.println("[POS] Generator started - interval " + intervalMs + "ms");
        long count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Map<String, Object> row = generateRow();
                String offset = String.valueOf(offsetCounter.incrementAndGet());
                channel.appendRow(row, offset);
                count++;
                if (count % 50 == 0) {
                    System.out.println("[POS] Streamed " + count + " transactions");
                }

                // ~5% chance of duplicate
                if (rand.nextDouble() < 0.05) {
                    channel.appendRow(row, String.valueOf(offsetCounter.incrementAndGet()));
                }

                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[POS] Error: " + e.getMessage());
            }
        }
        System.out.println("[POS] Generator stopped after " + count + " transactions");
    }

    private Map<String, Object> generateRow() {
        Object[] product = SummitGearData.randomProduct();
        String sku = (String) product[0];
        String cleanName = (String) product[1];
        String[] store = SummitGearData.randomStore();
        int quantity = rand.nextInt(3) + 1;
        double unitPrice = (double) product[5];
        // Slight price variation
        unitPrice = Math.round((unitPrice + (rand.nextGaussian() * unitPrice * 0.05)) * 100.0) / 100.0;

        String firstName = SummitGearData.randomFrom(SummitGearData.FIRST_NAMES);
        String lastName = SummitGearData.randomFrom(SummitGearData.LAST_NAMES);

        LocalDateTime txnTime = LocalDateTime.now().minusMinutes(rand.nextInt(1440));
        DateTimeFormatter fmt = DATE_FORMATS[rand.nextInt(DATE_FORMATS.length)];

        Map<String, Object> row = new HashMap<>();
        row.put("transaction_id", "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        row.put("store_id", rand.nextDouble() < 0.03 ? null : store[0]); // 3% null
        row.put("product_name", SummitGearData.messyProductName(sku, cleanName));
        row.put("sku", sku);
        row.put("quantity", quantity);
        row.put("unit_price", unitPrice);
        row.put("total_amount", Math.round(unitPrice * quantity * 100.0) / 100.0);
        row.put("payment_method", SummitGearData.randomFrom(SummitGearData.PAYMENT_METHODS));
        row.put("customer_email", SummitGearData.randomEmail(firstName, lastName));
        row.put("transaction_date", txnTime.format(fmt));
        return row;
    }
}
