package by.popkov.cryptoportfolio.my_portfolio_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.R;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.ItemHolder> {

    public interface OnCoinListClickListener {
        void onClick(CoinForView coinForView);
    }

    private OnCoinListClickListener onCoinListClickListener;

    private List<CoinForView> itemList = new ArrayList<>();

    void addCoin(CoinForView coinForView) {
        itemList.add(coinForView);
        notifyDataSetChanged();
    }

    void deleteCoin(CoinForView coinForView) {
        CoinForView oldCoin = itemList.stream()
                .filter(coin -> coin.getSymbol().equals(coinForView.getSymbol()))
                .findFirst()
                .orElse(null);
        itemList.remove(oldCoin);
        notifyDataSetChanged();
    }

    void updateCoin(CoinForView coinForView) {
        CoinForView oldCoin = itemList.stream()
                .filter(coin -> coin.getSymbol().equals(coinForView.getSymbol()))
                .findFirst()
                .orElse(null);
        int indexOfOldCoin = itemList.indexOf(oldCoin);
        itemList.remove(indexOfOldCoin);
        itemList.add(indexOfOldCoin, coinForView);
        notifyDataSetChanged();
    }

    void setCoinItemList(List<CoinForView> listCoinForView) {
        itemList = new ArrayList<>(listCoinForView);
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
        private TextView fiatSymbol;
        private TextView coinPrise;
        private TextView coinPrise24HChange;
        private TextView coinPriseSum;

        ItemHolder(@NonNull View itemView, @NonNull OnCoinListClickListener onCoinListClickListener, List<CoinForView> itemList) {
            super(itemView);
            coinIcon = itemView.findViewById(R.id.coinIcon);
            coinSymbol = itemView.findViewById(R.id.coinSymbol);
            fiatSymbol = itemView.findViewById(R.id.fiatSymbol);
            coinPrise = itemView.findViewById(R.id.coinPrise);
            coinPrise24HChange = itemView.findViewById(R.id.coinPrise24HChange);
            coinPriseSum = itemView.findViewById(R.id.coinPriseSum);
            itemView.setOnClickListener(v -> onCoinListClickListener.onClick(itemList.get(getAdapterPosition())));
        }

        private void bindItem(CoinForView coinForView) {
            Glide.with(itemView.getContext())
                    .load(coinForView.getLogoUrl())
                    .into(coinIcon);
            coinSymbol.setText(coinForView.getSymbol());
            fiatSymbol.setText(coinForView.getFiatSymbol());
            coinPrise.setText(coinForView.getPrise());
            coinPrise24HChange.setText(coinForView.getChangePercent24Hour());
            coinPrise24HChange.setTextColor(coinForView.getChange24Color());
            coinPriseSum.append(" " + coinForView.getSum());
        }
    }
}
