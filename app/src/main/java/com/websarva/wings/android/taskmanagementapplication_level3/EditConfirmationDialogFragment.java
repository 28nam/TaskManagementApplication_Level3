package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * ★コードを書いていきましょう★
 * 『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 * 心は折れてないですか？疲れましたよね。。
 * ↑これを書いているということは先生が疲れているんでしょうね( ◠‿◠ )
 *
 * タスク編集確認ダイアログクラス
 */
public class EditConfirmationDialogFragment extends DialogFragment {

    /**
     * ダイアログが作成される際に呼ばれるメソッド
     * @param savedInstanceState 以前のインスタンスの状態
     * @return 生成されたダイアログオブジェクト
     */
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //★ここに処理を書く
        //★AlertDialog dialog = null;の部分は開発の際に削除してダイアログの生成処理に書き換えること
        AlertDialog dialog = null;


        return dialog;
    }

    /**
     * ダイアログが閉じられる際に呼ばれるメソッド
     */
    //★ここにメソッドから書く


    /**
     * ダイアログのボタンがクリックされた際のリスナークラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        /**
         * ダイアログのボタンがクリックされた際に呼ばれるメソッド
         * @param dialog ダイアログオブジェクト
         * @param which クリックされたボタンの種類
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // フラグメントに埋め込まれた引き継ぎデータを取得
            Bundle bundle = getArguments();

            //★ここに処理を書く


        }

        /**
         * ★山下コード★
         * ★アラームについては加点項目です。基本的な機能が完成してからやってください！
         * タスクのアラーム通知を設定するメソッド
         * @param requestCode タスクのリクエストコード
         * @param taskDescription タスクの説明
         * @param finishDateTime タスクの完了予定日時
         */
        private void setAlarmNotification(int requestCode, String taskDescription, long finishDateTime) {
            // アラームまでの時間
            Date alarmDateTime = new Date(finishDateTime);

            // Calendarオブジェクトを取得
            Calendar calendar = Calendar.getInstance();
            // カレンダーに完了予定日時を設定
            calendar.setTime(alarmDateTime);
            // 完了予定日時の1時間前に設定
            calendar.add(Calendar.HOUR_OF_DAY, -1);
            // 完了予定日時をミリ秒単位で取得
            long alarmTimeInMillis = calendar.getTimeInMillis();

            // 所属する親アクティビティを取得
            Activity parentActivity = getActivity();

            // インテントを生成
            Intent intent = new Intent(parentActivity, AlarmNotification.class);
            // 通知に必要な引き継ぎデータを設定
            intent.putExtra("requestCode", requestCode);
            intent.putExtra("appTitle", getString(R.string.app_name));
            intent.putExtra("message", taskDescription + getString(R.string.msg_notification_title));

            // PendingIntentを生成
            PendingIntent pendingIntent = PendingIntent.getBroadcast(parentActivity, requestCode, intent, 0);

            // アラームマネージャーを取得
            AlarmManager alarmManager = (AlarmManager) parentActivity.getSystemService(Context.ALARM_SERVICE);

            // アラームマネージャーがnullではなかった場合
            if(alarmManager != null) {
                // アラームマネージャーにアラームを設定
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
            }
        }

        /**
         * ★山下コード★
         * ★アラームについては加点項目です。基本的な機能が完成してからやってください！
         * タスクのアラーム通知をキャンセルするメソッド
         * @param requestCode タスクのリクエストコード
         */
        private void cancelAlarmNotification(int requestCode) {
            // 所属する親アクティビティを取得
            Activity parentActivity = getActivity();

            // インテントを生成
            Intent indent = new Intent(parentActivity, AlarmNotification.class);

            // PendingIntentを生成
            //　Android 12以降（API レベル 31以上）でのバックワード互換性の問題を指しています。具体的には、PendingIntentを作成する際に、FLAG_IMMUTABLEまたはFLAG_MUTABLEのいずれかを指定する必要がある。
            //　PendingIntent.FLAG_IMMUTABLE:これはAndroid 12以降で導入されたフラグで、作成後にPendingIntentの内容を変更できないことを示します。つまり、一度作成されたPendingIntentは変更不可です。このフラグを指定すると、バックグラウンドでの最適化が可能になります。例えば、通知がタップされた際に特定のアクションを実行するPendingIntentを作成する場合、これは不変であるべきです。なぜなら、通知がタップされた後に変更する必要がないためです。
            //PendingIntent.FLAG_MUTABLE:これはAndroid 12以降でも利用可能ですが、FLAG_IMMUTABLE とは対照的に、作成後にPendingIntentの内容を変更できることを示します。つまり、一度作成されたPendingIntentは後から変更可能です。例えば、通知がタップされた際に特定のアクションを実行するPendingIntentを作成する場合であっても、通知の内容が後から変更される場合には FLAG_MUTABLE を使用します。
            //一般的には、PendingIntent.FLAG_IMMUTABLE を使い、作成後に内容が変更される必要がない場合に利用します。一方、特定の状況下で後から内容が変更される可能性がある場合には PendingIntent.FLAG_MUTABLE を使います。
            PendingIntent pending = PendingIntent.getBroadcast(parentActivity, requestCode, indent, PendingIntent.FLAG_IMMUTABLE);

            // アラームマネージャーを取得
            AlarmManager alarmManager = (AlarmManager)parentActivity.getSystemService(Context.ALARM_SERVICE);

            // アラームマネージャーがnullではなかった場合
            if (alarmManager != null) {
                // アラームをキャンセル
                alarmManager.cancel(pending);
            }
        }
    }
}