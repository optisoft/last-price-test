package org.example;

import java.util.List;
import junit.framework.TestCase;
import org.example.input.CsvReader;
import org.example.model.TickerLastPrice;
import org.example.model.TickerName;
import org.example.service.MarketData;
import org.example.service.MarketDataImpl;
import org.junit.Assert;

public class IntegrationTests extends TestCase {

    public void testBasic()
    {
        CsvReader reader = new CsvReader();
        List<TickerLastPrice> newRecords = reader.readCsv("src/test/resources/testBasic.csv");

        MarketData marketDataService = new MarketDataImpl();
        for(TickerLastPrice record : newRecords) {
            marketDataService.onPriceUpdate(record);
        }

        Assert.assertEquals("1.0989", marketDataService.getLastPrice(TickerName.EURUSD).getBid().toString());
        Assert.assertEquals("1.2012", marketDataService.getLastPrice(TickerName.EURUSD).getAsk().toString());

        Assert.assertEquals("119.4904", marketDataService.getLastPrice(TickerName.EURJPY).getBid().toString());
        Assert.assertEquals("120.0299", marketDataService.getLastPrice(TickerName.EURJPY).getAsk().toString());

        Assert.assertEquals("1.2487", marketDataService.getLastPrice(TickerName.GPBUSD).getBid().toString());
        Assert.assertEquals("1.2574", marketDataService.getLastPrice(TickerName.GPBUSD).getAsk().toString());
    }

    public void testWrongOrder()
    {
        CsvReader reader = new CsvReader();
        List<TickerLastPrice> newRecords = reader.readCsv("src/test/resources/testWrongOrder.csv");

        MarketData marketDataService = new MarketDataImpl();
        for(TickerLastPrice record : newRecords) {
            marketDataService.onPriceUpdate(record);
        }

        Assert.assertEquals("1.0989", marketDataService.getLastPrice(TickerName.EURUSD).getBid().toString());
        Assert.assertEquals("1.2012", marketDataService.getLastPrice(TickerName.EURUSD).getAsk().toString());

        Assert.assertEquals("119.4904", marketDataService.getLastPrice(TickerName.EURJPY).getBid().toString());
        Assert.assertEquals("120.0299", marketDataService.getLastPrice(TickerName.EURJPY).getAsk().toString());

        Assert.assertEquals("1.2487", marketDataService.getLastPrice(TickerName.GPBUSD).getBid().toString());
        Assert.assertEquals("1.2574", marketDataService.getLastPrice(TickerName.GPBUSD).getAsk().toString());
    }

    public void testSameTime()
    {
        CsvReader reader = new CsvReader();
        List<TickerLastPrice> newRecords = reader.readCsv("src/test/resources/testSameTime.csv");

        MarketData marketDataService = new MarketDataImpl();
        for(TickerLastPrice record : newRecords) {
            marketDataService.onPriceUpdate(record);
        }

        Assert.assertEquals("1.0989", marketDataService.getLastPrice(TickerName.EURUSD).getBid().toString());
        Assert.assertEquals("1.2012", marketDataService.getLastPrice(TickerName.EURUSD).getAsk().toString());

        Assert.assertEquals("119.4904", marketDataService.getLastPrice(TickerName.EURJPY).getBid().toString());
        Assert.assertEquals("120.0299", marketDataService.getLastPrice(TickerName.EURJPY).getAsk().toString());

        Assert.assertEquals("1.2487", marketDataService.getLastPrice(TickerName.GPBUSD).getBid().toString());
        Assert.assertEquals("1.2574", marketDataService.getLastPrice(TickerName.GPBUSD).getAsk().toString());
    }

}
