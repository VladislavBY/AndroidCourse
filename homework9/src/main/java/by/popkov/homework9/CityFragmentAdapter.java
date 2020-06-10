package by.popkov.homework9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import by.popkov.homework9.city_database.CityEntity;

public class CityFragmentAdapter extends RecyclerView.Adapter<CityFragmentAdapter.ItemViewHolder> {
    private ArrayList<String> cityNames = new ArrayList<>();
    private String selectedCity;

    void addCityName(String cityName) {
        this.cityNames.add(cityName);
        notifyDataSetChanged();
    }

    void setCityNames(List<CityEntity> cities) {
        cityNames.clear();
        List<String> collect = cities.stream().map(CityEntity::getName)
                .collect(Collectors.toList());
        cityNames.addAll(collect);
        notifyDataSetChanged();
    }

    CityFragmentAdapter() {
    }

    CityFragmentAdapter(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_fragment_city, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindItem(cityNames.get(position));
    }

    @Override
    public int getItemCount() {
        return (cityNames != null) ? cityNames.size() : 0;
    }

    interface OnCityClickListener {
        void onClick(String cityName);
    }

    private OnCityClickListener onCityClickListener;

    void setOnCityClickListener(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView cityTextView;
        private ImageView doneImageView;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            doneImageView = itemView.findViewById(R.id.doneImageView);
            itemView.setOnClickListener(v -> onCityClickListener.onClick(cityTextView.getText().toString()));
        }

        private void bindItem(String cityName) {
            cityTextView.setText(cityName);
            if (cityName.equals(selectedCity)) {
                doneImageView.setVisibility(View.VISIBLE);
            }
        }
    }
}
