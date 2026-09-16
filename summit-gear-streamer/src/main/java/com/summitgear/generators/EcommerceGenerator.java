package com.summitgear.generators;

import com.snowflake.ingest.streaming.SnowflakeStreamingIngestChannel;
import com.summitgear.model.SummitGearData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class EcommerceGenerator implements Runnable {

    private final SnowflakeStreamingIngestChannel channel;
    private final long intervalMs;
    private final AtomicLong offsetCounter = new AtomicLong(0);
    private final Random rand = new Random();

    public EcommerceGenerator(SnowflakeStreamingIngestChannel channel, long intervalMs) {
        this.channel = channel;
        this.intervalMs = intervalMs;
    }

    @Override
    public void run() {
        System.out.println("[ECOM] Generator started - interval " + intervalMs + "ms");
        long count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Map<String, Object> row = generateRow();
                String offset = String.valueOf(offsetCounter.incrementAndGet());
                channel.appendRow(row, offset);
                count++;
                if (count % 50 == 0) {
                    System.out.println("[ECOM] Streamed " + count + " orders");
                }
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[ECOM] Error: " + e.getMessage());
            }
        }
        System.out.println("[ECOM] Generator stopped after " + count + " orders");
    }

    private Map<String, Object> generateRow() {
        Object[] product = SummitGearData.randomProduct();
        String sku = (String) product[0];
        String cleanName = (String) product[1];
        int quantity = rand.nextInt(3) + 1;
        double unitPrice = (double) product[5];
        double subtotal = Math.round(unitPrice * quantity * 100.0) / 100.0;
        double shippingCost = rand.nextDouble() < 0.3 ? 0.0 : (5.99 + rand.nextInt(10));

        String firstName = SummitGearData.randomFrom(SummitGearData.FIRST_NAMES);
        String lastName = SummitGearData.randomFrom(SummitGearData.LAST_NAMES);

        LocalDateTime orderTime = LocalDateTime.now().minusMinutes(rand.nextInt(2880));
        // Some timestamps miss timezone info, some have it
        String timestamp = rand.nextDouble() < 0.5
            ? orderTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            : orderTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String coupon = SummitGearData.randomFrom(SummitGearData.COUPON_CODES);
        String shippingAddr = SummitGearData.randomAddress();
        // 70% same billing as shipping
        String billingAddr = rand.nextDouble() < 0.7 ? shippingAddr : SummitGearData.randomAddress();

        Map<String, Object> row = new HashMap<>();
        row.put("order_id", "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        row.put("customer_email", SummitGearData.randomEmail(firstName, lastName));
        row.put("product_name", cleanName); // ecommerce uses catalog names
        row.put("sku", sku);
        row.put("quantity", quantity);
        row.put("unit_price", unitPrice);
        row.put("subtotal", subtotal);
        row.put("shipping_cost", shippingCost);
        row.put("coupon_code", coupon);
        row.put("status", SummitGearData.randomFrom(SummitGearData.ORDER_STATUSES_MESSY));
        row.put("shipping_address", shippingAddr);
        row.put("billing_address", billingAddr);
        row.put("order_timestamp", timestamp);
        return row;
    }
}
