package by.popkov.homework8;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CityFragmentDialog extends AppCompatDialogFragment {
    private EditText cityEditText;
    private CityFragmentDialogListener cityFragmentDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog_city, null);
        cityEditText = view.findViewById(R.id.cityEditText);
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Enter the city name")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cityFragmentDialogListener
                                .OnPositiveButtonClick(cityEditText.getText().toString());
                    }
                }).create();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() != null) {
            try {
                cityFragmentDialogListener = (CityFragmentDialogListener) getParentFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(getParentFragment().toString() +
                        "ParentFragment must implement CityFragmentDialog.CityFragmentDialogListener");
            }
        }

    }

    interface CityFragmentDialogListener {
        void OnPositiveButtonClick(String cityName);
    }
}
