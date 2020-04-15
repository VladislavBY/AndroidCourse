package by.popkov.homework5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private SongAdapter songAdapter;
    private static final String ADAPTER = "ADAPTER";
    static final String SONG = "SONG";
    public static final String CHANNEL_ID = "songPlayChannel";
    private SongPlayService songPlayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        initRecyclerView(savedInstanceState);
        makeSongList();
        setListener();
    }

    private void setListener() {
        songAdapter.setCustomItemClickListener(new SongAdapter.CustomItemClickListener() {
            @Override
            public void onClick(Song song) {
                startSongPlayService(song);
            }
        });
    }

    private void makeSongList() {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song(R.raw.song1, "Song 1"));
        songs.add(new Song(R.raw.song2, "Song 2"));
        songs.add(new Song(R.raw.song3, "Song 3"));
        songs.add(new Song(R.raw.song4, "Song 4"));
        songs.add(new Song(R.raw.song5, "Song 5"));
        songAdapter.setSongItemList(songs);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Song Play Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void startSongPlayService(Song song) {
        Intent intent = new Intent(this, SongPlayService.class);
        intent.putExtra(SONG, song);
        startService(intent);

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            SongPlayService.SongPlayServicesBinder binder = (SongPlayService.SongPlayServicesBinder) iBinder;
            songPlayService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (songPlayService != null) songPlayService = null;
        }
    };

    private void bindSongPlayService() {
        bindService(new Intent(this, SongPlayService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    private void unbindSongPlayService() {
        unbindService(serviceConnection);
        if (songPlayService != null) songPlayService = null;
    }

    private void initRecyclerView(Bundle savedInstanceState) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (savedInstanceState != null) {
            recyclerView.setAdapter((RecyclerView.Adapter) savedInstanceState.getParcelable(ADAPTER));
        } else recyclerView.setAdapter(new SongAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false));
        songAdapter = (SongAdapter) recyclerView.getAdapter();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ADAPTER, songAdapter);
    }
}
