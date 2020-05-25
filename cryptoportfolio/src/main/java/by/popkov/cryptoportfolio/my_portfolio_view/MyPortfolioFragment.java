package by.popkov.cryptoportfolio.my_portfolio_view;

//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
//
//public class MyPortfolioFragment extends Fragment implements AddNewCoinDialogFragment.AddNewCoinDialogListener {
//    private ApiRepository apiRepository = ......;
//    private MyPortfolioViewModel myPortfolioViewModel;
//
//    @Override
//    public void OnPositiveButtonClick(String symbol, Double number) {
//        apiRepository.getCoin(symbol, number, //fiat symbol from preference,
//                coin -> myPortfolioViewModel.setNewCoinLiveData(coin), Throwable::printStackTrace);
//    }
//
//    private void initViewModel() {
//        if (getActivity() != null) {
//            myPortfolioViewModel = ViewModelProvider.AndroidViewModelFactory
//                    .getInstance(getActivity().getApplication())
//                    .create(MyPortfolioViewModel.class);
//            myPortfolioViewModel.getNewCoinLiveData().observe(getViewLifecycleOwner(), coin -> adaptet.addCoin(coin));
//            myPortfolioViewModel.getCoinListLiveData().observe(getViewLifecycleOwner(), coinList -> adaptet.setCoinList(coinList));
//        }
//    }
//}
