package com.summitgear;

import com.snowflake.ingest.streaming.*;
import com.summitgear.generators.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class SummitGearStreamer {

    public static void main(String[] args) throws Exception {
        Properties appProps = new Properties();
        try (InputStream is = SummitGearStreamer.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is == null) throw new FileNotFoundException("application.properties not found on classpath");
            appProps.load(is);
        }

        String profilePath = System.getenv("SNOWFLAKE_PROFILE_PATH");
        if (profilePath == null) {
            profilePath = "profile.json";
        }
        File profileFile = new File(profilePath);
        if (!profileFile.exists()) {
            System.err.println("ERROR: profile.json not found at: " + profileFile.getAbsolutePath());
            System.err.println("Create profile.json with: {\"account\": \"...\", \"user\": \"...\", \"url\": \"...\", \"private_key\": \"...\"}");
            System.err.println("Or set SNOWFLAKE_PROFILE_PATH env var to the file location.");
            System.exit(1);
        }

        // Load profile.json into Properties for the SDK
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        Map<String, String> profileMap = mapper.readValue(profileFile, Map.class);
        Properties sdkProps = new Properties();
        sdkProps.putAll(profileMap);

        String database = appProps.getProperty("snowflake.database", "AI_CAMP");
        String schema = appProps.getProperty("snowflake.schema", "BRONZE");

        long posInterval = Long.parseLong(appProps.getProperty("generator.pos.interval_ms", "400"));
        long ecomInterval = Long.parseLong(appProps.getProperty("generator.ecommerce.interval_ms", "600"));
        long invInterval = Long.parseLong(appProps.getProperty("generator.inventory.interval_ms", "3000"));
        long revInterval = Long.parseLong(appProps.getProperty("generator.reviews.interval_ms", "2000"));

        System.out.println("=== Summit Gear Co. Data Streamer ===");
        System.out.println("Database: " + database + ", Schema: " + schema);
        System.out.println("Profile: " + profileFile.getAbsolutePath());
        System.out.println();

        String posTable = "RAW_POS_TRANSACTIONS";
        String ecomTable = "RAW_ECOMMERCE_ORDERS";
        String invTable = "RAW_INVENTORY_FEED";
        String revTable = "RAW_CUSTOMER_REVIEWS";

        System.out.println("Opening streaming clients...");

        SnowflakeStreamingIngestClient posClient = SnowflakeStreamingIngestClientFactory
            .builder("summit_pos_client", database, schema, posTable + "-STREAMING")
            .setProperties(sdkProps).build();
        SnowflakeStreamingIngestChannel posChan = posClient.openChannel("pos_channel_1").getChannel();

        SnowflakeStreamingIngestClient ecomClient = SnowflakeStreamingIngestClientFactory
            .builder("summit_ecom_client", database, schema, ecomTable + "-STREAMING")
            .setProperties(sdkProps).build();
        SnowflakeStreamingIngestChannel ecomChan = ecomClient.openChannel("ecom_channel_1").getChannel();

        SnowflakeStreamingIngestClient invClient = SnowflakeStreamingIngestClientFactory
            .builder("summit_inv_client", database, schema, invTable + "-STREAMING")
            .setProperties(sdkProps).build();
        SnowflakeStreamingIngestChannel invChan = invClient.openChannel("inv_channel_1").getChannel();

        SnowflakeStreamingIngestClient revClient = SnowflakeStreamingIngestClientFactory
            .builder("summit_rev_client", database, schema, revTable + "-STREAMING")
            .setProperties(sdkProps).build();
        SnowflakeStreamingIngestChannel revChan = revClient.openChannel("rev_channel_1").getChannel();

        System.out.println("All channels open. Starting generators...");
        System.out.println("Press Ctrl+C to stop.\n");

        Thread posThread = new Thread(new POSGenerator(posChan, posInterval), "pos-gen");
        Thread ecomThread = new Thread(new EcommerceGenerator(ecomChan, ecomInterval), "ecom-gen");
        Thread invThread = new Thread(new InventoryGenerator(invChan, invInterval), "inv-gen");
        Thread revThread = new Thread(new ReviewGenerator(revChan, revInterval), "rev-gen");

        posThread.setDaemon(true);
        ecomThread.setDaemon(true);
        invThread.setDaemon(true);
        revThread.setDaemon(true);

        posThread.start();
        ecomThread.start();
        invThread.start();
        revThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down...");
            posThread.interrupt();
            ecomThread.interrupt();
            invThread.interrupt();
            revThread.interrupt();

            try {
                posChan.close(); posClient.close();
                ecomChan.close(); ecomClient.close();
                invChan.close(); invClient.close();
                revChan.close(); revClient.close();
                System.out.println("All channels closed cleanly.");
            } catch (Exception e) {
                System.err.println("Error closing channels: " + e.getMessage());
            }
        }));

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            // exit
        }
    }
}
