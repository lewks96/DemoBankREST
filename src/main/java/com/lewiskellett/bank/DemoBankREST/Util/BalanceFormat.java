package com.lewiskellett.bank.DemoBankREST.Util;

import java.text.DecimalFormat;

public class BalanceFormat {
    //https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
    private static final DecimalFormat balanceFormat = new DecimalFormat("0.00");

    public static double round(double balance) {
        return Double.parseDouble(balanceFormat.format(balance));
    }
}
