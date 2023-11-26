package org.example.config;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Config {
    public static final String CSV_DELIMITER = ",";
    public static final int FX_PRICE_SCALE = 4;
    public static final RoundingMode FX_ROUNDING_MODE = RoundingMode.HALF_UP;

    public static final BigDecimal COMMISSION_BID_PERCENTAGE = new BigDecimal("0.999"); //99.9%
    public static final BigDecimal COMMISSION_ASK_PERCENTAGE = new BigDecimal("1.001"); //100.1%
}
