package by.popkov.cryptoportfolio.repositories.data_base_repository.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "coin", indices = {@Index(value = {"symbol"}, unique = true)})
public class CoinEntity {
    @PrimaryKey
    @NonNull
    String symbol;
    Double number;

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    public Double getNumber() {
        return number;
    }

    public CoinEntity(@NonNull String symbol, Double number) {
        this.symbol = symbol;
        this.number = number;
    }
}
