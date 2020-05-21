package by.popkov.cryptoportfolio.my_portfolio_viewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.popkov.cryptoportfolio.R;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.ItemHolder> {

    interface OnCoinListClickListener {
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
                onCoinListClickListener
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
        ItemHolder(@NonNull View itemView, @NonNull OnCoinListClickListener onCoinListClick) {
            super(itemView);
        }

        private void bindItem(CoinForView coinForView) {

        }
    }
}
