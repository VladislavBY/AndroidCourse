package by.popkov.cryptoportfolio.my_portfolio_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        initViewModel();
        addCoinFab = view.findViewById(R.id.addCoinFab);
        addCoinFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewCoinDialogFragment().show(getChildFragmentManager(), "TAG");
            }
        });
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
            myPortfolioViewModel = new ViewModelProvider(this, new MyPortfolioViewModelFactory(getViewLifecycleOwner(), context))
                    .get(MyPortfolioViewModel.class);
            myPortfolioViewModel.getCoinForViewListLiveData().observe(getViewLifecycleOwner(), coinForViews ->
                    coinListAdapterOptional.ifPresent(coinListAdapter -> coinListAdapter.setCoinItemList(coinForViews)));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCoinListClickListenerOptional = Optional.empty();
    }
}
