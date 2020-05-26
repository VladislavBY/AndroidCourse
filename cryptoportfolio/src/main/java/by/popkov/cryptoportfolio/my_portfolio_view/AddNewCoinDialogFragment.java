package by.popkov.cryptoportfolio.my_portfolio_view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import by.popkov.cryptoportfolio.R;

public class AddNewCoinDialogFragment extends DialogFragment {
    interface AddNewCoinDialogListener {
        void OnPositiveButtonClick(final String symbol, final String number);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getParentFragment() instanceof AddNewCoinDialogListener && getContext() != null) {
            AddNewCoinDialogListener addNewCoinDialogListener = (AddNewCoinDialogListener) getParentFragment();
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ftagment_add_coin, null, false);
            EditText coinSymbol = view.findViewById(R.id.coinSymbol);
            EditText coinNumber = view.findViewById(R.id.coinNumber);
            return new AlertDialog.Builder(getContext())
                    .setView(view)
                    .setTitle(R.string.add_dialog_title)
                    .setPositiveButton(R.string.add_button, (dialog, which) ->
                            addNewCoinDialogListener.OnPositiveButtonClick(coinSymbol.getText().toString(),
                                    coinNumber.getText().toString()))
                    .create();
        }
        return new AlertDialog.Builder(getContext()).create();
    }
}
