package org.example.service;

import org.example.model.TickerLastPrice;
import org.example.model.TickerName;

import java.math.BigDecimal;

public interface MarketData {

    void addNewTicker(TickerName tickerName);
    public TickerLastPrice getLastPrice(TickerName tickerName);
    void onPriceUpdate(TickerLastPrice newTickerLastPrice);

}
