package org.example.input;

import org.example.config.Config;
import org.example.model.TickerLastPrice;
import org.example.model.TickerName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    public List<TickerLastPrice> readCsv(String fileName) {
        try {
            List<TickerLastPrice> result = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        TickerLastPrice newTickerLastPrice = getObjectFromLine(line);
                        result.add(newTickerLastPrice);
                    } catch (Exception e) {
                        System.err.println("WARN: " + e.getMessage() + ". Continue for next record...");
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TickerLastPrice getObjectFromLine(String line) throws Exception {
        String[] values = line.split(Config.CSV_DELIMITER);
        if(values.length < 5) {
            throw new Exception("Invalid number of columns");
        }
        TickerLastPrice result = new TickerLastPrice(Long.valueOf(values[0].trim()),
                getTickerName(values[1].trim()),
                new BigDecimal(values[2].trim()),
                new BigDecimal(values[3].trim()),
                values[4].trim());
        validate(result);
        return result;
    }

    private TickerName getTickerName(String feedTicker) {
        switch (feedTicker) {
            case "EUR/USD" : return TickerName.EURUSD;
            case "EUR/JPY" : return TickerName.EURJPY;
            case "GBP/USD" : return TickerName.GPBUSD;
            default : throw new UnsupportedOperationException("Unsupported ticker: " + feedTicker);
        }
    }

    private boolean validate(TickerLastPrice record) {
        if(record.getId() <= -1 ||
                record.getBid().compareTo(record.getAsk()) >= 0 ) {  //ask >= bid
            throw new IllegalArgumentException("Invalid record from feed: " + record);
        }
        return true;
    }
}
