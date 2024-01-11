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
 * 設定内容変更確認ダイアログクラス
 */
public class SettingsSaveConfirmationDialogFragment extends DialogFragment {
    /**
     * ★山下コード★
     * ダイアログが生成される際に呼ばれるメソッド
     * @param savedInstanceState 以前に保存された状態
     * @return 作成されたダイアログ
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // ダイアログビルダーを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // ダイアログのメッセージを設定
        builder.setMessage(R.string.save_dialog_msg);
        // Positive Buttonを設定
        builder.setPositiveButton(R.string.dialog_btn_save, new DialogButtonClickListener());
        // Negative Buttonを設定
        builder.setNegativeButton(R.string.btn_cancel, new DialogButtonClickListener());
        // ダイアログオブジェクトを生成し、リターン
        AlertDialog dialog = builder.create();

        return dialog;
    }

    /**
     * ★山下コード★
     * ダイアログのボタンがクリックされた際に呼ばれるリスナークラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        /**
         * ★山下コード★
         * ダイアログのボタンがクリックされた際に呼ばれるメソッド
         * @param dialog ダイアログオブジェクト
         * @param which  クリックされたボタンの種類
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 引き継ぎデータを取得
            Bundle bundle = getArguments();

            // タップされたボタンに応じて処理を行う
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    // Bundleオブジェクトから引き継ぎデータを取得する
                    int sortType = bundle.getInt(getString(R.string.settings_sort_type));
                    int completedTaskDisplayType = bundle.getInt(getString(R.string.settings_completed_task_display_type));
                    boolean cbImportantItemState = bundle.getBoolean(getString(R.string.settings_important_state));
                    boolean cbEmergencyItemState = bundle.getBoolean(getString(R.string.settings_emergency_state));
                    boolean cbNoteItemState = bundle.getBoolean(getString(R.string.settings_note_state));
                    boolean cbNotSetItemState = bundle.getBoolean(getString(R.string.settings_notset_state));

                    // 所属しているアクティビティを取得
                    Activity parentActivity = getActivity();

                    // SharedPreferences.Editorオブジェクトを取得
                    SharedPreferences preferences = parentActivity.getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
                    // 入力されたデータを保存する
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(getString(R.string.settings_sort_type), sortType);
                    editor.putInt(getString(R.string.settings_completed_task_display_type), completedTaskDisplayType);
                    editor.putBoolean(getString(R.string.settings_important_state), cbImportantItemState);
                    editor.putBoolean(getString(R.string.settings_emergency_state), cbEmergencyItemState);
                    editor.putBoolean(getString(R.string.settings_note_state), cbNoteItemState);
                    editor.putBoolean(getString(R.string.settings_notset_state), cbNotSetItemState);
                    editor.commit();

                    // アクティビティを終了
                    parentActivity.finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }
    }
}