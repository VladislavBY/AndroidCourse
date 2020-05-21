package by.popkov.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button btn;
    private MutableLiveData<Double> doubleLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        liveDataTest();


        showData();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.re);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showData();
            new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
        });
    }

    private void liveDataTest() {
        btn = findViewById(R.id.btn);
        LiveData<Integer> integerLiveData = Transformations.map(doubleLiveData, Double::intValue);
        integerLiveData.observe(this, integer -> Toast.makeText(this, String.valueOf(integer), Toast.LENGTH_LONG).show());
        btn.setOnClickListener(v -> doubleLiveData.setValue(10.1));
    }

    private void showData() {
        ApiRepository apiRepository = new ApiRepositoryImp();
        apiRepository.getCoin("BTC", 10.0, "USD", coin -> textView.setText(String.format("%s %f %f %f", coin.getSymbol(), coin.getChange24Hour(), coin.getMarketCap(), coin.getMarket24Volume())),
                throwable -> Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show());
    }
}
