package by.popkov.cryptoportfolio.my_portfolio_view;

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;

public class CoinListToPortfolioInfoConverter {
    private CoinListToPortfolioInfoConverter() {
    }

    static public PortfolioInfo convert(List<Coin> coinList) {
        double sum = 0;
        double change24Hour = 0;
        double changePercent24Hour = 0;
        int change24Color = 0;
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
        changePercent24Hour = change24Hour / sum * 100;
        if (changePercent24Hour > 0) {
            change24Color = Colors.COLOR_UP;
        } else if (changePercent24Hour < 0) {
            change24Color = Colors.COLOR_DOWN;
        } else if (changePercent24Hour == 0) {
            change24Color = Colors.COLOR_NEUTRAL;
        }
        return new PortfolioInfo(sum, changePercent24Hour, change24Hour, change24Color);
    }
}
