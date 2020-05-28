package by.popkov.cryptoportfolio.repositories.api_repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;

class ConverterJsonToCoin {
    private ConverterJsonToCoin() {
    }

    static List<Coin> toCoinList(@NonNull List<Coin> rawCoinList, @NonNull String fiatSymbol, @NonNull String json) {
        List<Coin> coinsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject raw = jsonObject.getJSONObject("RAW");
            for (int i = 0; i < rawCoinList.size(); i++) {
                try {
                    String symbol = rawCoinList.get(i).getSymbol();
                    JSONObject jsonCoin = raw.getJSONObject(symbol);
                    JSONObject fiat = jsonCoin.getJSONObject(fiatSymbol);
                    Coin coin = new Coin.Builder(symbol, rawCoinList.get(i).getNumber())
                            .setLogoUrl("https://www.cryptocompare.com" + fiat.getString("IMAGEURL"))
                            .setFiatSymbol(fiatSymbol)
                            .setPrise(fiat.getDouble("PRICE"))
                            .setChangePercent24Hour(fiat.getDouble("CHANGEPCT24HOUR"))
                            .setChange24Hour(fiat.getDouble("CHANGE24HOUR"))
                            .setGlobalSupply(fiat.getDouble("SUPPLY"))
                            .setMarketCap(fiat.getDouble("MKTCAP"))
                            .setMarket24Volume(fiat.getDouble("TOTALVOLUME24HTO"))
                            .build();
                    coinsList.add(coin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return coinsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coinsList;
    }

    @Nullable
    static Coin toCoin(@NonNull Coin rawCoin, @NonNull String fiatSymbol, @NonNull String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject raw = jsonObject.getJSONObject("RAW");
            JSONObject jsonCoin = raw.getJSONObject(rawCoin.getSymbol());
            JSONObject fiat = jsonCoin.getJSONObject(fiatSymbol);
            return new Coin.Builder(rawCoin.getSymbol(), rawCoin.getNumber())
                    .setLogoUrl("https://www.cryptocompare.com" + fiat.getString("IMAGEURL"))
                    .setFiatSymbol(fiatSymbol)
                    .setPrise(fiat.getDouble("PRICE"))
                    .setChangePercent24Hour(fiat.getDouble("CHANGEPCT24HOUR"))
                    .setChange24Hour(fiat.getDouble("CHANGE24HOUR"))
                    .setGlobalSupply(fiat.getDouble("SUPPLY"))
                    .setMarketCap(fiat.getDouble("MKTCAP"))
                    .setMarket24Volume(fiat.getDouble("TOTALVOLUME24HTO"))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
