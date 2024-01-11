package com.websarva.wings.android.taskmanagementapplication_level3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ★コードを書いていきましょう★
 *  『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 *  すごくどうでもいいけど、、、結婚式はやりたい派ですか？
 *  先生はやりたくない派です。。
 *  大人になってもイヤなことから逃げられないことって沢山あるよね૮₍ ˃ ⤙ ˂ ₎ა
 *
 * タスク編集フラグメント
 */
public class EditTaskFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    //　コンストラクタ
    public EditTaskFragment() {
        super(R.layout.fragment_edit_task);
    }

    /**
     * フラグメントのビューが生成された際の処理
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //★ここに処理を書く


    }

    /**
     * フラグメントが破棄される際の処理
     */
    //★ここにメソッドから書く




    /**
     * ★山下コード★
     * DatePickerDialogで日付が選択された際の処理
     * @param view DatePickerダイアログのビュー
     * @param year 選択された年
     * @param month 選択された月（0から始まるインデックス）
     * @param dayOfMonth 選択された日
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 月を文字列で格納する変数
        // ※月は０からのカウントの値が返されるので、+1する
        String monthStr = String.valueOf(month + 1);
        // 月が１～９だった場合
        if((month + 1) < 10) {
            // 月の数値の前に"0"を追加する
            monthStr = "0" + monthStr;
        }

        // 日を文字列で格納する変数
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        // 日が１～９だった場合
        if(dayOfMonth < 10) {
            // 日の数値の前に"0"を追加する
            dayOfMonthStr = "0" + dayOfMonthStr;
        }

        // 表示する形式の文字列を作成
        String dateStr = year + "/" + monthStr + "/" + dayOfMonthStr;

        // 完了日を表示するTextViewにdateStrを設定
        View fragmentView = getView();
        TextView tvEditTaskCompletionDate = fragmentView.findViewById(R.id.tvEditTaskCompletionDate);
        tvEditTaskCompletionDate.setText(dateStr);
    }

    /**
     * ★山下コード★
     * TimePickerDialogで時刻が選択された際の処理
     * @param view TimePickerダイアログのビュー
     * @param hourOfDay 選択された時の値 (24時間形式)
     * @param minute 選択された分の値
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // 時間を文字列で格納する変数
        String hourOfDayStr = String.valueOf(hourOfDay);
        // 時間が１～９だった場合
        if(hourOfDay < 10) {
            // 時間の数値の前に"0"を追加する
            hourOfDayStr = "0" + hourOfDayStr;
        }

        // 分を文字列で格納する変数
        String minuteStr = String.valueOf(minute);
        // 分が１～９だった場合
        if(minute < 10) {
            // 分の数値の前に"0"を追加する
            minuteStr = "0" + minuteStr;
        }

        // 表示する形式の文字列を作成
        String timeStr = hourOfDayStr + ":" + minuteStr;

        // 完了時刻を表示するTextViewにtimeStrを設定
        View fragmentView = getView();
        TextView tvEditTaskCompletionTime = fragmentView.findViewById(R.id.tvEditTaskCompletionTime);
        tvEditTaskCompletionTime.setText(timeStr);
    }

    /**
     * ★山下コード★
     * 日付選択ボタンのクリックリスナ
     */
    private class OnEditDateButtonClickListener implements View.OnClickListener {
        // 入力値を受け取るクラスをDatePickerFragmentに渡すフィールド
        EditTaskFragment _editTaskFragment;

        // コンストラクタ
        public OnEditDateButtonClickListener(EditTaskFragment editTaskFragment) {
            // 入力値を受け取るクラスを取得
            _editTaskFragment = editTaskFragment;
        }

        /**
         * ★山下コード★
         * ボタンがクリックされた際の処理
         * @param v クリックされたビュー
         */
        @Override
        public void onClick(View v) {
            // 日付選択ダイアログを生成
            DialogFragment dialogFragment = new DatePickerFragment(_editTaskFragment);
            // ダイアログを表示
            dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    }

    /**
     * ★山下コード★
     * 時刻選択ボタンのクリックリスナ
     */
    private class OnEditTimeButtonClickListener implements View.OnClickListener {
        // 入力値を受け取るクラスをTimePickerFragmentに渡すフィールド
        EditTaskFragment _editTaskFragment;

        // コンストラクタ
        public OnEditTimeButtonClickListener(EditTaskFragment editTaskFragment) {
            // 入力値を受け取るクラスを取得
            _editTaskFragment = editTaskFragment;
        }

        /**
         * ★山下コード★
         * ボタンがクリックされた際の処理
         * @param v クリックされたビュー
         */
        @Override
        public void onClick(View v) {
            // 時刻選択ダイアログを生成
            DialogFragment dialogFragment = new TimePickerFragment(_editTaskFragment);
            // ダイアログを表示
            dialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
        }
    }
}