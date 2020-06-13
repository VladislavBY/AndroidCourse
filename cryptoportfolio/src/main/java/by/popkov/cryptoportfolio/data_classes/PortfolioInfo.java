package by.popkov.cryptoportfolio.data_classes;

public class PortfolioInfo {
    private Double sum;
    private Double changePercent24Hour;
    private Double change24Hour;
    private String fiatSymbol;

    public Double getSum() {
        return sum;
    }

    public Double getChangePercent24Hour() {
        return changePercent24Hour;
    }

    public Double getChange24Hour() {
        return change24Hour;
    }

    public String getFiatSymbol() {
        return fiatSymbol;
    }

    public PortfolioInfo(Double sum, Double changePercent24Hour, Double change24Hour, String fiatSymbol) {
        this.sum = sum;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
        this.fiatSymbol = fiatSymbol;
    }
}
