package by.popkov.cryptoportfolio.my_portfolio_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Optional;

import by.popkov.cryptoportfolio.R;

public class MyPortfolioFragment extends Fragment implements AddNewCoinDialogFragment.AddNewCoinDialogListener {
    public static String TAG = "MyPortfolioFragment";
    private Context context;
    private MyPortfolioViewModel myPortfolioViewModel;
    private RecyclerView coinListRecyclerView;
    private FloatingActionButton addCoinFab;
    private TextView sumTextView;
    private TextView change24PrsTextView;
    private TextView change24TextView;

    private Optional<CoinListAdapter.OnCoinListClickListener> onCoinListClickListenerOptional = Optional.empty();
    private Optional<CoinListAdapter> coinListAdapterOptional = Optional.empty();

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

    private void initViews(View view) {
        addCoinFab = view.findViewById(R.id.addCoinFab);
        addCoinFab.setOnClickListener(v -> new AddNewCoinDialogFragment().show(getChildFragmentManager(), AddNewCoinDialogFragment.TAG));
        sumTextView = view.findViewById(R.id.sumTextView);
        change24PrsTextView = view.findViewById(R.id.change24PrsTextView);
        change24TextView = view.findViewById(R.id.change24TextView);
    }

    private void initRecyclerView(View view) {
        coinListRecyclerView = view.findViewById(R.id.coinListRecyclerView);
        onCoinListClickListenerOptional.ifPresent(onCoinListClickListener ->
                coinListRecyclerView.setAdapter(new CoinListAdapter(onCoinListClickListener)));
        coinListRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        coinListAdapterOptional = Optional.ofNullable((CoinListAdapter) coinListRecyclerView.getAdapter());
    }

    private void initViewModel() {
        if (getActivity() != null) {
            LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
            myPortfolioViewModel = new ViewModelProvider(
                    this, new MyPortfolioViewModelFactory(getActivity().getApplication(), context))
                    .get(MyPortfolioViewModel.class);
            myPortfolioViewModel.connectToRepo(viewLifecycleOwner);
            myPortfolioViewModel.getCoinForViewListLiveData().observe(viewLifecycleOwner, coinForViews ->
                    coinListAdapterOptional.ifPresent(coinListAdapter -> coinListAdapter.setCoinItemList(coinForViews)));
            myPortfolioViewModel.getThrowableMutableLiveData().observe(viewLifecycleOwner, throwable ->
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show());
            myPortfolioViewModel.getPortfolioInfoForViewLiveData().observe(viewLifecycleOwner,
                    portfolioInfoForView -> {
                        sumTextView.setText(portfolioInfoForView.getSum());
                        change24PrsTextView.setText(portfolioInfoForView.getChangePercent24Hour());
                        change24PrsTextView.setTextColor(portfolioInfoForView.getChange24Color());
                        change24TextView.setText(portfolioInfoForView.getChange24Hour());
                        change24TextView.setTextColor(portfolioInfoForView.getChange24Color());
                    }
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCoinListClickListenerOptional = Optional.empty();
        context = null;
    }
}
