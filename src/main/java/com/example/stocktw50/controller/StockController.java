package com.example.stocktw50.controller;

import com.example.stocktw50.model.Stock;
import com.example.stocktw50.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin // 若前後端分離，開發時建議加上
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/tw50")
    public List<Stock> getTW50Stocks() {
        return stockService.getTW50Stocks();
    }
}
