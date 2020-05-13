package by.popkov.cryptoportfolio.repositories.api_repository;

import by.popkov.cryptoportfolio.Coin;
import io.reactivex.rxjava3.functions.Consumer;

public interface ApiRepository {
    void getCoin(String symbol, Double number, Consumer<Coin> onSuccess, Consumer<Throwable> onError);
}
