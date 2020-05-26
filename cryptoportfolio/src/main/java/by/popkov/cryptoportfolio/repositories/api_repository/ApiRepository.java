package by.popkov.cryptoportfolio.repositories.api_repository;

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public interface ApiRepository {
    @NonNull Observable<List<Coin>> getCoinsList(List<Coin> rawCoinList, String fiatSymbol);

    @NonNull Observable<Coin> getCoin(Coin rawCoin, String fiatSymbol);
}
