package by.popkov.cryptoportfolio.repositories.api_repository;

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;
import io.reactivex.rxjava3.functions.Consumer;

public interface ApiRepository {
    void getCoinsList(List<Coin> rawCoinList, String fiatSymbol, Consumer<List<Coin>>onSuccess);

    void getCoin(Coin rawCoin, String fiatSymbol, Consumer<Coin> onSuccess);
}
