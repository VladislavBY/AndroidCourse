package by.popkov.homework5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ItemViewHolder> {
    private ArrayList<Song> songItemList;
    private ArrayList<ImageView> itemImageViewsList = new ArrayList<>();

    ArrayList<Song> getSongItemList() {
        return songItemList;
    }

    void setSongItemList(ArrayList<Song> songItemList) {
        this.songItemList = new ArrayList<>(songItemList);
        notifyDataSetChanged();
    }

    SongAdapter(ArrayList<Song> songItemList) {
        this.songItemList = new ArrayList<>(songItemList);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);
        return new ItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindItem(songItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return (songItemList != null) ? songItemList.size() : 0;
    }

    interface CustomItemClickListener {
        void onClick(Song song);
    }

    private CustomItemClickListener customItemClickListener;

    void setCustomItemClickListener(CustomItemClickListener customItemClickListener) {
        this.customItemClickListener = customItemClickListener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSongName;
        private ImageView imageViewPlayStatus;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
            imageViewPlayStatus = itemView.findViewById(R.id.imageViewPlayStatus);
            itemImageViewsList.add(imageViewPlayStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ImageView imageView : itemImageViewsList) {
                        imageView.setVisibility(View.INVISIBLE);
                    }
                    for (Song song : songItemList) {
                        song.setPlaying(false);
                    }
                    Song song = songItemList.get(getAdapterPosition());
                    song.setPlaying(true);
                    imageViewPlayStatus.setVisibility(View.VISIBLE);
                    customItemClickListener.onClick(song);
                }
            });
        }

        void bindItem(Song song) {
            textViewSongName.setText(song.getName());
            imageViewPlayStatus.setVisibility((song.isPlaying()) ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
