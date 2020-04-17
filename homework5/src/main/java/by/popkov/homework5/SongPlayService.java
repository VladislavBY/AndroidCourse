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
    public static final String SEND_SONG = "sendSong";
    public static final String NEXT_SONG = "nextSong";
    public static final String PREVIOUS_SONG = "previousSong";
    private MediaPlayer mediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

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
            if (intentAction.equals(NEXT_SONG))
                customOnCompletionListener.onCompletion(currentSong);
            else if (intentAction.equals(PREVIOUS_SONG))
                customOnClickPreviousListener.onClick(currentSong);
        }
        final Song song = (Song) intent.getSerializableExtra(MainActivity.SONG);
        if (song != null) {
            currentSong = song;
            PendingIntent openPlayerPendingIntent = getOpenPlayerPendingIntent();
            PendingIntent nextSongPendingIntent = getNextSongPendingIntent();
            PendingIntent previousSongPendingIntent = getPreviousSongPendingIntent();
            Notification notification = getNotification(
                    song,
                    openPlayerPendingIntent,
                    nextSongPendingIntent,
                    previousSongPendingIntent);
            startForeground(1, notification);
            startMediaPlayer(song);
        }

        return START_REDELIVER_INTENT;
    }

    private Notification getNotification(
            Song song,
            PendingIntent openPlayerPendingIntent,
            PendingIntent nextSongPendingIntent,
            PendingIntent previousSongPendingIntent) {
        return new NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(song.getName())
                .setSmallIcon(R.drawable.ic_play_arrow_red_24dp)
                .setContentIntent(openPlayerPendingIntent)
                .addAction(
                        R.drawable.ic_arrow_back_black_24dp,
                        getString(R.string.previous_song_text),
                        previousSongPendingIntent)
                .addAction(
                        R.drawable.ic_arrow_forward_black_24dp,
                        getString(R.string.next_song_text),
                        nextSongPendingIntent)
                .build();
    }

    private void startMediaPlayer(final Song song) {
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

    private PendingIntent getOpenPlayerPendingIntent() {
        Intent openPlayerIntent = new Intent(this, MainActivity.class);
        openPlayerIntent.putExtra(MainActivity.SONG, currentSong);
        openPlayerIntent.setAction(SEND_SONG);
        return PendingIntent.getActivity(this,
                0, openPlayerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getNextSongPendingIntent() {
        Intent nextIntent = new Intent(this, SongPlayService.class);
        nextIntent.setAction(NEXT_SONG);
        return PendingIntent.getService(this, 0, nextIntent, 0);

    }

    private PendingIntent getPreviousSongPendingIntent() {
        Intent previousIntent = new Intent(this, SongPlayService.class);
        previousIntent.setAction(PREVIOUS_SONG);
        return PendingIntent.getService(this, 0, previousIntent, 0);
    }

    class SongPlayServicesBinder extends Binder {
        SongPlayService getService() {
            return SongPlayService.this;
        }
    }


}
