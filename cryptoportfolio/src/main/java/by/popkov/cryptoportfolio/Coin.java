package by.popkov.cryptoportfolio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class Coin {
    @NonNull
    private String symbol;
    @Nullable
    private String logoUrl;
    @Nullable
    private Double prise;
    @Nullable
    private Double changePercent24Hour;
    @Nullable
    private Double number;
    @Nullable
    private Double sum;

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    @Nullable
    public String getLogoUrl() {
        return logoUrl;
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
    public Double getSum() {
        return sum;
    }

    private Coin(
            @NonNull String symbol,
            @Nullable String logoUrl,
            @Nullable Double prise,
            @Nullable Double changePercent24Hour,
            @Nullable Double number,
            @Nullable Double sum
    ) {
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.prise = prise;
        this.changePercent24Hour = changePercent24Hour;
        this.number = number;
        this.sum = sum;
    }

    public static class Builder {
        @NonNull
        private String symbol;
        @Nullable
        private String logoUrl;
        @Nullable
        private Double prise;
        @Nullable
        private Double changePercent24Hour;
        @Nullable
        private Double number;
        @Nullable
        private Double sum;

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

        public Builder setSum(@Nullable Double sum) {
            this.sum = sum;
            return this;
        }

        public Builder(@NotNull String symbol) {
            this.symbol = symbol;
        }

        @NonNull
        public Coin build() {
            return new Coin(symbol, logoUrl, prise, changePercent24Hour, number, sum);
        }

    }

}
