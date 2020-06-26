package by.popkov.cryptoportfolio.repositories.database_repository.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "coin", indices = {@Index(value = {"id"}, unique = true)})
public class CoinEntity {
    @NonNull
    @PrimaryKey
    private String id;
    @NonNull
    private String symbol;
    @NonNull
    private Double number;

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    @NotNull
    public Double getNumber() {
        return number;
    }

    public CoinEntity(@NonNull String id, @NonNull String symbol, @NotNull Double number) {
        this.id = id;
        this.symbol = symbol;
        this.number = number;
    }
}
