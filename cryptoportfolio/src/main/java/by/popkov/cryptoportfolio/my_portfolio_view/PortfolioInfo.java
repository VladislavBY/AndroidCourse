package by.popkov.cryptoportfolio.my_portfolio_view;

class PortfolioInfo {
    private Double sum;
    private Double changePercent24Hour;
    private Double change24Hour;
    private int change24Color;

    public Double getSum() {
        return sum;
    }

    public Double getChangePercent24Hour() {
        return changePercent24Hour;
    }

    public Double getChange24Hour() {
        return change24Hour;
    }

    public int getChange24Color() {
        return change24Color;
    }

    public PortfolioInfo(Double sum, Double changePercent24Hour, Double change24Hour, int change24Color) {
        this.sum = sum;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
        this.change24Color = change24Color;
    }
}
