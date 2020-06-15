package by.popkov.cryptoportfolio.data_classes;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Function;

import by.popkov.cryptoportfolio.utils.ShortSymbolConverter;

public class PortfolioInfoForViewMapper implements Function<PortfolioInfo, PortfolioInfoForView> {
    private static final String numberFormat = "%.2f";

    @Override
    public PortfolioInfoForView apply(@NotNull PortfolioInfo portfolioInfo) {
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
        String fiatSymbol = portfolioInfo.getFiatSymbol();
        String shortSymbol = ShortSymbolConverter.getShortSymbol(fiatSymbol);
        String sumForView = String.format(locale, numberFormat, portfolioInfo.getSum()) + shortSymbol;
        String changePercent24HourForView = String.format(locale, numberFormat, portfolioInfo.getChangePercent24Hour()) + "%";
        String change24HourForView = String.format(locale, numberFormat, portfolioInfo.getChange24Hour()) + shortSymbol;
        return new PortfolioInfoForView(sumForView, changePercent24HourForView, change24HourForView, change24Color, fiatSymbol);
    }
}
