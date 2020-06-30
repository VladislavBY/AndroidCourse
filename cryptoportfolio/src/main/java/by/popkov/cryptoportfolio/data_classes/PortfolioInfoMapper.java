package by.popkov.cryptoportfolio.data_classes;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

import by.popkov.cryptoportfolio.domain.Coin;

public class PortfolioInfoMapper implements Function<List<Coin>, PortfolioInfo> {

    @Override
    public PortfolioInfo apply(@NotNull List<Coin> coinList) {
        double sum = 0;
        double change24Hour = 0;
        double changePercent24Hour;
        for (Coin coin : coinList) {
            Double coinPrise = coin.getPrise();
            Double coinNumber = coin.getNumber();
            Double coinChange24Hour = coin.getChange24Hour();
            if (coinPrise != null && coinNumber != null && coinChange24Hour != null) {
                Double coinSum = coinPrise * coinNumber;
                sum += coinSum;
                Double coinSumChange = coinChange24Hour * coinNumber;
                change24Hour += coinSumChange;
            }
        }
        String fiatSymbol;
        if (!coinList.isEmpty()) {
            fiatSymbol = coinList.get(0).getFiatSymbol();
        } else {
            fiatSymbol = null;
        }

        changePercent24Hour = change24Hour / (sum - change24Hour) * 100;
        return new PortfolioInfo(sum, changePercent24Hour, change24Hour, fiatSymbol);
    }
}
