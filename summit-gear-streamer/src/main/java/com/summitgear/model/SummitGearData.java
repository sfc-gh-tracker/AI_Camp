package com.summitgear.model;

import java.util.*;

public class SummitGearData {

    public static final String[][] STORES = {
        {"STORE-001", "Summit Gear Park City",     "Park City",          "Wasatch North"},
        {"STORE-002", "Summit Gear Draper",         "Draper",             "Salt Lake South"},
        {"STORE-003", "Summit Gear Lehi",           "Lehi",               "Utah Valley"},
        {"STORE-004", "Summit Gear Ogden",          "Ogden",              "Wasatch North"},
        {"STORE-005", "Summit Gear Provo",          "Provo",              "Utah Valley"},
        {"STORE-006", "Summit Gear Sandy",          "Sandy",              "Salt Lake South"},
        {"STORE-007", "Summit Gear SLC Downtown",   "Salt Lake City",     "Salt Lake Metro"},
        {"STORE-008", "Summit Gear Cottonwood",     "Cottonwood Heights", "Salt Lake South"},
        {"STORE-009", "Summit Gear Layton",         "Layton",             "Wasatch North"},
        {"STORE-010", "Summit Gear Orem",           "Orem",               "Utah Valley"},
        {"STORE-011", "Summit Gear Logan",          "Logan",              "Cache Valley"},
        {"STORE-012", "Summit Gear St George",      "St. George",         "Southern Utah"},
    };

    public static final String[] MANAGER_NAMES = {
        "Jake Anderson", "Mia Thompson", "Ethan Rivera", "Chloe Nguyen",
        "Dylan Mortensen", "Alyssa Christensen", "Caleb Jensen", "Hailey Garcia",
        "Brayden Smith", "Kylie Peterson", "Tanner Olsen", "Sierra Johnson"
    };

    // SKU, Name, Category, Subcategory, Brand, MSRP, Cost, Season
    public static final Object[][] PRODUCTS = {
        {"SKU-1001", "Men's Powder Down Jacket",       "Ski",      "Jackets",       "Summit Alpine",    349.99, 140.00, "Winter"},
        {"SKU-1002", "Women's Backcountry Shell",       "Ski",      "Jackets",       "Summit Alpine",    429.99, 172.00, "Winter"},
        {"SKU-1003", "All-Mountain Ski 170cm",          "Ski",      "Skis",          "Wasatch Works",    699.99, 280.00, "Winter"},
        {"SKU-1004", "Freeride Ski Boot",               "Ski",      "Boots",         "Peak Pursuit",     549.99, 220.00, "Winter"},
        {"SKU-1005", "Insulated Ski Gloves",            "Ski",      "Accessories",   "Summit Alpine",     79.99,  32.00, "Winter"},
        {"SKU-1006", "MIPS Ski Helmet",                 "Ski",      "Helmets",       "Peak Pursuit",     199.99,  80.00, "Winter"},
        {"SKU-1007", "Ski Goggle Pro UV",               "Ski",      "Accessories",   "Summit Alpine",    159.99,  64.00, "Winter"},
        {"SKU-1008", "Youth Ski Package",               "Ski",      "Packages",      "Wasatch Works",    399.99, 160.00, "Winter"},
        {"SKU-2001", "Carbon Trail MTB 29er",           "Bike",     "Mountain Bikes","Zion Cycles",     2899.99,1160.00, "Summer"},
        {"SKU-2002", "Enduro Full Suspension",          "Bike",     "Mountain Bikes","Zion Cycles",     3499.99,1400.00, "Summer"},
        {"SKU-2003", "Gravel Adventure Bike",           "Bike",     "Gravel Bikes",  "Zion Cycles",     1899.99, 760.00, "Year-Round"},
        {"SKU-2004", "MTB Clipless Pedals",             "Bike",     "Components",    "Peak Pursuit",     129.99,  52.00, "Summer"},
        {"SKU-2005", "Full Face MTB Helmet",            "Bike",     "Helmets",       "Peak Pursuit",     249.99, 100.00, "Summer"},
        {"SKU-2006", "Bike Repair Multi-Tool",          "Bike",     "Accessories",   "Summit Alpine",     34.99,  14.00, "Year-Round"},
        {"SKU-3001", "Ultralight 55L Pack",             "Hike",     "Backpacks",     "Summit Alpine",    279.99, 112.00, "Summer"},
        {"SKU-3002", "Women's Hiking Boot GTX",         "Hike",     "Footwear",      "Peak Pursuit",     219.99,  88.00, "Year-Round"},
        {"SKU-3003", "Men's Trail Runner",              "Hike",     "Footwear",      "Peak Pursuit",     149.99,  60.00, "Summer"},
        {"SKU-3004", "Trekking Poles Carbon",           "Hike",     "Accessories",   "Summit Alpine",     99.99,  40.00, "Year-Round"},
        {"SKU-3005", "3-Season 2P Tent",                "Hike",     "Shelter",       "Summit Alpine",    349.99, 140.00, "Summer"},
        {"SKU-3006", "Hydration Reservoir 3L",          "Hike",     "Accessories",   "Summit Alpine",     39.99,  16.00, "Year-Round"},
        {"SKU-4001", "Dynamic Climbing Rope 60m",       "Climb",    "Ropes",         "Wasatch Works",    199.99,  80.00, "Year-Round"},
        {"SKU-4002", "Climbing Harness Sport",          "Climb",    "Harnesses",     "Wasatch Works",     89.99,  36.00, "Year-Round"},
        {"SKU-4003", "Quickdraw Set 6-Pack",            "Climb",    "Protection",    "Wasatch Works",     79.99,  32.00, "Year-Round"},
        {"SKU-4004", "Climbing Shoes Performance",      "Climb",    "Footwear",      "Peak Pursuit",     169.99,  68.00, "Year-Round"},
        {"SKU-4005", "Chalk Bag with Belt",             "Climb",    "Accessories",   "Summit Alpine",     24.99,  10.00, "Year-Round"},
        {"SKU-5001", "Trail Running Vest 8L",           "Run",      "Packs",         "Summit Alpine",    139.99,  56.00, "Year-Round"},
        {"SKU-5002", "GPS Running Watch",               "Run",      "Electronics",   "Peak Pursuit",     399.99, 160.00, "Year-Round"},
        {"SKU-5003", "Running Headlamp 500 Lumen",      "Run",      "Accessories",   "Summit Alpine",     49.99,  20.00, "Year-Round"},
        {"SKU-6001", "4-Season Expedition Tent",        "Camp",     "Shelter",       "Summit Alpine",    599.99, 240.00, "Winter"},
        {"SKU-6002", "Down Sleeping Bag 0F",            "Camp",     "Sleep Systems", "Summit Alpine",    449.99, 180.00, "Winter"},
        {"SKU-6003", "Ultralight Sleeping Pad",         "Camp",     "Sleep Systems", "Summit Alpine",    179.99,  72.00, "Year-Round"},
        {"SKU-6004", "Camp Stove Compact",              "Camp",     "Cooking",       "Wasatch Works",     49.99,  20.00, "Year-Round"},
        {"SKU-6005", "Bear Canister",                   "Camp",     "Storage",       "Wasatch Works",     79.99,  32.00, "Summer"},
        {"SKU-6006", "Collapsible Water Filter",        "Camp",     "Hydration",     "Summit Alpine",     44.99,  18.00, "Year-Round"},
    };

