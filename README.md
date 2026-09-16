# Your AI Is Only as Good as Your Data Foundation

## Summit Gear Co. Demo — AI Camp / Qlik Event

**Talk:** 30-minute live demo building a full data foundation from messy streaming data to a governed Cortex Agent.

**Event:** AI Camp / Qlik, October 1, 2026, Holladay, UT

### What's in this repo

| File | Description |
|------|-------------|
| `summit_gear_demo.ipynb` | The full demo notebook — run cells in order to build everything in Snowflake |
| `summit-gear-streamer/` | Java app using Snowpipe Streaming SDK to generate realistic messy retail data |

### Architecture

```
Java Streaming App (Snowpipe Streaming SDK)
     → BRONZE (raw messy data)
     → SILVER (dynamic tables: cleaned, deduped, AI sentiment)
     → GOLD (dynamic tables: store performance, product analytics, inventory health)
     → GOVERNANCE (RBAC, column masking, row access policies)
     → SEMANTIC VIEW + CORTEX AGENT (natural language Q&A)
```

### Quick Start

1. **Clone this repo**
   ```bash
   git clone https://github.com/sfc-gh-tracker/AI_Camp.git
   cd AI_Camp
   ```

2. **Configure the Java streamer**
   ```bash
   cd summit-gear-streamer
   cp profile.json.template profile.json
   # Edit profile.json with your Snowflake account, user, and private key
   ```

3. **Generate an RSA key pair** (if you don't have one)
   ```bash
   openssl genrsa 2048 | openssl pkcs8 -topk8 -inform PEM -out rsa_key.p8 -nocrypt
   openssl rsa -in rsa_key.p8 -pubout -out rsa_key.pub
   # Register the public key in Snowflake:
   # ALTER USER <your_user> SET RSA_PUBLIC_KEY='<contents of rsa_key.pub without headers>';
   ```

4. **Compile the Java app**
   ```bash
   mvn compile
   ```

5. **Run the demo notebook** — open `summit_gear_demo.ipynb` in Snowsight or your preferred notebook environment and run cells in order.

6. **Start the streamer** (before or during the demo)
   ```bash
   mvn exec:java
   ```

### Requirements

- Java 21+
- Maven 3.9+
- Snowflake account with ACCOUNTADMIN access
- RSA key pair registered for your user

### Demo Flow (30 min)

1. **The Mess** — show raw messy data streaming in
2. **Transformation** — dynamic tables clean and enrich (including AI sentiment)
3. **Governance** — role switching shows masking + row filtering live
4. **Semantic View** — the contract between data and AI
5. **Cortex Agent** — natural language Q&A over governed data
6. **Close** — "If you're building AI on ungoverned data, you're building on sand"
