package by.popkov.cryptoportfolio.my_portfolio_viewer;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;

public class MyPortfolioFragment extends Fragment implements AddNewCoinDialogFragment.AddNewCoinDialogListener {
    private ApiRepository apiRepository = ......;
    private MyPortfolioViewModel myPortfolioViewModelImp;

    @Override
    public void OnPositiveButtonClick(String symbol, Double number) {
        apiRepository.getCoin(symbol, number,
                coin -> myPortfolioViewModelImp.setNewCoinLiveData(coin), Throwable::printStackTrace);
    }

    private void initViewModel() {
        if (getActivity() != null) {
            myPortfolioViewModelImp = ViewModelProvider.AndroidViewModelFactory
                    .getInstance(getActivity().getApplication())
                    .create(MyPortfolioViewModel.class);
            myPortfolioViewModelImp.getNewCoinLiveData().observe(getViewLifecycleOwner(), coin -> adaptet.addCoin(coin));
            myPortfolioViewModelImp.getCoinListLiveData().observe(getViewLifecycleOwner(), coinList -> adaptet.setCoinList(coinList));
        }
    }
}