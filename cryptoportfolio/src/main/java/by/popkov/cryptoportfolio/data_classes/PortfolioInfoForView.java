package by.popkov.cryptoportfolio.data_classes;

public class PortfolioInfoForView {
    private String sum;
    private String changePercent24Hour;
    private String change24Hour;
    private int change24Color;
    private String fiatSymbol;

    public String getSum() {
        return sum;
    }

    public String getChangePercent24Hour() {
        return changePercent24Hour;
    }

    public String getChange24Hour() {
        return change24Hour;
    }

    public int getChange24Color() {
        return change24Color;
    }

    public String getFiatSymbol() {
        return fiatSymbol;
    }

    public PortfolioInfoForView(String sum, String changePercent24Hour, String change24Hour, int change24Color, String fiatSymbol) {
        this.sum = sum;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
        this.change24Color = change24Color;
        this.fiatSymbol = fiatSymbol;
    }
}