    // Intentional typos for messy POS data
    public static final Map<String, String[]> PRODUCT_NAME_TYPOS = new HashMap<>();
    static {
        PRODUCT_NAME_TYPOS.put("SKU-1001", new String[]{"Mens Down Jackt", "Men's Powdr Down Jacket", "Mens Powder Down Jkt"});
        PRODUCT_NAME_TYPOS.put("SKU-1002", new String[]{"Womens Backcountry Shel", "Women Backcountry Shell", "Wmns BC Shell"});
        PRODUCT_NAME_TYPOS.put("SKU-2001", new String[]{"Carbon Trail MTB 29", "Crbon Trail MTB 29er", "Carbon Trl MTB"});
        PRODUCT_NAME_TYPOS.put("SKU-3001", new String[]{"Ultralight 55L Pak", "UL 55L Pack", "Ultralite 55L Pack"});
        PRODUCT_NAME_TYPOS.put("SKU-4004", new String[]{"Climbing Shoe Perf", "Climb Shoes Performance", "Clmbing Shoes Perf"});
        PRODUCT_NAME_TYPOS.put("SKU-6002", new String[]{"Down Sleeping Bag OF", "Down Sleep Bag 0F", "Dwn Sleeping Bag"});
    }

    public static final String[] PAYMENT_METHODS = {
        "Credit Card", "Debit Card", "Apple Pay", "Cash", "Gift Card"
    };

    public static final String[] ORDER_STATUSES_MESSY = {
        "shipped", "SHIPPED", "Shipped", "ship",
        "delivered", "DELIVERED", "Delivered", "deliverd",
        "processing", "PROCESSING", "Processing", "in process",
        "pending", "PENDING", "Pending",
        "cancelled", "CANCELED", "Cancelled", "canx",
        "returned", "RETURNED"
    };

    public static final String[] COUPON_CODES = {
        null, null, null, null, null, // 50% chance of null
        "SUMMER25", "WELCOME10", "GEAR20", "VIP15", "FLASH30",
        "LABORDAY", "WASATCH10", "TRAILDAYS"
    };

    public static final String[] FIRST_NAMES = {
        "James", "Emma", "Liam", "Olivia", "Noah", "Ava", "Mason", "Sophia",
        "Logan", "Isabella", "Jackson", "Mia", "Aiden", "Charlotte", "Lucas",
        "Amelia", "Carter", "Harper", "Jayden", "Evelyn", "Ethan", "Abigail",
        "Wyatt", "Emily", "Brayden", "Ella", "Grayson", "Scarlett", "Hunter",
        "Grace", "Kai", "Lily", "Dalton", "Zoey", "Tanner", "Riley"
    };

