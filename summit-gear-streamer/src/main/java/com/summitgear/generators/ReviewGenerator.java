package com.summitgear.generators;

import com.snowflake.ingest.streaming.SnowflakeStreamingIngestChannel;
import com.summitgear.model.SummitGearData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ReviewGenerator implements Runnable {

    private final SnowflakeStreamingIngestChannel channel;
    private final long intervalMs;
    private final AtomicLong offsetCounter = new AtomicLong(0);
    private final Random rand = new Random();

    public ReviewGenerator(SnowflakeStreamingIngestChannel channel, long intervalMs) {
        this.channel = channel;
        this.intervalMs = intervalMs;
    }

    @Override
    public void run() {
        System.out.println("[REV] Generator started - interval " + intervalMs + "ms");
        long count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Map<String, Object> row = generateRow();
                String offset = String.valueOf(offsetCounter.incrementAndGet());
                channel.appendRow(row, offset);
                count++;
                if (count % 25 == 0) {
                    System.out.println("[REV] Streamed " + count + " reviews");
                }
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[REV] Error: " + e.getMessage());
            }
        }
        System.out.println("[REV] Generator stopped after " + count + " reviews");
    }

    private Map<String, Object> generateRow() {
        Object[] product = SummitGearData.randomProduct();
        String sku = (String) product[0];

        String firstName = SummitGearData.randomFrom(SummitGearData.FIRST_NAMES);
        String lastName = SummitGearData.randomFrom(SummitGearData.LAST_NAMES);
        String reviewText = SummitGearData.randomReview();
        int stars = SummitGearData.reviewStarRating(reviewText);

        LocalDate reviewDate = LocalDate.now().minusDays(rand.nextInt(90));
        String dateStr = rand.nextDouble() < 0.2
            ? reviewDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            : reviewDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        Map<String, Object> row = new HashMap<>();
        row.put("review_id", "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        row.put("product_sku", sku);
        row.put("customer_name", firstName + " " + lastName);
        row.put("star_rating", stars);
        row.put("review_text", reviewText);
        row.put("review_date", dateStr);
        return row;
    }
}
