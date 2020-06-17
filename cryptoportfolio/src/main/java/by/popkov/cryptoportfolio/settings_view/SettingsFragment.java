package by.popkov.cryptoportfolio.settings_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import by.popkov.cryptoportfolio.OnHomeClickListener;
import by.popkov.cryptoportfolio.R;

import static by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp.*;

public class SettingsFragment extends Fragment {
    public static final String TAG = "SettingsFragment";
    private Optional<OnHomeClickListener> onHomeClickListenerOptional = Optional.empty();
    private Context context;
    private SettingsFragmentViewModel settingsFragmentViewModel;

    private RadioGroup selectedSymbol;
    private ImageButton homeBtn;

    @NotNull
    public static SettingsFragment getInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnHomeClickListener) {
            onHomeClickListenerOptional = Optional.of((OnHomeClickListener) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initViews(view);
    }

    private void initViewModel() {
        settingsFragmentViewModel = new ViewModelProvider(getViewModelStore(), new SettingsFragmentViewModelFactory(context))
                .get(SettingsFragmentViewModel.class);
    }

    private void initViews(@NotNull View view) {
        selectedSymbol = view.findViewById(R.id.selectedSymbol);
        setCheckedSymbol(view);
        setSelectedSymbolChangeListener();
        homeBtn = view.findViewById(R.id.homeBtn);
        setHomeBtnListener();
    }

    private void setCheckedSymbol(View view) {
        switch (settingsFragmentViewModel.getFiatSettings()) {
            case USD:
                ((RadioButton) view.findViewById(R.id.usd)).setChecked(true);
                break;
            case EUR:
                ((RadioButton) view.findViewById(R.id.eur)).setChecked(true);
                break;
            case RUB:
                ((RadioButton) view.findViewById(R.id.rub)).setChecked(true);
                break;
            case GBP:
                ((RadioButton) view.findViewById(R.id.gbp)).setChecked(true);
                break;
            case JPY:
                ((RadioButton) view.findViewById(R.id.jpy)).setChecked(true);
                break;
            case KRW:
                ((RadioButton) view.findViewById(R.id.krw)).setChecked(true);
                break;
            case BYN:
                ((RadioButton) view.findViewById(R.id.byn)).setChecked(true);
                break;
            case BTC:
                ((RadioButton) view.findViewById(R.id.btc)).setChecked(true);
                break;
            case ETH:
                ((RadioButton) view.findViewById(R.id.eth)).setChecked(true);
                break;
        }
    }

    private void setSelectedSymbolChangeListener() {
        selectedSymbol.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.usd:
                    settingsFragmentViewModel.saveFiatSetting(USD);
                    break;
                case R.id.eur:
                    settingsFragmentViewModel.saveFiatSetting(EUR);
                    break;
                case R.id.rub:
                    settingsFragmentViewModel.saveFiatSetting(RUB);
                    break;
                case R.id.gbp:
                    settingsFragmentViewModel.saveFiatSetting(GBP);
                    break;
                case R.id.jpy:
                    settingsFragmentViewModel.saveFiatSetting(JPY);
                    break;
                case R.id.krw:
                    settingsFragmentViewModel.saveFiatSetting(KRW);
                    break;
                case R.id.byn:
                    settingsFragmentViewModel.saveFiatSetting(BYN);
                    break;
                case R.id.btc:
                    settingsFragmentViewModel.saveFiatSetting(BTC);
                    break;
                case R.id.eth:
                    settingsFragmentViewModel.saveFiatSetting(ETH);
                    break;
            }
        });
    }


    private void setHomeBtnListener() {
        onHomeClickListenerOptional.ifPresent(onHomeClickListener ->
                homeBtn.setOnClickListener(v -> onHomeClickListener.onHomeClick()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onHomeClickListenerOptional = Optional.empty();
        context = null;
    }
}