    public static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Anderson", "Thompson", "Garcia", "Martinez",
        "Robinson", "Clark", "Lewis", "Walker", "Hall", "Allen", "Young",
        "King", "Wright", "Lopez", "Hill", "Green", "Adams", "Nelson",
        "Christensen", "Jensen", "Olsen", "Peterson", "Hansen", "Mortensen"
    };

    public static final String[] STREET_NAMES = {
        "Main St", "State St", "Center St", "Highland Dr", "Wasatch Blvd",
        "Fort Union Blvd", "Bangerter Hwy", "Redwood Rd", "700 E", "1300 E",
        "University Ave", "Canyon Rd", "Foothill Dr", "Pioneer Rd", "Temple View Dr"
    };

    public static final String[] CITIES = {
        "Salt Lake City", "Provo", "Draper", "Sandy", "Lehi", "Orem",
        "Park City", "Ogden", "Layton", "Logan", "St. George", "Cottonwood Heights",
        "Midvale", "Murray", "Bountiful", "Heber City", "Springville"
    };

    // Review templates - mix of quality
    public static final String[] POSITIVE_REVIEWS = {
        "Absolutely love this product! Took it up to Brighton and it performed flawlessly in the powder.",
        "Best purchase I've made this season. The quality is outstanding and it fits perfectly.",
        "Used this on the Bonneville Shoreline Trail last weekend - exceeded expectations. Highly recommend.",
        "My go-to gear for weekend adventures in the Wasatch. Five stars all the way.",
        "Incredible value for the price. Better than more expensive brands I've tried at REI.",
        "Perfect for Utah conditions. Handles everything from Alta powder to Moab slickrock.",
        "Bought this for my trip to Zion and it was a game changer. Super lightweight and durable.",
        "The build quality is exceptional. You can tell this was designed by people who actually use it.",
        "Great for early morning runs along the Jordan River Parkway. Very comfortable.",
        "Took it to Snowbird opening day - no complaints. Will buy again."
    };

    public static final String[] NEGATIVE_REVIEWS = {
        "Disappointed. The zipper broke after two uses on the slopes at Snowbasin.",
        "Not worth the price. Fell apart after one trip up Big Cottonwood Canyon.",
        "Sizing is way off - ordered my usual size and it was way too small. Had to return.",
        "Terrible customer service when I tried to exchange. The stitching came undone immediately.",
        "Would not recommend. The waterproofing failed during a rainstorm in the Uintas.",
        "Looks nice but doesn't hold up to actual backcountry use. Stick with a known brand."
    };

    public static final String[] MIXED_REVIEWS = {
        "Decent product for the price. Not premium quality but gets the job done for casual use.",
        "It's okay. The design is good but the materials feel a bit cheap for what you pay.",
        "Works well enough for weekend warriors. Serious athletes might want something higher end.",
        "Good for beginners. I've upgraded since but this was a solid starter piece of gear."
    };

    public static final String[] GARBAGE_REVIEWS = {
        ".",
        "asdf",
        "test review ignore",
        "k",
        "N/A",
        "",
        "my dog ate it"
    };

    private static final Random RAND = new Random();

    public static String randomFrom(String[] arr) {
        return arr[RAND.nextInt(arr.length)];
    }

    public static Object[] randomProduct() {
        return PRODUCTS[RAND.nextInt(PRODUCTS.length)];
    }

    public static String[] randomStore() {
        return STORES[RAND.nextInt(STORES.length)];
    }

    public static String randomEmail(String first, String last) {
        String[] domains = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "icloud.com", "byu.edu", "utah.edu", "usu.edu"};
        return (first.toLowerCase() + "." + last.toLowerCase() + "@" + randomFrom(domains));
    }

    public static String randomAddress() {
        int num = RAND.nextInt(9000) + 100;
        return num + " " + randomFrom(STREET_NAMES) + ", " + randomFrom(CITIES) + ", UT " + (84000 + RAND.nextInt(800));
    }

    public static String messyProductName(String sku, String cleanName) {
        if (RAND.nextDouble() < 0.15 && PRODUCT_NAME_TYPOS.containsKey(sku)) {
            String[] typos = PRODUCT_NAME_TYPOS.get(sku);
            return typos[RAND.nextInt(typos.length)];
        }
        return cleanName;
    }

    public static String randomReview() {
        double r = RAND.nextDouble();
        if (r < 0.45) return randomFrom(POSITIVE_REVIEWS);
        if (r < 0.65) return randomFrom(NEGATIVE_REVIEWS);
        if (r < 0.85) return randomFrom(MIXED_REVIEWS);
        return randomFrom(GARBAGE_REVIEWS);
    }

    public static int reviewStarRating(String review) {
        for (String p : POSITIVE_REVIEWS) if (p.equals(review)) return 4 + RAND.nextInt(2);
        for (String n : NEGATIVE_REVIEWS) if (n.equals(review)) return 1 + RAND.nextInt(2);
        for (String m : MIXED_REVIEWS) if (m.equals(review)) return 3;
        return RAND.nextInt(5) + 1;
    }
}
