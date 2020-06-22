package by.popkov.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import by.popkov.cryptoportfolio.coin_info_fragment.CoinInfoFragment;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.my_portfolio_fragment.CoinListAdapter;
import by.popkov.cryptoportfolio.my_portfolio_fragment.MyPortfolioFragment;
import by.popkov.cryptoportfolio.settings_view.SettingsFragment;

public class MainActivity extends AppCompatActivity implements CoinListAdapter.OnCoinListClickListener,
        OnHomeClickListener, MyPortfolioFragment.OnSettingsBtnClickListener, SettingsFragment.OnUpdateCoinListListener {


    @Override
    public void onHomeClick() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClickSettings() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, SettingsFragment.getInstance(), SettingsFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClick(CoinForView coinForView) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, CoinInfoFragment.getInstance(coinForView), CoinInfoFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUpdateCoinList() {
        Fragment myPortfolioFragment = getSupportFragmentManager().findFragmentByTag(MyPortfolioFragment.TAG);
        if (myPortfolioFragment instanceof MyPortfolioFragment) {
            ((MyPortfolioFragment) myPortfolioFragment).updateCoinList();
        }
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
                .add(R.id.fragmentContainer, MyPortfolioFragment.getInstance(), MyPortfolioFragment.TAG)
                .commit();
    }
}
