package by.popkov.homework5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.SeekBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String ADAPTER = "ADAPTER";
    static final String SONG = "SONG";
    public static final String CHANNEL_ID = "songPlayChannel";
    private SongAdapter songAdapter;
    private SongPlayService songPlayService;
    private ArrayList<Song> songArrayList;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        makeSongList();
        initRecyclerView(savedInstanceState);
        setAdapterListener();
        setPlayArrowByNotification();
        bindSongPlayService();
        seekBar = findViewById(R.id.seekBar);
        updateSeekBar();
    }

    private void updateSeekBar() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (songPlayService != null && songPlayService.getMediaPlayer() != null) {
                        seekBar.setMax(songPlayService.getMediaPlayer().getDuration());
                        seekBar.setProgress(songPlayService.getMediaPlayer().getCurrentPosition());
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void setAdapterListener() {
        songAdapter.setCustomItemClickListener(new SongAdapter.CustomItemClickListener() {
            @Override
            public void onClick(Song song) {
                startSongPlayService(song);
            }
        });
    }

    private void setPlayArrowByNotification() {
        Intent intent = getIntent();
        Song song = (Song) intent.getSerializableExtra(SONG);
        if (song != null) {
            for (int i = 0; i < songArrayList.size(); i++) {
                if (songArrayList.get(i).getId() == song.getId()) {
                    songArrayList.get(i).setPlaying(true);
                    songAdapter.setSongItemList(songArrayList);
                    break;
                }
            }
        }
    }

    private void setSongPlayServiceListener() {
        songPlayService.setCustomOnCompletionListener(new SongPlayService.CustomOnCompletionListener() {
            @Override
            public void onCompletion(Song song) {
                for (int i = 0; i < songArrayList.size(); i++) {
                    if (songArrayList.get(i).getId() == song.getId() && i + 1 < songArrayList.size()) {
                        startSongPlayService(songArrayList.get(i + 1));
                        songArrayList.get(i + 1).setPlaying(true);
                        songArrayList.get(i).setPlaying(false);
                        songAdapter.setSongItemList(songArrayList);
                        break;
                    }
                }
            }
        });
        songPlayService.setCustomOnClickPreviousListener(new SongPlayService.CustomOnClickPreviousListener() {
            @Override
            public void onClick(Song song) {
                for (int i = 0; i < songArrayList.size(); i++) {
                    if (songArrayList.get(i).getId() == song.getId() && i - 1 >= 0) {
                        startSongPlayService(songArrayList.get(i - 1));
                        songArrayList.get(i - 1).setPlaying(true);
                        songArrayList.get(i).setPlaying(false);
                        songAdapter.setSongItemList(songArrayList);
                        break;
                    }
                }
            }
        });
    }

    private void startSongPlayService(Song song) {
        Intent intent = new Intent(this, SongPlayService.class);
        intent.putExtra(SONG, song);
        ContextCompat.startForegroundService(this, intent);
    }

    private void makeSongList() {
        songArrayList = new ArrayList<>();
        songArrayList.add(new Song(R.raw.song1, "Song 1"));
        songArrayList.add(new Song(R.raw.song2, "Song 2"));
        songArrayList.add(new Song(R.raw.song3, "Song 3"));
        songArrayList.add(new Song(R.raw.song4, "Song 4"));
        songArrayList.add(new Song(R.raw.song5, "Song 5"));
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

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            SongPlayService.SongPlayServicesBinder binder = (SongPlayService.SongPlayServicesBinder) iBinder;
            songPlayService = binder.getService();
            setSongPlayServiceListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (songPlayService != null) songPlayService = null;
        }
    };

    private void bindSongPlayService() {
        Intent intent = new Intent(this, SongPlayService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void unbindSongPlayService() {
        unbindService(serviceConnection);
        if (songPlayService != null) songPlayService = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindSongPlayService();
    }

    private void initRecyclerView(Bundle savedInstanceState) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (savedInstanceState != null) {
            recyclerView.setAdapter((RecyclerView.Adapter) savedInstanceState.getParcelable(ADAPTER));
        } else {
            SongAdapter songAdapter = new SongAdapter();
            songAdapter.setSongItemList(songArrayList);
            recyclerView.setAdapter(songAdapter);
        }
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
