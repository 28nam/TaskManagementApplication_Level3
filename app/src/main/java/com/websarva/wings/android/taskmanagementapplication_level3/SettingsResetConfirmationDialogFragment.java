package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * ★！このクラスは変更しないでください！★
 * 初期設定に戻す確認ダイアログクラス
 */
public class SettingsResetConfirmationDialogFragment extends DialogFragment {
    /**
     * ★山下コード★
     * ダイアログが生成された際に呼ばれるメソッド
     * @param savedInstanceState 保存されたインスタンスの状態
     * @return 生成されたダイアログ
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // ダイアログビルダーを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // ダイアログのメッセージを設定
        builder.setMessage(R.string.reset_dialog_msg);
        // Positive Buttonを設定
        builder.setPositiveButton(R.string.reset_dialog_btn_back, new DialogButtonClickListener());
        // Negative Buttonを設定
        builder.setNegativeButton(R.string.btn_cancel, new DialogButtonClickListener());
        // ダイアログオブジェクトを生成し、リターン
        AlertDialog dialog = builder.create();

        return dialog;
    }

    /**
     * ★山下コード★
     *ダイアログ内のボタンがクリックされた際のリスナー
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        /**
         * ★山下コード★
         * ボタンがクリックされた際の処理
         * @param dialog ダイアログオブジェクト
         * @param which  クリックされたボタンの種類
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // タップされたボタンに応じて処理を行う
            switch(which) {
                // Positive Buttonがクリックされた場合
                case DialogInterface.BUTTON_POSITIVE:
                    // 所属しているアクティビティを取得
                    Activity parentActivity = getActivity();

                    // 設定の初期値が格納されたSharedPreferencesオブジェクトを取得
                    SharedPreferences defaultPreferences = parentActivity.getSharedPreferences(getString(R.string.default_settings_file_key), Context.MODE_PRIVATE);
                    // 設定値が格納されたSharedPreferencesオブジェクトを取得
                    SharedPreferences preferences = parentActivity.getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);

                    // 入力データを初期値に戻す
                    // SharedPreferences.Editorオブジェクトを取得
                    SharedPreferences.Editor editor = preferences.edit();
                    // 初期値を設定値に保存する
                    editor.putInt(getString(R.string.settings_sort_type), defaultPreferences.getInt(getString(R.string.settings_sort_type), 0));
                    editor.putInt(getString(R.string.settings_completed_task_display_type), defaultPreferences.getInt(getString(R.string.settings_completed_task_display_type), 1));
                    editor.putBoolean(getString(R.string.settings_important_state), defaultPreferences.getBoolean(getString(R.string.settings_important_state), true));
                    editor.putBoolean(getString(R.string.settings_emergency_state), defaultPreferences.getBoolean(getString(R.string.settings_emergency_state), true));
                    editor.putBoolean(getString(R.string.settings_note_state), defaultPreferences.getBoolean(getString(R.string.settings_note_state), true));
                    editor.putBoolean(getString(R.string.settings_notset_state), defaultPreferences.getBoolean(getString(R.string.settings_notset_state), true));
                    // 格納した値を保存
                    editor.commit();

                    // アクティビティを終了
                    parentActivity.finish();
                    break;
                // Negative Buttonがクリックされた場合
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
                // その他の場合
                default:
                    break;
            }
        }
    }
}
