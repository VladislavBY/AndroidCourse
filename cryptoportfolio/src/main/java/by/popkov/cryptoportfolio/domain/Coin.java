package by.popkov.cryptoportfolio.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class Coin {
    @NonNull
    private String symbol;
    @Nullable
    private String logoUrl;
    @Nullable
    private String fiatSymbol;
    @Nullable
    private Double prise;
    @Nullable
    private Double changePercent24Hour;
    @Nullable
    private Double change24Hour;
    @Nullable
    private Double number;
    @Nullable
    private Double globalSupply;
    @Nullable
    private Double marketCap;
    @Nullable
    private Double market24Volume;

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
    public Double getPrise() {
        return prise;
    }

    @Nullable
    public Double getChangePercent24Hour() {
        return changePercent24Hour;
    }

    @Nullable
    public Double getNumber() {
        return number;
    }

    @Nullable
    public Double getGlobalSupply() {
        return globalSupply;
    }

    @Nullable
    public Double getMarketCap() {
        return marketCap;
    }

    @Nullable
    public Double getMarket24Volume() {
        return market24Volume;
    }

    @Nullable
    public Double getChange24Hour() {
        return change24Hour;
    }

    private Coin(
            @NonNull String symbol,
            @Nullable String logoUrl,
            @Nullable String fiatSymbol,
            @Nullable Double prise,
            @Nullable Double changePercent24Hour,
            @Nullable Double change24Hour,
            @Nullable Double number,
            @Nullable Double globalSupply,
            @Nullable Double marketCap,
            @Nullable Double market24Volume
    ) {
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.fiatSymbol = fiatSymbol;
        this.prise = prise;
        this.changePercent24Hour = changePercent24Hour;
        this.change24Hour = change24Hour;
        this.number = number;
        this.globalSupply = globalSupply;
        this.marketCap = marketCap;
        this.market24Volume = market24Volume;
    }

    public static class Builder {
        @NonNull
        private String symbol;
        @Nullable
        private String logoUrl;
        @Nullable
        private String fiatSymbol;
        @Nullable
        private Double prise;
        @Nullable
        private Double changePercent24Hour;
        @Nullable
        private Double change24Hour;
        @Nullable
        private Double number;
        @Nullable
        private Double globalSupply;
        @Nullable
        private Double marketCap;
        @Nullable
        private Double market24Volume;

        public Builder setLogoUrl(@Nullable String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Builder setPrise(@Nullable Double prise) {
            this.prise = prise;
            return this;
        }

        public Builder setChangePercent24Hour(@Nullable Double changePercent24Hour) {
            this.changePercent24Hour = changePercent24Hour;
            return this;
        }

        public Builder setNumber(@Nullable Double number) {
            this.number = number;
            return this;
        }

        public Builder setChange24Hour(@Nullable Double change24Hour) {
            this.change24Hour = change24Hour;
            return this;
        }

        public Builder setFiatSymbol(@Nullable String fiatSymbol) {
            this.fiatSymbol = fiatSymbol;
            return this;
        }

        public Builder setGlobalSupply(@Nullable Double globalSupply) {
            this.globalSupply = globalSupply;
            return this;
        }

        public Builder setMarketCap(@Nullable Double marketCap) {
            this.marketCap = marketCap;
            return this;
        }

        public Builder setMarket24Volume(@Nullable Double market24Volume) {
            this.market24Volume = market24Volume;
            return this;
        }

        public Builder(@NotNull String symbol) {
            this.symbol = symbol;
        }

        @NonNull
        public Coin build() {
            return new Coin(
                    symbol,
                    logoUrl,
                    fiatSymbol,
                    prise,
                    changePercent24Hour,
                    change24Hour,
                    number,
                    globalSupply,
                    marketCap,
                    market24Volume
            );
        }
    }
}