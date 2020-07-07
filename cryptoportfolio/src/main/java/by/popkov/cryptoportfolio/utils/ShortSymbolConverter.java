package by.popkov.cryptoportfolio.utils;

import androidx.annotation.NonNull;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;

public class ShortSymbolConverter {

    private static final String USD_SYMBOL_SHORT = "$";
    private static final String EUR_SYMBOL_SHORT = "€";
    private static final String RUB_SYMBOL_SHORT = "₽";
    private static final String GBP_SYMBOL_SHORT = "£";
    private static final String JPY_SYMBOL_SHORT = "¥";
    private static final String KRW_SYMBOL_SHORT = "₩";
    private static final String BYN_SYMBOL_SHORT = "Br";
    private static final String BTC_SYMBOL_SHORT = "Ƀ";
    private static final String ETH_SYMBOL_SHORT = "Ξ";

    public static String getShortSymbol(@NonNull String symbol) {
        if (symbol == null) return "null";
        switch (symbol) {
            case ApiRepositoryImp.USD:
                return USD_SYMBOL_SHORT;
            case ApiRepositoryImp.EUR:
                return EUR_SYMBOL_SHORT;
            case ApiRepositoryImp.RUB:
                return RUB_SYMBOL_SHORT;
            case ApiRepositoryImp.GBP:
                return GBP_SYMBOL_SHORT;
            case ApiRepositoryImp.JPY:
                return JPY_SYMBOL_SHORT;
            case ApiRepositoryImp.KRW:
                return KRW_SYMBOL_SHORT;
            case ApiRepositoryImp.BYN:
                return BYN_SYMBOL_SHORT;
            case ApiRepositoryImp.BTC:
                return BTC_SYMBOL_SHORT;
            case ApiRepositoryImp.ETH:
                return ETH_SYMBOL_SHORT;
        }
        throw new IllegalArgumentException("Unsupported symbol");
    }
}
