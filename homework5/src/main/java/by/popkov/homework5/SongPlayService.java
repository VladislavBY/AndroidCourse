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


public class SongPlayService extends Service {
    private static final String SEND_SONG = "sendSong";
    private static final String NEXT_SONG = "nextSong";
    private static final String PREVIOUS_SONG = "previousSong";
    private static final String EXIT = "exit";
    private Song currentSong;
    private MediaPlayer mediaPlayer;


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buttonAction(intent.getAction());
        final Song song = (Song) intent.getSerializableExtra(MainActivity.SONG);
        if (song != null) {
            currentSong = song;
            Notification notification = getNotification(song);
            startForeground(1, notification);
            startMediaPlayer(song);
        }
        return START_REDELIVER_INTENT;
    }

    private void buttonAction(String intentAction) {
        if (intentAction != null) {
            switch (intentAction) {
                case NEXT_SONG:
                    customOnCompletionListener.onCompletion(currentSong);
                    break;
                case PREVIOUS_SONG:
                    customOnClickPreviousListener.onClick(currentSong);
                    break;
                case EXIT:
                    if (mediaPlayer != null) mediaPlayer.stop();
                    stopSelf();
                    break;
            }
        }
    }

    private Notification getNotification(Song song) {
        return new NotificationCompat
                .Builder(this, MainActivity.CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(song.getName())
                .setSmallIcon(R.drawable.ic_play_arrow_red_24dp)
                .setContentIntent(getOpenPlayerPendingIntent())
                .addAction(
                        R.drawable.ic_arrow_back_black_24dp,
                        getString(R.string.previous_song_text),
                        getPreviousSongPendingIntent()
                )
                .addAction(
                        R.drawable.ic_arrow_forward_black_24dp,
                        getString(R.string.next_song_text),
                        getNextSongPendingIntent()
                )
                .addAction(
                        R.drawable.ic_remove_circle_outline_black_24dp,
                        getString(R.string.exit_song),
                        getExitSongPendingIntent()
                )
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
        return PendingIntent.getService(this, 1, nextIntent, 0);

    }

    private PendingIntent getPreviousSongPendingIntent() {
        Intent previousIntent = new Intent(this, SongPlayService.class);
        previousIntent.setAction(PREVIOUS_SONG);
        return PendingIntent.getService(this, 2, previousIntent, 0);
    }

    private PendingIntent getExitSongPendingIntent() {
        Intent exitIntent = new Intent(this, SongPlayService.class);
        exitIntent.setAction(EXIT);
        return PendingIntent.getService(this, 3, exitIntent, 0);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SongPlayServicesBinder();
    }

    class SongPlayServicesBinder extends Binder {

        SongPlayService getService() {
            return SongPlayService.this;
        }

    }

}
