package by.popkov.cryptoportfolio.my_portfolio_view;

import androidx.fragment.app.DialogFragment;

public class AddNewCoinDialogFragment extends DialogFragment {
    interface AddNewCoinDialogListener {
        void OnPositiveButtonClick(final String symbol, final Double number);
    }
}
