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
    private MediaPlayer mediaPlayer;

    interface CustomOnCompletionListener {
        void onCompletion(Song song);
    }

    private CustomOnCompletionListener customOnCompletionListener;

    public CustomOnCompletionListener getCustomOnCompletionListener() {
        return customOnCompletionListener;
    }

    public void setCustomOnCompletionListener(CustomOnCompletionListener customOnCompletionListener) {
        this.customOnCompletionListener = customOnCompletionListener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SongPlayServicesBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Song song = (Song) intent.getSerializableExtra(MainActivity.SONG);
        if (song != null) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.putExtra(MainActivity.SONG, song);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(song.getName())
                    .setSmallIcon(R.drawable.ic_play_arrow_red_24dp)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);

            if (mediaPlayer != null) mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, song.getId());
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    customOnCompletionListener.onCompletion(song);
                }
            });
        }

        return START_REDELIVER_INTENT;
    }

    class SongPlayServicesBinder extends Binder {
        SongPlayService getService() {
            return SongPlayService.this;
        }
    }


}
