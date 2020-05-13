package by.popkov.cryptoportfolio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class Coin {
    @NonNull
    private String symbol;
    @Nullable
    private Double prise;
    @Nullable
    private Double number;

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    @Nullable
    public Double getPrise() {
        return prise;
    }

    @Nullable
    public Double getNumber() {
        return number;
    }

    private Coin(@NotNull String symbol, @Nullable Double prise, @Nullable Double number) {
        this.symbol = symbol;
        this.prise = prise;
        this.number = number;
    }

    public static class Builder {
        @NonNull
        private String symbol;
        @Nullable
        private Double prise;
        @Nullable
        private Double number;

        public Builder(@NotNull String symbol) {
            this.symbol = symbol;
        }

        @Nullable
        public Double getPrise() {
            return prise;
        }

        @NonNull
        public Builder setPrise(@Nullable Double prise) {
            this.prise = prise;
            return this;
        }

        @Nullable
        public Double getNumber() {
            return number;
        }

        @NonNull
        public Builder setNumber(@Nullable Double number) {
            this.number = number;
            return this;
        }

        @NonNull
        public Coin build() {
            return new Coin(symbol, prise, number);
        }

    }

}
