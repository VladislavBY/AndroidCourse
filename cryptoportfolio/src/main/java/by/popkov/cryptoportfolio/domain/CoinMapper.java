package by.popkov.cryptoportfolio.domain;


import java.util.function.Function;

import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinEntity;

public class CoinMapper implements Function<CoinEntity, Coin> {
    @Override
    public Coin apply(CoinEntity input) {
        return new Coin.Builder(input.getSymbol(), input.getNumber()).build();
    }
}
