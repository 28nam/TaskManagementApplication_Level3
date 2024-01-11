package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * ★コードを書いていきましょう★
 * 『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 * 変数名とかよく見て付けてください꒰(ूꇐωꇐ ू)꒱
 *
 * タスク登録確認ダイアログクラス
 */
public class RegistrationConfirmationDialogFragment extends DialogFragment {

    /**
     * ダイアログが作成される際に呼ばれるメソッド
     * @param savedInstanceState 以前に保存されたダイアログの状態
     * @return 作成されたダイアログオブジェクト
     */
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //★ここに処理を書く
        //★AlertDialog dialog = null;の部分は開発の際に削除してダイアログの生成処理に書き換えること
        AlertDialog dialog = null;


        return dialog;
    }

    /**
     * ダイアログが破棄される際に呼ばれるメソッド
     */
    //★ここにメソッドから書く

    /**
     * ダイアログボタンがクリックされた際のリスナークラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        /**
         * ダイアログボタンがクリックされた際に呼ばれるメソッド
         * @param dialog クリックが発生したダイアログオブジェクト
         * @param which  クリックされたボタンのID
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //★　ここに処理を書く


        }

        /**
         * タスクの通知を設定するメソッド
         * @param requestCode       アラームのリクエストコード
         * @param taskDescription   タスクの説明
         * @param finishDateTime    タスクの完了予定日時
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
    }
}