package by.popkov.cryptoportfolio.my_portfolio_viewer;

import androidx.arch.core.util.Function;

import by.popkov.cryptoportfolio.Coin;

public class CoinForViewMapper implements Function<Coin, CoinForView> {
    @Override
    public CoinForView apply(Coin coin) {
        CoinForView.Builder builder = new CoinForView.Builder(coin.getSymbol());
        builder.setLogoUrl(coin.getLogoUrl())
                .setFiatSymbol(coin.getFiatSymbol())
                .setPrise(String.valueOf(coin.getPrise()))
                .setChangePercent24Hour(String.valueOf(coin.getChangePercent24Hour()))
                .setChange24Hour(String.valueOf(coin.getChange24Hour()))
                .setNumber(String.valueOf(coin.getNumber()))
                .setGlobalSupply(String.valueOf(coin.getGlobalSupply()))
                .setMarketCap(String.valueOf(coin.getMarketCap()))
                .setMarket24Volume(String.valueOf(coin.getMarket24Volume()));
        if (coin.getNumber() != null && coin.getPrise() != null) {
            builder.setSum(String.valueOf(coin.getNumber() * coin.getPrise()));
        } else {
            builder.setSum("null");
        }
        if (coin.getChangePercent24Hour() != null) {
            if (coin.getChangePercent24Hour() >= 0) {
                builder.setChange24Color(0xFFDA0303);
            } else builder.setChange24Color(0xFF19DA03);
        }
        return builder.build();
    }
}