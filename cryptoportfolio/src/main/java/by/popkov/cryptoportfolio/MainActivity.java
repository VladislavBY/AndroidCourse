package by.popkov.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import by.popkov.cryptoportfolio.coin_info_view.CoinInfoFragment;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinForView;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinListAdapter;
import by.popkov.cryptoportfolio.my_portfolio_view.MyPortfolioFragment;

public class MainActivity extends AppCompatActivity implements CoinListAdapter.OnCoinListClickListener,
        OnHomeClickListener {


    @Override
    public void onHomeClick() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClick(CoinForView coinForView) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, CoinInfoFragment.getInstance(coinForView), CoinInfoFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            showMyPortfolioFragment();
        }

    }

    private void showMyPortfolioFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new MyPortfolioFragment(), MyPortfolioFragment.TAG)
                .commit();
    }
}
