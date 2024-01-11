package com.websarva.wings.android.taskmanagementapplication_level3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ★コードを書いていきましょう★
 * 『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 * 休み時間はしっかり休んでくださいね　っ ̫ -˘
 *
 * タスク編集アクティビティ
 */
public class EditActivity extends AppCompatActivity {

    /**
     * アクティビティが作成される際に呼ばれるメソッド
     * @param savedInstanceState 以前のインスタンスの状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //★ここに処理を書く

    }

    /**
     * オプションメニューが作成される際に呼ばれるメソッド
     * @param menu オプションメニュー
     * @return true（メニューが表示される）、false（メニューが表示されない）
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //★ここに処理を書く


        return true;
    }

    /**
     * オプションメニューの項目が選択された際に呼ばれるメソッド
     * @param item 選択されたメニューアイテム
     * @return true（処理が完了）、false（それ以外の場合）
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 戻り値用の変数を用意
        boolean returnVal = true;

        //★ここに処理を書く


        return returnVal;
    }

    /*
     * ★タスクを変更するために入力された内容をBundleに保存する必要があります。
     * ★メソッドに分けた方がきれいなので作成していますが、別に使わなくてもOKです。
     * 入力値をBundleオブジェクトに保存するメソッド
     * @return 保存された入力値を格納したBundleオブジェクト
     */
    private Bundle saveInputValueInBundle() {
        // 引き継ぎデータを格納するBundleオブジェクトを生成
        Bundle bundle = new Bundle();

        /**
         * ★山下ヒント～　(ヒントを元に自分で調べてみてください)
         * スピナーの入力値の取得  .getSelectedItemPosition()
         * ラジオボタンの入力値の取得  .getCheckedRadioButtonId()
         *
         * Bundleに格納する場合の型
         * 文字系　.putString
         * 日時　.putLong
         * ラジオボタン、スピナー　.putInt
         */
        //★ここに処理を書く



        // 入力値を保存したBundleオブジェクトを返す
        return bundle;
    }
}