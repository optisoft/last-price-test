package org.example.service;

import org.example.config.Config;
import org.example.model.TickerName;
import org.example.model.TickerLastPrice;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class MarketDataImpl implements MarketData {

    //first rule, data consistency
    //second rule, performance

    //ConcurrentHashMap in order to avoid ConcurrentModificationExceptions during adding or removing elements and is not blocking during reads
    private static final ConcurrentHashMap<TickerName, TickerLastPrice> map = new ConcurrentHashMap<>();

    //initialization via default constructor
    public MarketDataImpl() {
        addNewTicker(TickerName.EURUSD);
        addNewTicker(TickerName.GPBUSD);
        addNewTicker(TickerName.EURJPY);
    }

    public void addNewTicker(TickerName tickerName) {
        map.putIfAbsent(tickerName, new TickerLastPrice(tickerName));
    }

    public TickerLastPrice getLastPrice(TickerName tickerName) {
        TickerLastPrice tickerLastPrice = map.get(tickerName);
        if(tickerLastPrice == null) {
            throw new IllegalArgumentException(tickerName + " not yet available");
        }
        return tickerLastPrice;
    }

    public void onPriceUpdate(TickerLastPrice newTickerLastPrice) {
        TickerLastPrice tickerLastPrice = getLastPrice(newTickerLastPrice.getTickerName());
        BigDecimal newBid = applyCommission(newTickerLastPrice.getBid(), Config.COMMISSION_BID_PERCENTAGE);
        BigDecimal newAsk = applyCommission(newTickerLastPrice.getAsk(), Config.COMMISSION_ASK_PERCENTAGE);
        if(tickerLastPrice.updatePrice(newTickerLastPrice.getId(), newBid, newAsk, newTickerLastPrice.getTimestamp())) {
            System.out.println("Publish to REST new " + tickerLastPrice);
        }
    }

    private BigDecimal applyCommission(BigDecimal price, BigDecimal commissionPercentage) {
        return price.multiply(commissionPercentage).setScale(Config.FX_PRICE_SCALE, Config.FX_ROUNDING_MODE);
    }


}
