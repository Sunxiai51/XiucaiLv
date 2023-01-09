package com.sunveee.xiucailv.web.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MoneyUtils
 *
 * @author SunVeee
 * @date 2023/1/9 21:15
 */
public class MoneyUtils {

    public static double fen2yuan(long fen) {
        return new BigDecimal(fen).divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN).doubleValue();
    }

    public static long yuan2fen(double yuan) {
        return new BigDecimal(yuan).multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).longValue();
    }

    public static String yuanToString(double yuan) {
        return String.format("%.2f", yuan);
    }

    public static String fen2yuanString(long fen) {
        return yuanToString(fen2yuan(fen));
    }

}
