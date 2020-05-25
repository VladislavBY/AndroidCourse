package by.popkov.cryptoportfolio.domain;

import androidx.arch.core.util.Function;

import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinEntity;

public class CoinMapper implements Function<CoinEntity, Coin> {
    @Override
    public Coin apply(CoinEntity input) {
        return new Coin.Builder(input.getSymbol())
                .setNumber(input.getNumber())
                .build();
    }
}
