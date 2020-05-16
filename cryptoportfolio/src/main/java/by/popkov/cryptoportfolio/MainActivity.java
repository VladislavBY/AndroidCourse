package by.popkov.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        showData();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.re);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showData();
            new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
        });
    }

    private void showData() {
        ApiRepository apiRepository = new ApiRepositoryImp();
        apiRepository.getCoin("BTC", 10.0, "USD", coin -> textView.setText(String.format("%s %f %f %f", coin.getSymbol(), coin.getGlobalSupply(), coin.getMarketCap(), coin.getMarket24Volume())),
                throwable -> Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show());
    }
}
