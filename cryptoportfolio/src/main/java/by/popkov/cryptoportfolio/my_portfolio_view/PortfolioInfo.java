package by.popkov.cryptoportfolio.my_portfolio_view;

class PortfolioInfo {
    private Double sum;
    private Double changePercent24Hour;
    private Double change24Hour;
    private String fiatSymbol;

    Double getSum() {
        return sum;
    }

    Double getChangePercent24Hour() {
        return changePercent24Hour;
    }

    Double getChange24Hour() {
        return change24Hour;
    }

    String getFiatSymbol() {
        return fiatSymbol;
    }

    public PortfolioInfo(Double sum, Double changePercent24Hour, Double change24Hour, String fiatSymbol) {
        this.sum = sum;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
        this.fiatSymbol = fiatSymbol;
    }
}
