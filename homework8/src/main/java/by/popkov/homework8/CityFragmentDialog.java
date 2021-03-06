package by.popkov.homework8;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

public class CityFragmentDialog extends AppCompatDialogFragment {
    private EditText cityEditText;
    private CityFragmentDialogListener cityFragmentDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_city, null, false);
        cityEditText = view.findViewById(R.id.cityEditText);
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Enter the city name")
                .setNegativeButton("Cancel", (dialog, which) -> {
                }).setPositiveButton("Done", (dialog, which) -> {
                    if (cityFragmentDialogListener != null) {
                        cityFragmentDialogListener.OnPositiveButtonClick(cityEditText.getText().toString());
                    }
                }).create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof CityFragmentDialogListener) {
            cityFragmentDialogListener = (CityFragmentDialogListener) parentFragment;
        }

    }

    interface CityFragmentDialogListener {
        void OnPositiveButtonClick(String cityName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cityFragmentDialogListener = null;
    }
}
