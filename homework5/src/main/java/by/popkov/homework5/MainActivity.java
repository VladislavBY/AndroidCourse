package by.popkov.homework5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private SongAdapter songAdapter;
    private static final String ADAPTER = "ADAPTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView(savedInstanceState);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song1);
        mediaPlayer.start();

        songAdapter.setSongItemList(new ArrayList<>(Arrays.asList("Song 1", "Song 2", "Song 3")));
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
