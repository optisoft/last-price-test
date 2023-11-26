package org.example.model;

import java.math.BigDecimal;
import java.time.Instant;

public class TickerLastPrice {
    private volatile long id = -1; //assuming that newer price has always larger id and is greater than -1

    private TickerName tickerName; //on purpose, also available as Map key
    private volatile BigDecimal bid; //… the volatile modifier guarantees that any thread that reads a field will see the most recently written value.” - Joshua Bloch
    private volatile BigDecimal ask;
    private volatile String timestamp; //as text, just for showing on UI. Example 01-06-2020 12:01:01:001

    public TickerLastPrice(TickerName tickerName) {
        this.tickerName = tickerName;
    }

    public TickerLastPrice(long id, TickerName tickerName, BigDecimal bid, BigDecimal ask, String timestamp) {
        this.id = id;
        this.tickerName = tickerName;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    public synchronized boolean updatePrice(long id, BigDecimal bid, BigDecimal ask, String timestamp) { //synchronizing only here, won't need synchronizing on Map level
        if(id > this.id) {
            this.id = id;
            this.bid = bid;
            this.ask = ask;
            this.timestamp = timestamp;
            return true;
        }
        return false; //no actual update
    }

    @Override
    public String toString() {
        return "TickerLastPrice{" +
                "id=" + id +
                ", tickerName=" + tickerName +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public TickerName getTickerName() {
        return tickerName;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
