## 後端專案簡介


### 架構圖說明

```plaintext
+-------------------+           RESTful API           +----------------------+
|    前端 (Vue.js)  |  |   後端 (Spring Boot) |
|                   |                                 |                      |
|  - UI/UX設計      |                                 |  - 爬蟲模組           |
|  - 台灣50排行展示  |                                 |  - 股票資料API        |
|  - 股票連結跳轉    |                                 |  - 資料庫存取         |
+-------------------+                                 +----------------------+
         |                                                        |
         |                                                        |
         |------------------- 靜態資源 (Nginx/CDN) ---------------|
```

---


### 後端專案結構（Spring Boot）

```plaintext
stock-tw50-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── stocktw50/
│       │               ├── controller/
│       │               │   └── StockController.java      # 提供API
│       │               ├── service/
│       │               │   └── StockService.java         # 商業邏輯
│       │               ├── crawler/
│       │               │   └── StockCrawler.java         # 爬蟲模組
│       │               ├── model/
│       │               │   └── Stock.java                # 股票資料模型
│       │               ├── repository/
│       │               │   └── StockRepository.java      # 資料存取
│       │               └── StockTw50Application.java     # 啟動入口
│       └── resources/
│           ├── application.yml
│           └── static/
├── pom.xml
└── README.md
```

---




**內容結構與呈現方式**

- 專案以分層架構設計，主要包含以下目錄：
    - `controller/`：負責對外提供 RESTful API，處理前端的請求並回傳資料。
    - `service/`：負責商業邏輯，協調資料爬取、資料處理與資料存取。
    - `crawler/`：專責於從「股市金字塔」網站自動化爬取台灣50排行及股票資訊。
    - `model/`：定義資料結構（如股票資訊）。
    - `repository/`：負責與資料庫溝通，存取股票排行與詳細資料。

**連接方式**

- 前端透過 HTTP 請求（RESTful API）與後端溝通，取得台灣50排行及股票資訊。
- 後端定時或依需求啟動爬蟲，將最新排行資料存入資料庫或快取，並透過 API 提供給前端。
- 點擊股票時，前端直接跳轉到原始金字塔網站的詳細頁面，後端不做轉發。

**採用技術**

- **Spring Boot**：作為主體框架，實現 RESTful API 與服務分層。
- **Java**：主要開發語言。
- **Jsoup/Selenium**：常用於網頁爬蟲的 Java 函式庫，負責解析 HTML 並擷取所需資料。
- **資料庫（如 MySQL/H2）**：儲存爬取到的股票排行與資訊（可選，視需求決定是否快取）。
- **定時任務（Spring Scheduler）**：自動化定期爬取最新資料。



---
