package by.popkov.cryptoportfolio.repositories.api_repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.Coin;

public class ConverterJsonToCoin {
    public static List<Coin> toCoin(List<String> symbols, List<Double> numbers, String json) {
        List<Coin> coinsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject raw = jsonObject.getJSONObject("RAW");
            for (int i = 0; i < symbols.size(); i++) {
                String symbol = symbols.get(i);
                JSONObject jsonCoin = raw.getJSONObject(symbol);
                JSONObject usd = jsonCoin.getJSONObject("USD");
                double price = usd.getDouble("PRICE");
                Double number = numbers.get(i);
                Coin coin = new Coin.Builder(symbol)
                        .setLogoUrl("https://www.cryptocompare.com" + usd.getString("IMAGEURL"))
                        .setPrise(price)
                        .setNumber(number)
                        .setChangePercent24Hour(usd.getDouble("CHANGEPCT24HOUR"))
                        .setSum(number * price)
                        .build();
                coinsList.add(coin);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coinsList;
    }
}
