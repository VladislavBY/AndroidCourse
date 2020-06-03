package by.popkov.cryptoportfolio.coin_info_view;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    public interface OnHomeClickListener {
        void onHomeClick();
    }

    public static final String TAG = "CoinInfoFragment";

    private static final String EXTRA_COIN_FOR_VIEW = "ExtraCoinForView";
    private CoinForView coinForView;
    private OnHomeClickListener onHomeClickListener;

    private Toolbar toolbar;

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
        extractArguments();
        initViews(view);
        setToolBar();
    }

    private void setToolBar() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setActionBar(toolbar);
            ActionBar actionBar = activity.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onHomeClickListener.onHomeClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
