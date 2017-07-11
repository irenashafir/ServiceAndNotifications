package shafir.irena.serviceandnotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by irena on 11/07/2017.
 */

// receive firebase message here
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public  static final String TAG = "Ness";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: Received");
    }

    @Override
    public void handleIntent(Intent intent) {

        // get the payload from your notification
        String title = intent.getExtras().getString("title");
        String shortMessage = intent.getExtras().getString("short");



        // super.handleIntent(intent);
        // super- if the app is in the background- it push notification DEFAULT
        // is the app is in the foreground - it sends the message to onMessageReceived


        // deprecated in API LEVEL O
        // import android.app.notification

        Intent contentIntent = new Intent(this, MainActivity.class);
        // put some extras -- from the original could message

        PendingIntent pi = PendingIntent.getActivity(this, 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Channel1";
            setupChannel(channelName);
            Notification.Builder builder = new Notification.Builder(this, channelName);

            // title, text, smallIcon, -- > 99.9 content Intent, set AutoCancel true
            builder.setContentTitle(title)
                    .setContentText(shortMessage)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                    .setBadgeIconType(Notification.BADGE_ICON_LARGE)
                    .setAutoCancel(true)
                    .setContentIntent(pi);

            NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mgr.notify(1, builder.build());
        }
        else {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle(title)
                    .setContentText(shortMessage)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)  // this will push the notification from the top
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pi);

            NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mgr.notify(1, builder.build());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannel(String id){
        String channelName = getResources().getString(R.string.channel1_name);

        NotificationChannel channel = new NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_HIGH);

        channel.setDescription(getResources().getString(R.string.channel_description));
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.AUDIO_ATTRIBUTES_DEFAULT);

        channel.setShowBadge(true);

        NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mgr.createNotificationChannel(channel);
    }


}
