package by.popkov.cryptoportfolio.my_portfolio_view;

class PortfolioInfoForView {
    private String sum;
    private String changePercent24Hour;
    private String change24Hour;
    private int change24Color;

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

    PortfolioInfoForView(String sum, String changePercent24Hour, String change24Hour, int change24Color) {
        this.sum = sum;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
        this.change24Color = change24Color;
    }
}
