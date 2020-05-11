package by.popkov.homework8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CityFragment extends Fragment implements CityFragmentDialog.CityFragmentDialogListener {
    static final String FRAGMENT_TAG = "CityFragment";
    private static String CITY_DATABASE = "CITY_DATABASE";
    static final String SELECTED_CITY_KEY = "SELECTED_CITY_KEY ";
    private RecyclerView recyclerView;
    private FragmentActivity fragmentActivity;
    private CityFragmentAdapter cityFragmentAdapter;
    private CityDatabase cityDatabase;
    private SharedPreferences sharedPreferences;

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
            connectToCityDatabase();
            makeRecyclerView();
        }
    }

    private void connectToCityDatabase() {
        cityDatabase = Room.databaseBuilder(fragmentActivity, CityDatabase.class, CITY_DATABASE).build();
    }

    private void getSettings() {
        sharedPreferences = fragmentActivity.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private void makeRecyclerView() {
        String selectedCity = sharedPreferences.getString(SELECTED_CITY_KEY, "0");
        if (!selectedCity.equals("0")) {
            recyclerView.setAdapter(new CityFragmentAdapter(selectedCity));
        } else recyclerView.setAdapter(new CityFragmentAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity, RecyclerView.VERTICAL, false));
        cityFragmentAdapter = (CityFragmentAdapter) recyclerView.getAdapter();
        setAdapterOnCityClickListener();
        setCityFromDataBase();
    }

    private void setCityFromDataBase() {
        CompletableFuture.supplyAsync(() -> {
            ArrayList<String> result = new ArrayList<>();
            CityEntity[] cityEntities = cityDatabase.getCityDao().loadAllCity();
            for (CityEntity cityEntity : cityEntities) {
                result.add(cityEntity.getName());
            }
            return result;
        }).thenAcceptAsync(strings -> cityFragmentAdapter.setCityNames(strings), ContextCompat.getMainExecutor(fragmentActivity));

    }

    private void setAdapterOnCityClickListener() {
        cityFragmentAdapter.setOnCityClickListener(cityName -> {
            SharedPreferences sharedPreferences
                    = fragmentActivity.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit()
                    .putString(SELECTED_CITY_KEY, cityName)
                    .apply();
            fragmentActivity.getSupportFragmentManager().popBackStack();
        });

    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton4);
        floatingActionButton.setOnClickListener(v -> showFragmentDialog());
    }

    private void showFragmentDialog() {
        new CityFragmentDialog().show(getChildFragmentManager(), "CityFragmentDialog");
    }

    @Override
    public void OnPositiveButtonClick(final String cityName) {
        cityFragmentAdapter.addCityName(cityName);
        new Thread(() -> cityDatabase.getCityDao().insertCity(new CityEntity().setName(cityName))).start();
    }

    @Override
    public void onDestroyView() {
        sharedPreferences = null;
        super.onDestroyView();
        cityDatabase.close();
    }
}
