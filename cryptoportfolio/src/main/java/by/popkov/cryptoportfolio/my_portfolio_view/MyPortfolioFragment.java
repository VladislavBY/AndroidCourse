package by.popkov.cryptoportfolio.my_portfolio_view;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class MyPortfolioFragment extends Fragment implements AddNewCoinDialogFragment.AddNewCoinDialogListener {
    private MyPortfolioViewModel myPortfolioViewModel;

    @Override
    public void OnPositiveButtonClick(String symbol, Double number) {
        myPortfolioViewModel.saveCoin(symbol, number);

    }

    private void initViewModel() {
        if (getActivity() != null) {
            myPortfolioViewModel = new ViewModelProvider(this, new MyPortfolioViewModelFactory(getActivity().getApplication()))
                    .get(MyPortfolioViewModel.class);
            myPortfolioViewModel.getCoinForViewListLiveData().observe(getViewLifecycleOwner(),
                    coinList -> adaptet.setCoinList(coinList));
        }
    }
}
