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

import java.util.List;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepositoryImp;

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
        dbTest();

        showData();
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.re);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showData();
            new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
        });
    }

    private void dbTest() {
        DatabaseRepository databaseRepository = new DatabaseRepositoryImp(this);
        LiveData<List<Coin>> coinList = databaseRepository.getCoinList();
        coinList.observe(this, coinList1 -> Toast.makeText(this, coinList1.get(coinList1.size()-1).getSymbol(), Toast.LENGTH_LONG).show());
        databaseRepository.addNewCoin(new Coin.Builder("TEST", 0.0).build());
    }

    private void liveDataTest() {
        btn = findViewById(R.id.btn);
        LiveData<Integer> integerLiveData = Transformations.map(doubleLiveData, Double::intValue);
        integerLiveData.observe(this, integer -> Toast.makeText(this, String.valueOf(integer), Toast.LENGTH_LONG).show());
        btn.setOnClickListener(v -> doubleLiveData.setValue(10.1));
    }

    private void showData() {
    }
}
