package by.popkov.cryptoportfolio.domain;


import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import by.popkov.cryptoportfolio.repositories.database_repository.database.CoinEntity;

public class CoinMapper implements Function<CoinEntity, Coin> {
    @Override
    public Coin apply(@NotNull CoinEntity input) {
        return new Coin.Builder(input.getId(), input.getSymbol(), input.getNumber()).build();
    }
}
