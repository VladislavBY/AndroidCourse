package by.popkov.cryptoportfolio.repositories.api_repository;

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;
import io.reactivex.rxjava3.functions.Consumer;

public interface ApiRepository {
    void getCoinsList(List<String> symbols, List<Double> numbers, String fiatSymbol, Consumer<List<Coin>>onSuccess, Consumer<Throwable> onError);

    void getCoin(String symbol, Double number, String fiatSymbol, Consumer<Coin> onSuccess, Consumer<Throwable> onError);
}
