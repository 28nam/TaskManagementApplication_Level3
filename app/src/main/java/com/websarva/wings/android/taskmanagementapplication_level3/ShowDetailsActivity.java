package com.websarva.wings.android.taskmanagementapplication_level3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * ★コードを書いていきましょう★
 * 『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 * 少年漫画大好き女の先生は最近少女漫画にはまってます( ¯꒳¯̥̥ )
 * たぶんきゅんきゅんしたいんでしょうねｗそういうお年頃になりましたｗ
 * おすすめ漫画あったら教えてください。怖いやつ以外なら全ジャンルＯＫです！
 * 漫喫いきます。
 *
 * タスク詳細アクティビティ
 */
public class ShowDetailsActivity extends AppCompatActivity {

    /**
     * アクティビティが作成された際に呼ばれるメソッド
     * @param savedInstanceState 以前に保存された状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        //★　ここに処理を書く



    }

    /**
     * オプションメニューを作成する際に呼ばれるメソッド
     * @param menu 作成するメニュー
     * @return メニューが作成されたかどうか
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //★　ここに処理を書く



        return true;
    }

    /**
     * オプションメニューのアイテムが選択された際に呼ばれるメソッド
     * @param item 選択されたメニューアイテム
     * @return 処理が成功したかどうか
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 戻り値用の変数を用意
        boolean returnVal = true;

        //★　ここに処理を書く



        return returnVal;
    }

    /**
     * ボタンがクリックされた際のリスナークラス
     */
    private class ButtonClickListener implements View.OnClickListener {
        /**
         * ボタンがクリックされた際に呼ばれるメソッド
         * @param v クリックされたビューオブジェクト
         */
        @Override
        public void onClick(View v) {
            //★　ここに処理を書く


        }
    }
}