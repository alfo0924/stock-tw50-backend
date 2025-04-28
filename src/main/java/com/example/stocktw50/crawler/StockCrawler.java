package com.example.stocktw50.crawler;

import com.example.stocktw50.model.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

        // 解析表格
        Element table = doc.selectFirst("table#StockList");
        if (table == null) return result;

        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) { // skip header
            Element row = rows.get(i);
            Elements tds = row.select("td");
            if (tds.size() < 6) continue;

            int rank = Integer.parseInt(tds.get(0).text().trim());
            String id = tds.get(1).text().trim();
            String name = tds.get(2).text().trim();
            String marketCap = tds.get(3).text().trim();
            String changeStr = tds.get(5).text().replace(",", "").replace("%", "").trim();
            double change = 0.0;
            try {
                change = Double.parseDouble(changeStr);
            } catch (Exception e) {
                // ignore
            }

            result.add(new Stock(rank, id, name, marketCap, change));
        }
        return result;
    }
}
