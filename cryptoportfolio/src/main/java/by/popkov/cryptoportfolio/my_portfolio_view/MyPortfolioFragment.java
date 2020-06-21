package by.popkov.cryptoportfolio.my_portfolio_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import by.popkov.cryptoportfolio.R;
import by.popkov.cryptoportfolio.data_classes.CoinForView;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoForView;

public class MyPortfolioFragment extends Fragment implements AddNewCoinDialogFragment.AddNewCoinDialogListener {
    public interface OnSettingsBtnClickListener {
        void onClickSettings();
    }

    public static String TAG = "MyPortfolioFragment";

    private Context context;
    private MyPortfolioViewModel myPortfolioViewModel;
    private Optional<CoinListAdapter.OnCoinListClickListener> onCoinListClickListenerOptional = Optional.empty();
    private Optional<OnSettingsBtnClickListener> onSettingsBtnClickListenerOptional = Optional.empty();
    private Optional<CoinListAdapter> coinListAdapterOptional = Optional.empty();

    private RecyclerView coinListRecyclerView;
    private FloatingActionButton addCoinFab;
    private ImageButton settingsImageButton;
    private TextView sumTextView;
    private TextView change24PrsTextView;
    private TextView change24TextView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private TextView portfolioIsEmpty;
    private SearchView searchCoin;


    @NotNull
    public static MyPortfolioFragment getInstance() {
        return new MyPortfolioFragment();
    }

    @Override
    public void OnPositiveButtonClick(String symbol, String number) {
        myPortfolioViewModel.saveCoin(symbol, number);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof CoinListAdapter.OnCoinListClickListener) {
            onCoinListClickListenerOptional = Optional.of((CoinListAdapter.OnCoinListClickListener) context);
        }
        if (context instanceof OnSettingsBtnClickListener) {
            onSettingsBtnClickListenerOptional = Optional.of((OnSettingsBtnClickListener) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_portfolio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        initViews(view);
        initViewModel();
    }

    private void initRecyclerView(@NotNull View view) {
        coinListRecyclerView = view.findViewById(R.id.coinListRecyclerView);
        onCoinListClickListenerOptional.ifPresent(onCoinListClickListener ->
                coinListRecyclerView.setAdapter(new CoinListAdapter(onCoinListClickListener)));
        coinListRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        coinListAdapterOptional = Optional.ofNullable((CoinListAdapter) coinListRecyclerView.getAdapter());
    }

    private void initViews(@NotNull View view) {
        addCoinFab = view.findViewById(R.id.addCoinFab);
        settingsImageButton = view.findViewById(R.id.settingsImageButton);
        sumTextView = view.findViewById(R.id.sumTextView);
        change24PrsTextView = view.findViewById(R.id.change24PrsTextView);
        change24TextView = view.findViewById(R.id.change24TextView);
        portfolioIsEmpty = view.findViewById(R.id.portfolioIsEmpty);
        refreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressBar);
        searchCoin = view.findViewById(R.id.searchCoin);
        setBtnListeners();
        setSwipeRefreshLayoutListener();
        setSearchCoinListener();
    }

    private void setBtnListeners() {
        addCoinFab.setOnClickListener(v -> new AddNewCoinDialogFragment().show(getChildFragmentManager(), AddNewCoinDialogFragment.TAG));
        onSettingsBtnClickListenerOptional.ifPresent(onSettingsBtnClickListener ->
                settingsImageButton.setOnClickListener(v -> onSettingsBtnClickListener.onClickSettings()));
    }

    private void setSwipeRefreshLayoutListener() {
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            updateCoinList();
        });
    }

    public void updateCoinList() {
        myPortfolioViewModel.updateCoinList();
    }

    private void setSearchCoinListener() {
        searchCoin.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myPortfolioViewModel.setValueSearchViewQueryLiveData(newText);
                return false;
            }
        });
    }

    private void initViewModel() {
        if (getActivity() != null) {
            LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
            myPortfolioViewModel = new ViewModelProvider(
                    this, new MyPortfolioViewModelFactory(getActivity().getApplication(), context))
                    .get(MyPortfolioViewModel.class);
            myPortfolioViewModel.getCoinForViewListLiveData().observe(viewLifecycleOwner, coinForViews -> {
                        coinListAdapterOptional.ifPresent(coinListAdapter -> {
                            coinListAdapter.setCoinItemList(coinForViews);
                            coinListAdapter.getFilter().filter(myPortfolioViewModel.getSearchViewQueryViewLiveData().getValue());
                        });
                        portfolioIsEmptyVisibleSwitcher(coinForViews);
                    }
            );
            myPortfolioViewModel.getPortfolioInfoForViewLiveData().observe(viewLifecycleOwner,
                    this::setPortfolioViewData
            );
            myPortfolioViewModel.getSearchViewQueryViewLiveData().observe(viewLifecycleOwner,
                    s -> coinListAdapterOptional.ifPresent(coinListAdapter -> coinListAdapter.getFilter().filter(s)));
            myPortfolioViewModel.getIsLoadingLiveData().observe(viewLifecycleOwner, this::loadSwitcher);
        }
    }

    private void setPortfolioViewData(@NotNull PortfolioInfoForView portfolioInfoForView) {
        sumTextView.setText(portfolioInfoForView.getSum());
        change24PrsTextView.setText(portfolioInfoForView.getChangePercent24Hour());
        change24PrsTextView.setTextColor(portfolioInfoForView.getChange24Color());
        change24TextView.setText(portfolioInfoForView.getChange24Hour());
        change24TextView.setTextColor(portfolioInfoForView.getChange24Color());
    }

    private void portfolioIsEmptyVisibleSwitcher(@NotNull List<CoinForView> coinForViewList) {
        if (coinForViewList.isEmpty()) {
            portfolioIsEmpty.setVisibility(View.VISIBLE);
            sumTextView.setVisibility(View.INVISIBLE);
            change24PrsTextView.setVisibility(View.INVISIBLE);
            change24TextView.setVisibility(View.INVISIBLE);
        } else {
            portfolioIsEmpty.setVisibility(View.INVISIBLE);
            sumTextView.setVisibility(View.VISIBLE);
            change24PrsTextView.setVisibility(View.VISIBLE);
            change24TextView.setVisibility(View.VISIBLE);
        }
    }

    private void loadSwitcher(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCoinListClickListenerOptional = Optional.empty();
        context = null;
    }
}
