package by.popkov.cryptoportfolio.my_portfolio_view;

class PortfolioInfo {
    private Double sum;
    private Double changePercent24Hour;
    private Double change24Hour;

    Double getSum() {
        return sum;
    }

    Double getChangePercent24Hour() {
        return changePercent24Hour;
    }

    Double getChange24Hour() {
        return change24Hour;
    }


    PortfolioInfo(Double sum, Double changePercent24Hour, Double change24Hour) {
        this.sum = sum;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
    }
}
