package util;

public final class Dinheiro {

    private Dinheiro() {
    }

    public static long toCentavos(double reais) {
        return Math.round(reais * 100.0);
    }

    public static String format(long cents) {
        long abs = Math.abs(cents);
        return (cents < 0 ? "-R$ " : "R$ ") + (abs / 100) + "," + String.format("%02d", abs % 100);
    }
}
