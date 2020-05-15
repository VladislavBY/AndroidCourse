package by.popkov.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.text);
        ApiRepository apiRepository = new ApiRepositoryImp();
        apiRepository.getCoin("BTC", 10.0, "RUR", coin -> text.setText(String.format("%s %s %s %s", coin.getSymbol(), coin.getPrise(), coin.getSum(), coin.getChangePercent24Hour())),
                throwable -> Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show());
    }
}
