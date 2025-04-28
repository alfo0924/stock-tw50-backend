package com.example.stocktw50.repository;

import com.example.stocktw50.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StockRepository {
    private List<Stock> tw50Stocks = new ArrayList<>();

    public List<Stock> getTW50Stocks() {
        return new ArrayList<>(tw50Stocks);
    }

    public void saveTW50Stocks(List<Stock> stocks) {
        this.tw50Stocks = new ArrayList<>(stocks);
    }
}
