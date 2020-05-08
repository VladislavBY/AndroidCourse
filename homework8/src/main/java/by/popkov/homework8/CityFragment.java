package by.popkov.homework8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CityFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String selectedCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getSettings();
        makeRecyclerView();
    }

    private void getSettings() {
    }

    private void makeRecyclerView() {
        recyclerView.setAdapter(new CityFragmentAdapter());
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
    }
}
