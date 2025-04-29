package com.example.stocktw50.service;

import com.example.stocktw50.crawler.StockCrawler;
import com.example.stocktw50.model.Stock;
import com.example.stocktw50.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockCrawler stockCrawler;

    @Autowired
    private StockRepository stockRepository;

    // 啟動時立即爬一次
    @PostConstruct
    public void init() {
        updateTW50Stocks();
    }

    // 每小時自動更新一次
    @Scheduled(cron = "0 0 * * * *")
    public void updateTW50Stocks() {
        try {
            List<Stock> stocks = stockCrawler.fetchTW50Stocks();
            stockRepository.saveTW50Stocks(stocks);
            System.out.println("台灣50排行已更新，共 " + stocks.size() + " 檔");
        } catch (Exception e) {
            System.err.println("台灣50排行爬取失敗：" + e.getMessage());
        }
    }

    public List<Stock> getTW50Stocks() {
        return stockRepository.getTW50Stocks();
    }
}
