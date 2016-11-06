package de.mchllngr.devsettings.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.drm.DrmStore;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import de.mchllngr.devsettings.R;

/**
 * Util-Class for using the {@link Notification}.
 */
public class DevSettingsNotificationUtil {

    /**
     * Id used for showing the notification.
     */
    private static int notificationId = -1;

    /**
     * Shows the {@link Notification}.
     */
    public static void showNotification(Context context) {
        hideNotification(context);

        notificationId = context.getResources().getInteger(R.integer.notification_id);

        // create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setOngoing(true)
                .setShowWhen(false)
                .setCustomContentView(generateCustomContentView(context))
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(Notification.PRIORITY_MIN)
                .build();

        // show notification
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }

    /**
     * Generates the CustomContentView for the {@link Notification}.
     */
    private static RemoteViews generateCustomContentView(Context context) {
        // get custom notification view for length
        RemoteViews customContentView = new RemoteViews(
                context.getPackageName(),
                R.layout.notification_custom_content
        );

        // TODO set PendingIntent
//        // set PendingIntent
//        Intent resultIntent = new Intent(context, StartApplicationService.class);
//        resultIntent.putExtra(
//                context.getString(R.string.key_package_name),
//                applicationModels[i].packageName
//        );
//        // needed to make the PendingIntent 'unique' so multiple PendingIntents can
//        // be active at the same time
//        resultIntent.setAction(Long.toString(System.currentTimeMillis()));
//        PendingIntent pendingIntent = PendingIntent.getService(
//                context,
//                0,
//                resultIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//        customContentView.setOnClickPendingIntent(ICON_IDS_CUSTOM_CONTENT[i], pendingIntent);

        return customContentView;
    }

    /**
     * Hide the {@link Notification}.
     */
    public static void hideNotification(Context context) {
        if (notificationId < 0) return;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }
}
