package com.example.stocktw50.crawler;

import com.example.stocktw50.model.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockCrawler {
    private static final String URL = "https://norway.twsthr.info/StockHoldersTopWeek.aspx?Show=2";

    public List<Stock> fetchTW50Stocks() throws IOException {
        List<Stock> result = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();

        // 1. 直接抓取 <pre> 區塊，這裡才是主要資料
        Element pre = doc.selectFirst("pre");
        if (pre == null) return result;

        String[] lines = pre.text().split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();
            // 2. 只處理開頭為數字的資料列
            if (!line.matches("^\\d+\\s+.*")) continue;

            // 3. 切割欄位
            String[] tokens = line.split("\\s+");
            if (tokens.length < 15) continue; // 欄位數量不足則跳過

            try {
                int rank = Integer.parseInt(tokens[0]);
                String idAndName = tokens[1];
                String id = idAndName.replaceAll("[^0-9]", "");
                String name = idAndName.replaceAll("[0-9]", "");
                // 依照資料實際欄位調整
                String industry = tokens[2];
                double change = Double.parseDouble(tokens[tokens.length - 1]); // 最後一欄為漲跌
                String price = tokens[tokens.length - 2]; // 倒數第二欄為收盤價

                // 你可以根據需要擴充更多欄位
                Stock stock = new Stock();
                stock.setRank(rank);
                stock.setId(id);
                stock.setName(name);
                stock.setMarketCap(price); // 假設 marketCap 欄位用收盤價暫代
                stock.setChange(change);

                result.add(stock);
            } catch (Exception e) {
                // 若有格式錯誤則略過該行
            }
        }
        return result;
    }
}
