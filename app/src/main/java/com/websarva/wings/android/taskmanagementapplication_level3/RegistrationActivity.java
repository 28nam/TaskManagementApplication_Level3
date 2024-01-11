package com.websarva.wings.android.taskmanagementapplication_level3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
 *  『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 *  エラーが出ている箇所をよく見て解決していきましょう꒰(ूꇐωꇐ ू)꒱
 *
 * タスク登録アクティビティ
 */
public class RegistrationActivity extends AppCompatActivity {
    /**
     * アクティビティが作成された際に呼ばれるメソッド
     * @param savedInstanceState 以前に保存されたアクティビティの状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //★ここに処理を書く
        //★アクションバーの戻るメニューを作成


    }

    /**
     * オプションメニューが作成される際に呼ばれるメソッド
     * @param menu メニューオブジェクト
     * @return メニューが正常に作成された場合はtrue
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //★ここに処理を書く



        return true;
    }

    /**
     * オプションメニューの項目が選択された際に呼ばれるメソッド
     * @param item 選択されたメニューアイテム
     * @return 処理が正常に行われた場合はtrue
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 戻り値用の変数を用意
        boolean returnVal = true;

        //★ここに処理を書く


        return returnVal;
    }

    /**
     * ★メソッドとして分ける場合は使用して下さい。使わなくてもＯＫです。
     * 入力値をBundleに格納して返すメソッド
     * @return 入力値を格納したBundleオブジェクト
     */
    private Bundle saveInputValueInBundle() {
        // 引き継ぎデータを格納するBundleオブジェクトを生成
        Bundle inputValueBundle = new Bundle();

        /**
         * ★山下ヒント　(ヒントを元に自分で調べてみてください)
         * スピナーの入力値の取得  .getSelectedItemPosition()
         * ラジオボタンの入力値の取得  .getCheckedRadioButtonId()
         *
         *　Bundleに格納する場合の型
         * 文字系　.putString
         * 日時　.putLong
         * ラジオボタン、スピナー　.putInt
         */
        //★ここに処理を書く



        // 入力値を保存したBundleオブジェクトを返す
        return inputValueBundle;
    }
}