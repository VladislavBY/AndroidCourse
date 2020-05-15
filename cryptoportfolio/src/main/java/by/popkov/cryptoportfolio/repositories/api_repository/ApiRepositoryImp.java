package by.popkov.cryptoportfolio.repositories.api_repository;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.Coin;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRepositoryImp implements ApiRepository {
    private static final String API_KEY = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=%s&tsyms=%s";
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public void getCoinsList(List<String> symbols, List<Double> numbers, String fiatSymbol, Consumer<List<Coin>> onSuccess, Consumer<Throwable> onError) {
        final Request request = makeRequest(symbols, fiatSymbol);
        Observable.create((ObservableOnSubscribe<Response>) emitter ->
                emitter.onNext(okHttpClient.newCall(request).execute()))
                .subscribeOn(Schedulers.io())
                .map(Response::body)
                .map(responseBody -> ConverterJsonToCoin.toCoin(symbols, numbers, fiatSymbol, responseBody.string()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coinList -> onSuccess.accept(coinList));

    }

    @Override
    public void getCoin(String symbol, Double number, String fiatSymbol, Consumer<Coin> onSuccess, Consumer<Throwable> onError) {
        ArrayList<String> symbols = new ArrayList<>();
        symbols.add(symbol);
        ArrayList<Double> numbers = new ArrayList<>();
        numbers.add(number);
        symbols.add(symbol);
        Request request = makeRequest(symbols, fiatSymbol);
        Observable.create((ObservableOnSubscribe<Response>) emitter ->
                emitter.onNext(okHttpClient.newCall(request).execute()))
                .subscribeOn(Schedulers.io())
                .map(Response::body)
                .map(responseBody -> ConverterJsonToCoin.toCoin(symbols, numbers, fiatSymbol, responseBody.string()))
                .observeOn(AndroidSchedulers.mainThread())
                .
    }

    private Request makeRequest(List<String> symbols, String fiatSymbol) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < symbols.size(); i++) {
            if (i == 0) {
                stringBuilder.append(symbols.get(i));
            } else {
                stringBuilder.append(",").append(symbols.get(i));
            }
        }
        return new Request.Builder()
                .url(String.format(API_KEY, stringBuilder.toString(), fiatSymbol))
                .build();
    }
}