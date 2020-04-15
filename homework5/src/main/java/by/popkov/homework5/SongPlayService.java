package by.popkov.homework5;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static by.popkov.homework5.MainActivity.CHANNEL_ID;


public class SongPlayService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SongPlayServicesBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Song song = (Song) intent.getSerializableExtra(MainActivity.SONG);
        if (song != null) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(song.getName())
                    .setSmallIcon(R.drawable.ic_play_arrow_red_24dp)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);

            MediaPlayer mediaPlayer = MediaPlayer.create(this, song.getId());
            mediaPlayer.start();
        }

        return START_REDELIVER_INTENT;
    }

    class SongPlayServicesBinder extends Binder {
        SongPlayService getService() {
            return SongPlayService.this;
        }
    }


}
