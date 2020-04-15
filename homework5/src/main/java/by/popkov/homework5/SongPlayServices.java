package by.popkov.homework5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.Serializable;

public class SongPlayServices extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Song song = (Song) intent.getSerializableExtra(MainActivity.SONG);
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), song.getId());
        mediaPlayer.start();

        return START_REDELIVER_INTENT;
    }
}
