package com.example.stocktw50.model;

public class Stock {
    private int rank;
    private String id;
    private String name;
    private String marketCap;
    private double change;

    public Stock() {}

    public Stock(int rank, String id, String name, String marketCap, double change) {
        this.rank = rank;
        this.id = id;
        this.name = name;
        this.marketCap = marketCap;
        this.change = change;
    }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMarketCap() { return marketCap; }
    public void setMarketCap(String marketCap) { this.marketCap = marketCap; }

    public double getChange() { return change; }
    public void setChange(double change) { this.change = change; }
}
