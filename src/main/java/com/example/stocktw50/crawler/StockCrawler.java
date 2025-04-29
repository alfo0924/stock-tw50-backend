package com.example.stocktw50.crawler;

import com.example.stocktw50.model.Stock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockCrawler {
    private static final String URL = "https://norway.twsthr.info/StockHoldersTopWeek.aspx?Show=2";

    public List<Stock> fetchTW50Stocks() {
        List<Stock> result = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(URL);
            Thread.sleep(3000);

            WebElement table = driver.findElement(By.id("StockList"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));

            for (int i = 1; i < rows.size(); i++) {
                List<WebElement> tds = rows.get(i).findElements(By.tagName("td"));
                if (tds.size() < 6) continue;

                try {
                    int rank = Integer.parseInt(tds.get(0).getText().trim());
                    String id = tds.get(1).getText().trim();
                    String name = tds.get(2).getText().trim();
                    String marketCap = tds.get(3).getText().trim();
                    String changeStr = tds.get(5).getText().replace(",", "").replace("%", "").trim();
                    double change = 0.0;
                    try {
                        change = Double.parseDouble(changeStr);
                    } catch (Exception e) {
                        // ignore
                    }

                    Stock stock = new Stock();
                    stock.setRank(rank);
                    stock.setId(id);
                    stock.setName(name);
                    stock.setMarketCap(marketCap);
                    stock.setChange(change);

                    result.add(stock);
                } catch (Exception e) {
                    // 格式錯誤略過
                }
            }
        } catch (Exception e) {
            System.err.println("Selenium 抓取失敗: " + e.getMessage());
        } finally {
            driver.quit();
        }
        return result;
    }
}
