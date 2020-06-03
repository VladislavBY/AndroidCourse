package by.popkov.cryptoportfolio.coin_info_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import by.popkov.cryptoportfolio.R;
import by.popkov.cryptoportfolio.my_portfolio_view.CoinForView;

public class CoinInfoFragment extends Fragment {
    public static final String TAG = "CoinInfoFragment";

    private static final String EXTRA_COIN_FOR_VIEW = "ExtraCoinForView";
    private CoinForView coinForView;

    private Toolbar toolbar;

    public static CoinInfoFragment getInstance(CoinForView coinForView) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_COIN_FOR_VIEW, coinForView);
        CoinInfoFragment coinInfoFragment = new CoinInfoFragment();
        coinInfoFragment.setArguments(bundle);
        return coinInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coin_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments();
        initViews(view);
        setToolBar();
    }

    private void setToolBar() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setActionBar(toolbar);
            if (activity.getActionBar() != null) {
                activity.getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private void initViews(View view) {
        toolbar = view.findViewById(R.id.toolBar);
    }

    private void extractArguments() {
        if (getArguments() != null) {
            coinForView = (CoinForView) getArguments().getSerializable(EXTRA_COIN_FOR_VIEW);
        }
    }
}
