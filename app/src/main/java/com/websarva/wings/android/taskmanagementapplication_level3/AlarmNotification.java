package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * ★！このクラスは変更しないでください！★
 * ★アラームについては加点項目です。基本的な機能が完成してからやってください！
 * アラーム通知クラス
 */
public class AlarmNotification extends BroadcastReceiver {
    // 通知チャネルID文字列定数
    private static final String CHANNEL_ID = "task_alarm_notification_channel";

    /**
     * ブロードキャストレシーバが通知を受信した際に呼ばれるメソッド
     * @param context コンテキスト
     * @param intent 受信したインテント
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // 引き継ぎデータからリクエストコードを取得
        int requestCode = intent.getIntExtra("requestCode", -1);
        // 引き継ぎデータからアプリタイトルを取得
        String appTitle = intent.getStringExtra("appTitle");
        // 引き継ぎデータからメッセージを取得
        String message = intent.getStringExtra("message");
        // requestCodeが一致するPendingIntentを取得
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 通知チャネルの重要度を設定
        int notificationImpotance = NotificationManager.IMPORTANCE_DEFAULT;

        // 通知チャネルを生成
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, appTitle, notificationImpotance);
        // 通知のバイブレーションを設定
        channel.enableVibration(true);
        // 通知のバッジ表示を設定
        channel.setShowBadge(true);
        // 通知をロック画面にも表示するよう設定
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        // NotificationManagerオブジェクトを取得
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        // 通知チャネルを設定
        manager.createNotificationChannel(channel);

        // Notificationを作成するBuilderクラスを生成
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        // 通知エリアに表示されるアイコンを設定
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        // 通知ドロワーでの表示タイトルを設定
        builder.setContentTitle(message);
        // PendingIntentを設定
        builder.setContentIntent(pendingIntent);
        // BuilderからNotificationオブジェクトを生成
        Notification notification = builder.build();
        // NotificationManagerCompatオブジェクトを取得
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        // 通知の送信
        managerCompat.notify(requestCode, notification);
    }
}