package by.popkov.homework8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CityFragment extends Fragment implements CityFragmentDialog.CityFragmentDialogListener {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String selectedCity;
    private FragmentActivity fragmentActivity;
    private CityFragmentAdapter cityFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            getSettings();
            makeRecyclerView();
        }

    }

    private void getSettings() {
        SharedPreferences sharedPreferences
                = fragmentActivity.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        selectedCity = sharedPreferences.getString(MainActivity.SELECTED_CITY_KEY, "0");
    }

    private void makeRecyclerView() {
        if (!selectedCity.equals("0")) {
            recyclerView.setAdapter(new CityFragmentAdapter(selectedCity));
        } else recyclerView.setAdapter(new CityFragmentAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity, RecyclerView.VERTICAL, false));
        cityFragmentAdapter = (CityFragmentAdapter) recyclerView.getAdapter();
        setAdapterOnCityClickListener();
    }

    private void setAdapterOnCityClickListener() {
        cityFragmentAdapter.setOnCityClickListener(new CityFragmentAdapter.OnCityClickListener() {
            @Override
            public void onClick(String cityName) {
                SharedPreferences sharedPreferences
                        = fragmentActivity.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString(MainActivity.SELECTED_CITY_KEY, cityName)
                        .apply();
                fragmentActivity.getSupportFragmentManager().popBackStack();
            }
        });

    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton4);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentDialog();
            }
        });
    }

    private void showFragmentDialog() {
        new CityFragmentDialog().show(getChildFragmentManager(), "CityFragmentDialog");
    }

    @Override
    public void OnPositiveButtonClick(String cityName) {
        cityFragmentAdapter.addCityName(cityName);
    }
}
