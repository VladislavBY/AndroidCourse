package by.popkov.cryptoportfolio.my_portfolio_view;

import java.util.Locale;

class PortfolioInfoForViewConverter {
    private PortfolioInfoForViewConverter() {
    }

    private static final String numberFormat = "%.2f";

    static PortfolioInfoForView convert(PortfolioInfo portfolioInfo) {
        Locale locale = Locale.getDefault();
        int change24Color = 0;
        Double changePercent24Hour = portfolioInfo.getChangePercent24Hour();
        if (changePercent24Hour > 0) {
            change24Color = Colors.COLOR_UP;
        } else if (changePercent24Hour < 0) {
            change24Color = Colors.COLOR_DOWN;
        } else if (changePercent24Hour == 0) {
            change24Color = Colors.COLOR_NEUTRAL;
        }
        String sumForView = String.format(locale, numberFormat, portfolioInfo.getSum());
        String changePercent24HourForView = String.format(locale, numberFormat, portfolioInfo.getChangePercent24Hour()) + "%";
        String change24HourForView = String.format(locale, numberFormat, portfolioInfo.getChange24Hour());
        return new PortfolioInfoForView(sumForView, changePercent24HourForView, change24HourForView, change24Color);
    }
}
