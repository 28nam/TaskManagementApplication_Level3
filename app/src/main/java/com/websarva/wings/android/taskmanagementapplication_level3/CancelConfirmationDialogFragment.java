package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * ★！このクラスは変更しないでください！★
 * キャンセル確認ダイアログクラス
 * 戻るボタンが押された時に表示されるダイアログを作成しています
 */
public class CancelConfirmationDialogFragment extends DialogFragment {

    /**
     * ★山下コード★
     * ダイアログが生成される際に呼ばれるメソッド
     * @param savedInstanceState 以前のインスタンスの状態
     * @return 生成されたダイアログ
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // フラグメントに埋め込まれた引き継ぎデータを取得
        Bundle bundle = getArguments();

        // ダイアログビルダーを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // ダイアログのメッセージを設定
        builder.setMessage(bundle.getString("dialogMessage"));
        // Positive Buttonを設定
        builder.setPositiveButton(bundle.getString("positiveMessage"), new DialogButtonClickListener());
        // Negative Buttonを設定
        builder.setNegativeButton(R.string.btn_cancel, new DialogButtonClickListener());
        // ダイアログオブジェクトを生成し、リターン
        AlertDialog dialog = builder.create();

        return dialog;
    }

    /**
     * ★山下コード★
     * ダイアログボタンがクリックされた際の処理を行うクラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // タップされたボタンに応じて処理を行う
            switch(which) {
                // Positive Buttonがクリックされた場合
                case DialogInterface.BUTTON_POSITIVE:
                    // 所属しているアクティビティを取得
                    Activity parentActivity = getActivity();
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
