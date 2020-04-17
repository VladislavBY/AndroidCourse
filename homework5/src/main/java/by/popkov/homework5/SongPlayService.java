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
    private static final String COMMAND = "COMMAND";
    private MediaPlayer mediaPlayer;
    private Song currentSong;

    interface CustomOnCompletionListener {
        void onCompletion(Song song);
    }

    private CustomOnCompletionListener customOnCompletionListener;

    public void setCustomOnCompletionListener(CustomOnCompletionListener customOnCompletionListener) {
        this.customOnCompletionListener = customOnCompletionListener;
    }

    interface CustomOnClickPreviousListener {
        void onClick(Song song);
    }

    private CustomOnClickPreviousListener customOnClickPreviousListener;

    public void setCustomOnClickPreviousListener(CustomOnClickPreviousListener customOnClickPreviousListener) {
        this.customOnClickPreviousListener = customOnClickPreviousListener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SongPlayServicesBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String intentAction = intent.getAction();
        if (intentAction != null) {
            if (intentAction.equals("nextSong"))
                customOnCompletionListener.onCompletion(currentSong);
            else if (intentAction.equals("previousSong"))
                customOnClickPreviousListener.onClick(currentSong);
        }

        final Song song = (Song) intent.getSerializableExtra(MainActivity.SONG);
        if (song != null) {
            currentSong = song;
            Intent openPlayerIntent = new Intent(this, MainActivity.class);
            openPlayerIntent.putExtra(MainActivity.SONG, song);
            openPlayerIntent.setAction("sendSong");
            PendingIntent openPlayerPendingIntent = PendingIntent.getActivity(this,
                    0, openPlayerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent nextIntent = new Intent(this, SongPlayService.class);
            nextIntent.putExtra(COMMAND, "NEXT");
            nextIntent.setAction("nextSong");
            PendingIntent nextPendingIntent = PendingIntent.getService(this,
                    0, nextIntent, 0);

            Intent previousIntent = new Intent(this, SongPlayService.class);
            previousIntent.putExtra(COMMAND, "PREVIOUS");
            previousIntent.setAction("previousSong");
            PendingIntent previousPendingIntent = PendingIntent.getService(this,
                    0, previousIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(song.getName())
                    .setSmallIcon(R.drawable.ic_play_arrow_red_24dp)
                    .setContentIntent(openPlayerPendingIntent)
                    .addAction(R.drawable.ic_arrow_back_black_24dp, "PREVIOUS", previousPendingIntent)
                    .addAction(R.drawable.ic_arrow_forward_black_24dp, "NEXT", nextPendingIntent)
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
