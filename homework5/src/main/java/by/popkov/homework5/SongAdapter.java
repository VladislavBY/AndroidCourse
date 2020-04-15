package by.popkov.homework5;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ItemViewHolder> implements Parcelable {
    private ArrayList<Song> songItemList = new ArrayList<>();

    public ArrayList<Song> getSongItemList() {
        return songItemList;
    }

    void setSongItemList(ArrayList<Song> songItemList) {
        this.songItemList = songItemList;
        notifyDataSetChanged();
    }

    SongAdapter() {
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
        if (songItemList != null) return songItemList.size();
        else return 0;
    }

    interface CustomItemClickListener {
        void onClick(Song song);
    }

    private CustomItemClickListener customItemClickListener;

    public void setCustomItemClickListener(CustomItemClickListener customItemClickListener) {
        this.customItemClickListener = customItemClickListener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSongName;
        private ImageView imageViewPlayStatus;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
            imageViewPlayStatus = itemView.findViewById(R.id.imageViewPlayStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewPlayStatus.setVisibility(View.VISIBLE);
                    customItemClickListener.onClick(songItemList.get(getAdapterPosition()));
                }
            });
        }

        void bindItem(Song song) {
            textViewSongName.setText(song.getName());
        }
    }

    private SongAdapter(Parcel in) {
        songItemList = (ArrayList<Song>) in.readSerializable();
    }

    public static final Creator<SongAdapter> CREATOR = new Creator<SongAdapter>() {
        @Override
        public SongAdapter createFromParcel(Parcel in) {
            return new SongAdapter(in);
        }

        @Override
        public SongAdapter[] newArray(int size) {
            return new SongAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(songItemList);
    }
}
