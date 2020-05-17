package by.popkov.cryptoportfolio.repositories.api_repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.Coin;

class ConverterJsonToCoin {
    @Nullable
    static List<Coin> toCoinList(@NonNull List<String> symbols, @NonNull List<Double> numbers, @NonNull String fiatSymbol, @NonNull String json) {
        List<Coin> coinsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject raw = jsonObject.getJSONObject("RAW");
            for (int i = 0; i < symbols.size(); i++) {
                String symbol = symbols.get(i);
                JSONObject jsonCoin = raw.getJSONObject(symbol);
                JSONObject fiat = jsonCoin.getJSONObject(fiatSymbol);
                double price = fiat.getDouble("PRICE");
                Double number = numbers.get(i);
                Coin coin = new Coin.Builder(symbol)
                        .setLogoUrl("https://www.cryptocompare.com" + fiat.getString("IMAGEURL"))
                        .setFiatSymbol(fiatSymbol)
                        .setPrise(price)
                        .setNumber(number)
                        .setChangePercent24Hour(fiat.getDouble("CHANGEPCT24HOUR"))
                        .setChange24Hour(fiat.getDouble("CHANGE24HOUR"))
                        .setSum(number * price)
                        .setGlobalSupply(fiat.getDouble("SUPPLY"))
                        .setMarketCap(fiat.getDouble("MKTCAP"))
                        .setMarket24Volume(fiat.getDouble("TOTALVOLUME24HTO"))
                        .build();
                coinsList.add(coin);
            }
            return coinsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    static Coin toCoin(@NonNull String symbol, @NonNull Double number, @NonNull String fiatSymbol, @NonNull String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject raw = jsonObject.getJSONObject("RAW");
            JSONObject jsonCoin = raw.getJSONObject(symbol);
            JSONObject fiat = jsonCoin.getJSONObject(fiatSymbol);
            double price = fiat.getDouble("PRICE");
            return new Coin.Builder(symbol)
                    .setLogoUrl("https://www.cryptocompare.com" + fiat.getString("IMAGEURL"))
                    .setFiatSymbol(fiatSymbol)
                    .setPrise(price)
                    .setNumber(number)
                    .setChangePercent24Hour(fiat.getDouble("CHANGEPCT24HOUR"))
                    .setChange24Hour(fiat.getDouble("CHANGE24HOUR"))
                    .setSum(number * price)
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
