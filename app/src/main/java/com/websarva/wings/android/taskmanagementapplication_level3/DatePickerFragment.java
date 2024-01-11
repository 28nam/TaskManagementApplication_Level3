package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * ★！このクラスは変更しないでください！★
 * タスク登録時に表示される日付選択ダイアログ
 */
public class DatePickerFragment extends DialogFragment {
    // 呼び出し側のEditTaskFragmentを格納するフィールド
    private EditTaskFragment _editTaskFragment;

    // コンストラクタ
    public DatePickerFragment(EditTaskFragment editTaskFragment) {
        // 日付取得のダイアログ受け取るクラスをフィールドに代入
        _editTaskFragment = editTaskFragment;
    }

    /**
     * ★山下コード★
     * ダイアログが生成される際に呼ばれるメソッド
     * @param savedInstanceState 以前のインスタンスの状態
     * @return 生成されたダイアログ
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Calendarを取得
        final Calendar c = Calendar.getInstance();
        // 現在の年月日をそれぞれ取得
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // DatePickerDialogを生成し、ダイアログとしてreturnする
        return new DatePickerDialog(getActivity(), _editTaskFragment, year, month, day);
    }
}