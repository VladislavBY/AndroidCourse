package by.popkov.cryptoportfolio.coin_info_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import by.popkov.cryptoportfolio.R;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinForView;

public class CoinInfoFragment extends Fragment implements CoinInfoFragmentViewModel.ShowThrowable {
    @Override
    public void show(Throwable throwable) {
        Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    public interface OnHomeClickListener {
        void onHomeClick();
    }

    public static final String TAG = "CoinInfoFragment";

    private static final String EXTRA_COIN_FOR_VIEW = "ExtraCoinForView";
    private OnHomeClickListener onHomeClickListener;
    private CoinInfoFragmentViewModel coinInfoFragmentViewModel;
    private Context context;

    private ImageView coinIcon;
    private TextView coinSymbol;
    private TextView coinNumberData;
    private TextView coinSumData;
    private TextView coinChangeSum24HourData;
    private TextView coinPriseData;
    private TextView coinChangePercent24HourData;
    private TextView coinChange24HourData;
    private TextView coinMarketCapData;
    private TextView coinMarket24VolumeData;
    private TextView coinGlobalSupplyData;
    private Button editBtn;
    private Button deleteBtn;
    private ImageButton homeBtn;

    @NotNull
    public static CoinInfoFragment getInstance(CoinForView coinForView) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_COIN_FOR_VIEW, coinForView);
        CoinInfoFragment coinInfoFragment = new CoinInfoFragment();
        coinInfoFragment.setArguments(bundle);
        return coinInfoFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnHomeClickListener) {
            onHomeClickListener = (OnHomeClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coin_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initViewModel();
        setBtnListeners();
    }

    private void initViewModel() {
        coinInfoFragmentViewModel = new ViewModelProvider(this, new CoinInfoFragmentViewModelFactory(extractCoinForView(), context))
                .get(CoinInfoFragmentViewModel.class);
        coinInfoFragmentViewModel.connectToRepo(getViewLifecycleOwner(), this);
        coinInfoFragmentViewModel.getCoinForViewLiveData().observe(getViewLifecycleOwner(), this::setViewsData);
    }

    private void setBtnListeners() {
        deleteBtn.setOnClickListener(v -> showDeleteDialog());
        editBtn.setOnClickListener(v -> showEditDialog());
        homeBtn.setOnClickListener(v -> onHomeClickListener.onHomeClick());
    }

    private void showEditDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_dialog_view, null, false);
        EditText editText = view.findViewById(R.id.newNumber);
        new AlertDialog.Builder(context)
                .setTitle(R.string.edit_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.edit_dialog_positive,
                        (dialog, which) -> coinInfoFragmentViewModel.updateCoin(Double.valueOf(editText.getText().toString())))
                .setNegativeButton(R.string.edit_dialog_negative, (dialog, which) -> {
                })
                .create()
                .show();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.delete_dialog_title)
                .setPositiveButton(R.string.delete_dialog_positive, (dialog, which) -> {
                    coinInfoFragmentViewModel.deleteCoin();
                    onHomeClickListener.onHomeClick();
                })
                .setNeutralButton(R.string.delete_dialog_negative, ((dialog, which) -> {
                }))
                .create()
                .show();
    }

    private void setViewsData(CoinForView coinForView) {
        Glide.with(this)
                .load(coinForView.getLogoUrl())
                .into(coinIcon);
        coinSymbol.setText(coinForView.getSymbol());
        coinNumberData.setText(coinForView.getNumber());
        coinSumData.setText(coinForView.getSum());
        coinChangeSum24HourData.setText(coinForView.getChangeSum24Hour());
        coinChangeSum24HourData.setTextColor(coinForView.getChange24Color());
        coinPriseData.setText(coinForView.getPrise());
        coinChangePercent24HourData.setText(coinForView.getChangePercent24Hour());
        coinChangePercent24HourData.setTextColor(coinForView.getChange24Color());
        coinChange24HourData.setText(coinForView.getChange24Hour());
        coinChange24HourData.setTextColor(coinForView.getChange24Color());
        coinMarketCapData.setText(coinForView.getMarketCap());
        coinMarket24VolumeData.setText(coinForView.getMarket24Volume());
        coinGlobalSupplyData.setText(coinForView.getGlobalSupply());
    }

    private CoinForView extractCoinForView() {
        if (getArguments() != null) {
            return (CoinForView) getArguments().getSerializable(EXTRA_COIN_FOR_VIEW);
        }
        throw new IllegalArgumentException("Not found CoinForView in Arguments");
    }

    private void initViews(@NonNull View view) {
        coinIcon = view.findViewById(R.id.coinIcon);
        coinSymbol = view.findViewById(R.id.coinSymbol);
        coinNumberData = view.findViewById(R.id.coinNumberData);
        coinSumData = view.findViewById(R.id.coinSumData);
        coinChangeSum24HourData = view.findViewById(R.id.coinChangeSum24HourData);
        coinPriseData = view.findViewById(R.id.coinPriseData);
        coinChangePercent24HourData = view.findViewById(R.id.coinChangePercent24HourData);
        coinChange24HourData = view.findViewById(R.id.coinChange24HourData);
        coinMarketCapData = view.findViewById(R.id.coinMarketCapData);
        coinMarket24VolumeData = view.findViewById(R.id.coinMarket24VolumeData);
        coinGlobalSupplyData = view.findViewById(R.id.coinGlobalSupplyData);
        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        homeBtn = view.findViewById(R.id.homeBtn);
    }
}
