package by.popkov.cryptoportfolio.settings_view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Optional;

import by.popkov.cryptoportfolio.OnHomeClickListener;
import by.popkov.cryptoportfolio.R;

public class SettingsFragment extends Fragment {
    public static final String TAG = "SettingsFragment";
    private Optional<OnHomeClickListener> onHomeClickListenerOptional = Optional.empty();
    private Context context;

    private RadioGroup selectedSymbol;
    private ImageButton homeBtn;


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

        initViews(view);
    }

    private void initViews(View view) {
        selectedSymbol = view.findViewById(R.id.selectedSymbol);
        homeBtn = view.findViewById(R.id.homeBtn);
        setHomeBtnListener();
        setSelectedSymbolChangeListener();
    }

    private void setSelectedSymbolChangeListener() {
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
