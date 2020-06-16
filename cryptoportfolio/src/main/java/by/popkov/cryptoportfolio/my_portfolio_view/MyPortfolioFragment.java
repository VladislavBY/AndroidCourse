package by.popkov.cryptoportfolio.my_portfolio_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyPortfolioFragment extends Fragment implements AddNewCoinDialogFragment.AddNewCoinDialogListener,
        MyPortfolioViewModel.ShowThrowable {
    public interface OnSettingsBtnClickListener {
        void onClickSettings();
    }

    public static String TAG = "MyPortfolioFragment";

    private Context context;
    private MyPortfolioViewModel myPortfolioViewModel;
    private RecyclerView coinListRecyclerView;
    private FloatingActionButton addCoinFab;
    private ImageButton settingsImageButton;
    private TextView sumTextView;
    private TextView change24PrsTextView;
    private TextView change24TextView;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private TextView portfolioIsEmpty;

    private Optional<CoinListAdapter.OnCoinListClickListener> onCoinListClickListenerOptional = Optional.empty();
    private Optional<OnSettingsBtnClickListener> onSettingsBtnClickListenerOptional = Optional.empty();
    private Optional<CoinListAdapter> coinListAdapterOptional = Optional.empty();

    @Override
    public void show(@NotNull Throwable throwable) {
        Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        loadSwitcher(false);
    }

    @Override
    public void OnPositiveButtonClick(String symbol, String number) {
        myPortfolioViewModel.saveCoin(symbol, number, this);
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

    private void initRecyclerView(View view) {
        coinListRecyclerView = view.findViewById(R.id.coinListRecyclerView);
        onCoinListClickListenerOptional.ifPresent(onCoinListClickListener ->
                coinListRecyclerView.setAdapter(new CoinListAdapter(onCoinListClickListener)));
        coinListRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        coinListAdapterOptional = Optional.ofNullable((CoinListAdapter) coinListRecyclerView.getAdapter());
    }

    private void initViews(View view) {
        addCoinFab = view.findViewById(R.id.addCoinFab);
        settingsImageButton = view.findViewById(R.id.settingsImageButton);
        sumTextView = view.findViewById(R.id.sumTextView);
        change24PrsTextView = view.findViewById(R.id.change24PrsTextView);
        change24TextView = view.findViewById(R.id.change24TextView);
        portfolioIsEmpty = view.findViewById(R.id.portfolioIsEmpty);
        refreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressBar);
        setBtnListeners();
        setSwipeRefreshLayoutListener();
    }

    private void setBtnListeners() {
        addCoinFab.setOnClickListener(v -> new AddNewCoinDialogFragment().show(getChildFragmentManager(), AddNewCoinDialogFragment.TAG));
        onSettingsBtnClickListenerOptional.ifPresent(onSettingsBtnClickListener ->
                settingsImageButton.setOnClickListener(v -> onSettingsBtnClickListener.onClickSettings()));
    }

    private void setSwipeRefreshLayoutListener() {
        refreshLayout.setOnRefreshListener(() -> {
            myPortfolioViewModel.updateCoinList(this);
            refreshLayout.setRefreshing(false);
            loadSwitcher(true);
        });
    }

    private void initViewModel() {
        if (getActivity() != null) {
            LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
            myPortfolioViewModel = new ViewModelProvider(
                    this, new MyPortfolioViewModelFactory(context))
                    .get(MyPortfolioViewModel.class);
            myPortfolioViewModel.connectToRepo(viewLifecycleOwner, this);
            myPortfolioViewModel.getCoinForViewListLiveData().observe(viewLifecycleOwner, coinForViews -> {
                        coinListAdapterOptional.ifPresent(coinListAdapter -> coinListAdapter.setCoinItemList(coinForViews));
                        portfolioIsEmptyVisibleSwitcher(coinForViews);
                        loadSwitcher(false);
                    }
            );
            myPortfolioViewModel.getPortfolioInfoForViewLiveData().observe(viewLifecycleOwner,
                    this::setPortfolioViewData
            );
        }
    }

    private void setPortfolioViewData(PortfolioInfoForView portfolioInfoForView) {
        sumTextView.setText(portfolioInfoForView.getSum());
        change24PrsTextView.setText(portfolioInfoForView.getChangePercent24Hour());
        change24PrsTextView.setTextColor(portfolioInfoForView.getChange24Color());
        change24TextView.setText(portfolioInfoForView.getChange24Hour());
        change24TextView.setTextColor(portfolioInfoForView.getChange24Color());
    }

    private void portfolioIsEmptyVisibleSwitcher(List<CoinForView> coinForViewList) {
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
