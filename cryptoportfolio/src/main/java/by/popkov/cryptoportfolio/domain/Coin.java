package by.popkov.cryptoportfolio.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Coin {
    @NonNull
    private String id;
    @NonNull
    private String symbol;
    @NonNull
    private Double number;
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
    private Double globalSupply;
    @Nullable
    private Double marketCap;
    @Nullable
    private Double market24Volume;

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    @NonNull
    public Double getNumber() {
        return number;
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
            @NonNull String id,
            @NonNull String symbol,
            @Nullable String logoUrl,
            @Nullable String fiatSymbol,
            @Nullable Double prise,
            @Nullable Double changePercent24Hour,
            @Nullable Double change24Hour,
            @NonNull Double number,
            @Nullable Double globalSupply,
            @Nullable Double marketCap,
            @Nullable Double market24Volume
    ) {
        this.id = id;
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
        private String id;
        @NonNull
        private String symbol;
        @NonNull
        private Double number;
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

        public Builder(@NonNull String id, @NonNull String symbol, @NonNull Double number) {
            this.id = id;
            this.symbol = symbol;
            this.number = number;
        }

        @NonNull
        public Coin build() {
            return new Coin(
                    id,
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
