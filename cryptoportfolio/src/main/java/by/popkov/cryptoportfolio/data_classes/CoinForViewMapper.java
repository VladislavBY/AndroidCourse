package by.popkov.cryptoportfolio.data_classes;

import java.util.Locale;
import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.utils.ShortSymbolConverter;

public class CoinForViewMapper implements Function<Coin, CoinForView> {
    private static final String numberFormat = "%.2f";

    @Override
    public CoinForView apply(Coin coin) {
        Locale locale = Locale.getDefault();
        String shortSymbol = ShortSymbolConverter.getShortSymbol(coin.getFiatSymbol());
        CoinForView.Builder builder = new CoinForView.Builder(coin.getSymbol());
        builder.setLogoUrl(coin.getLogoUrl())
                .setFiatSymbol(coin.getFiatSymbol())
                .setPrise(String.format(locale, numberFormat, coin.getPrise()) + shortSymbol)
                .setChangePercent24Hour(String.format(locale, numberFormat, coin.getChangePercent24Hour()) + "%")
                .setChange24Hour(String.format(locale, numberFormat, coin.getChange24Hour()) + shortSymbol)
                .setNumber(String.format(locale, numberFormat, coin.getNumber()))
                .setGlobalSupply(String.format(locale, numberFormat, coin.getGlobalSupply()))
                .setMarketCap(String.format(locale, numberFormat, coin.getMarketCap()) + shortSymbol)
                .setMarket24Volume(String.format(locale, numberFormat, coin.getMarket24Volume()) + shortSymbol);
        if (coin.getNumber() != null && coin.getPrise() != null) {
            builder.setSum(String.format(locale, numberFormat, coin.getNumber() * coin.getPrise()) + shortSymbol);
        } else {
            builder.setSum("null");
        }
        if (coin.getNumber() != null && coin.getChange24Hour() != null) {
            builder.setChangeSum24Hour(String.format(locale, numberFormat, coin.getNumber() * coin.getChange24Hour()) + shortSymbol);
        } else {
            builder.setChangeSum24Hour("null");
        }
        if (coin.getChangePercent24Hour() != null) {
            if (coin.getChangePercent24Hour() > 0) {
                builder.setChange24Color(Colors.COLOR_UP);
            } else if (coin.getChangePercent24Hour() < 0) {
                builder.setChange24Color(Colors.COLOR_DOWN);
            } else if (coin.getChangePercent24Hour() == 0.0) {
                builder.setChange24Color(Colors.COLOR_NEUTRAL);
            }
        }
        return builder.build();
    }
}
