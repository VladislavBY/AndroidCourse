package by.popkov.cryptoportfolio.my_portfolio_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.R;
import by.popkov.cryptoportfolio.data_classes.CoinForView;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.ItemHolder> implements Filterable {

    public interface OnCoinListClickListener {

        void onItemClick(CoinForView coinForView);
    }

    private OnCoinListClickListener onCoinListClickListener;

    private List<CoinForView> itemList = new ArrayList<>();
    private List<CoinForView> itemListFull = new ArrayList<>();

    void setCoinItemList(List<CoinForView> listCoinForView) {
        itemList.clear();
        itemList.addAll(listCoinForView);
        itemListFull.clear();
        itemListFull.addAll(listCoinForView);
        notifyDataSetChanged();
    }

    CoinListAdapter(@NonNull OnCoinListClickListener onCoinListClickListener) {
        this.onCoinListClickListener = onCoinListClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_coin_list, parent, false),
                onCoinListClickListener,
                itemList
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bindItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return (itemList != null) ? itemList.size() : 0;
    }


    static class ItemHolder extends RecyclerView.ViewHolder {

        private ImageView coinIcon;

        private TextView coinSymbol;
        private TextView coinPrise;
        private TextView coinPrise24HChange;
        private TextView coinPriseSum;

        ItemHolder(@NonNull View itemView, @NonNull OnCoinListClickListener onCoinListClickListener, List<CoinForView> itemList) {
            super(itemView);
            coinIcon = itemView.findViewById(R.id.coinIcon);
            coinSymbol = itemView.findViewById(R.id.coinSymbol);
            coinPrise = itemView.findViewById(R.id.coinPrise);
            coinPrise24HChange = itemView.findViewById(R.id.coinPrise24HChange);
            coinPriseSum = itemView.findViewById(R.id.coinPriseSum);
            itemView.setOnClickListener(v -> onCoinListClickListener.onItemClick(itemList.get(ItemHolder.this.getAdapterPosition())));
        }

        private void bindItem(@NotNull CoinForView coinForView) {
            Glide.with(itemView.getContext())
                    .load(coinForView.getLogoUrl())
                    .into(coinIcon);
            coinSymbol.setText(coinForView.getSymbol());
            coinPrise.setText(coinForView.getPrise());
            coinPrise24HChange.setText(coinForView.getChangePercent24Hour());
            coinPrise24HChange.setTextColor(coinForView.getChange24Color());
            coinPriseSum.setText(coinForView.getSum());
        }

    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CoinForView> filteredContactItemList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredContactItemList.addAll(itemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CoinForView coin : itemListFull) {
                    if (coin.getSymbol().toLowerCase().contains(filterPattern)) {
                        filteredContactItemList.add(coin);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredContactItemList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return contactFilter;
    }
}
