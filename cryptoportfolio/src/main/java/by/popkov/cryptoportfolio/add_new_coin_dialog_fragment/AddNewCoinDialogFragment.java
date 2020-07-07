package by.popkov.cryptoportfolio.add_new_coin_dialog_fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import by.popkov.cryptoportfolio.R;

public class AddNewCoinDialogFragment extends DialogFragment {
    public static final String TAG = "AddNewCoinDialogFragment";

    private AddNewCoinDialogFragmentViewModel addNewCoinDialogFragmentViewModel;
    private Context context;

    @NotNull
    public static AddNewCoinDialogFragment getInstance() {
        return new AddNewCoinDialogFragment();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        initViewModel();
        return makeDialog();
    }

    private void initViewModel() {
        if (getActivity() != null) {
            addNewCoinDialogFragmentViewModel = new ViewModelProvider(
                    getViewModelStore(), new AddNewCoinDialogFragmentViewModelFactory(getActivity().getApplication(), context)
            ).get(AddNewCoinDialogFragmentViewModel.class);
        }
    }

    @NotNull
    private Dialog makeDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ftagment_add_coin, null, false);
        EditText coinSymbol = view.findViewById(R.id.coinSymbol);
        EditText coinNumber = view.findViewById(R.id.coinNumber);
        return new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(R.string.add_dialog_title)
                .setPositiveButton(R.string.add_button, (dialog, which) ->
                        onPositiveButtonClick(coinSymbol.getText().toString(),
                                coinNumber.getText().toString()))
                .setNeutralButton(R.string.cancel_button, (dialog, which) -> {
                })
                .create();
    }

    private void onPositiveButtonClick(String symbol, String number) {
        addNewCoinDialogFragmentViewModel.saveCoin(symbol, number);
    }
}
