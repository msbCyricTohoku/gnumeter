package com.example.gnumetercyric;
import java.util.Locale;

public class Utils {

    private static final long B = 1;
    private static final long KB = B * 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;

    public static String parseSpeed(double bytes, boolean inBits) {
        double value = inBits ? bytes * 8 : bytes;
        if (value < KB) {
            return String.format(Locale.getDefault(), "%.1f " + (inBits ? "b" : "B") + "/s", value);
        } else if (value < MB) {
            return String.format(Locale.getDefault(), "%.1f K" + (inBits ? "b" : "B") + "/s", value / KB);
        } else if (value < GB) {
            return String.format(Locale.getDefault(), "%.1f M" + (inBits ? "b" : "B") + "/s", value / MB);
        } else {
            return String.format(Locale.getDefault(), "%.2f G" + (inBits ? "b" : "B") + "/s", value / GB);
        }
    }


    public static int parseval(double bytes, boolean inBits) {
        double value = inBits ? bytes * 8 : bytes;
        if (value < KB) {
            return (int)value;
                    //Integer.format(Locale.getDefault(), "%.1f " + (inBits ? "b" : "B") + "/s", value);
        } else if (value < MB) {
            return (int)(value); // / KB);
                    //String.format(Locale.getDefault(), "%.1f K" + (inBits ? "b" : "B") + "/s", value / KB);
        } else if (value < GB) {
            return (int)(value); ///MB);
                    //String.format(Locale.getDefault(), "%.1f M" + (inBits ? "b" : "B") + "/s", value / MB);
        } else {
            return (int)(value); ///GB);
                    //String.format(Locale.getDefault(), "%.2f G" + (inBits ? "b" : "B") + "/s", value / GB);
        }
    }

}