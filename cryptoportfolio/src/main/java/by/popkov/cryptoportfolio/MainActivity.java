package by.popkov.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import by.popkov.cryptoportfolio.coin_info_fragment.CoinInfoFragment;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.my_portfolio_fragment.CoinListAdapter;
import by.popkov.cryptoportfolio.my_portfolio_fragment.MyPortfolioFragment;
import by.popkov.cryptoportfolio.settings_fragment.SettingsFragment;

import static android.view.animation.AnimationUtils.*;

/**
 * Apps base activity.
 */
public class MainActivity extends AppCompatActivity implements CoinListAdapter.OnCoinListClickListener,
        OnHomeClickListener, MyPortfolioFragment.OnSettingsBtnClickListener, SettingsFragment.OnUpdatePortfolioListener {

    /**
     * Implements {@link OnHomeClickListener} for back navigational btn.
     */
    @Override
    public void onHomeClick() {
        getSupportFragmentManager().popBackStack();
        startAnimScreenChangeBackward();
    }

    /**
     * Implements {@link MyPortfolioFragment.OnSettingsBtnClickListener} for settings navigational btn.
     */
    @Override
    public void onClickSettings() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, SettingsFragment.getInstance(), SettingsFragment.TAG)
                .addToBackStack(null)
                .commit();
        startAnimScreenChangeForward();
    }

    /**
     * Implements {@link CoinListAdapter.OnCoinListClickListener}
     * for click on {@link CoinListAdapter} items navigational btn.
     */
    @Override
    public void onItemClick(CoinForView coinForView) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, CoinInfoFragment.getInstance(coinForView), CoinInfoFragment.TAG)
                .addToBackStack(null)
                .commit();
        startAnimScreenChangeForward();
    }

    /**
     * Implements {@link SettingsFragment.OnUpdatePortfolioListener} for update data in {@link MyPortfolioFragment}.
     */
    @Override
    public void onUpdatePortfolio() {
        Fragment myPortfolioFragment = getSupportFragmentManager().findFragmentByTag(MyPortfolioFragment.TAG);
        if (myPortfolioFragment instanceof MyPortfolioFragment) {
            ((MyPortfolioFragment) myPortfolioFragment).updatePortfolioData();
        }
    }

    private FrameLayout fragmentContainer;

    /**
     * At first Create call {@link #showMyPortfolioFragment()}
     *
     * @param savedInstanceState will be null on app start
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            showMyPortfolioFragment();
        }
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }

    /**
     * Shows main fragment {@link MyPortfolioFragment} with summary portfolio info
     */
    private void showMyPortfolioFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, MyPortfolioFragment.getInstance(), MyPortfolioFragment.TAG)
                .commit();
    }

    /**
     * Starts animation for forward navigation on {@link #fragmentContainer}
     */
    private void startAnimScreenChangeForward() {
        fragmentContainer.startAnimation(loadAnimation(this, R.anim.screen_change_forward));
    }

    /**
     * Starts animation for backward navigation on {@link #fragmentContainer}
     */
    private void startAnimScreenChangeBackward() {
        fragmentContainer.startAnimation(loadAnimation(this, R.anim.screen_change_backward));
    }
}
