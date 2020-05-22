package by.popkov.cryptoportfolio.my_portfolio_viewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import by.popkov.cryptoportfolio.Coin;

class CoinForView {
    @NonNull
    private String symbol;
    @Nullable
    private String logoUrl;
    @Nullable
    private String fiatSymbol;
    @Nullable
    private String prise;
    @Nullable
    private String changePercent24Hour;
    @Nullable
    private String change24Hour;
    private int change24Color;
    @Nullable
    private String number;
    @Nullable
    private String sum;
    @Nullable
    private String globalSupply;
    @Nullable
    private String marketCap;
    @Nullable
    private String market24Volume;

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    @Nullable
    public String getLogoUrl() {
        return logoUrl;
    }

    @Nullable
    public String getFiatSymbol() {
        return fiatSymbol;
    }

    @Nullable
    public String getPrise() {
        return prise;
    }

    @Nullable
    public String getChangePercent24Hour() {
        return changePercent24Hour;
    }

    @Nullable
    public String getChange24Hour() {
        return change24Hour;
    }

    public int getChange24Color() {
        return change24Color;
    }

    @Nullable
    public String getNumber() {
        return number;
    }

    @Nullable
    public String getSum() {
        return sum;
    }

    @Nullable
    public String getGlobalSupply() {
        return globalSupply;
    }

    @Nullable
    public String getMarketCap() {
        return marketCap;
    }

    @Nullable
    public String getMarket24Volume() {
        return market24Volume;
    }

    public CoinForView(Coin coin) {
        this.symbol = coin.getSymbol();
        this.logoUrl = coin.getLogoUrl();
        this.fiatSymbol = coin.getFiatSymbol();
        this.prise = String.valueOf(coin.getPrise());
        this.changePercent24Hour = String.valueOf(coin.getChangePercent24Hour());
        this.change24Hour = String.valueOf(coin.getChange24Hour());
        this.number = String.valueOf(coin.getNumber());
        this.globalSupply = String.valueOf(coin.getGlobalSupply());
        this.marketCap = String.valueOf(coin.getMarketCap());
        this.market24Volume = String.valueOf(coin.getMarket24Volume());
        if (coin.getNumber() != null && coin.getPrise() != null) {
            sum = String.valueOf(coin.getNumber() * coin.getPrise());
        } else {
            sum = "null";
        }
        if (coin.getChangePercent24Hour() != null) {
            if (coin.getChangePercent24Hour() >= 0) {
                change24Color = 0xFFDA0303;
            } else change24Color = 0xFF19DA03;
        }
    }
}
