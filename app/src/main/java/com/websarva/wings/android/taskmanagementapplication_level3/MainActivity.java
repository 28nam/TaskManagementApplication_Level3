package com.websarva.wings.android.taskmanagementapplication_level3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * ★！このクラスは変更しないでください！★
 * メインアクティビティ
 */
public class MainActivity extends AppCompatActivity {
    // データベースヘルパーオブジェクト
    private TaskDatabaseHelper _helper;
    // データベース接続オブジェクト
    private SQLiteDatabase _db;
    Bundle bundle= new Bundle();

    /**
     * アクティビティが作成された際に呼ばれるメソッド
     * @param savedInstanceState 以前に保存されたアクティビティの状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ボタンとして扱うImageViewにタップ時のリスナを設定
        ImageView ivRegistration = findViewById(R.id.ivRegistration);
        ivRegistration.setOnClickListener(new ButtonClickListener());

        // 設定とは、、、　アプリの中で出来るタスクの表示の設定
        // 設定の初期値が格納されたSharedPreferencesオブジェクトを取得
        SharedPreferences defaultPreferences = MainActivity.this.getSharedPreferences(getString(R.string.default_settings_file_key), Context.MODE_PRIVATE);
        // 初期値が格納されていなかった場合
        if(defaultPreferences.getAll().size() <= 0) {
            // SharedPreferences.Editorオブジェクトを取得
            SharedPreferences.Editor editor = defaultPreferences.edit();
            // 初期値を格納する
            editor.putInt(getString(R.string.settings_sort_type), 0);
            editor.putInt(getString(R.string.settings_completed_task_display_type), 1);
            editor.putBoolean(getString(R.string.settings_important_state), true);
            editor.putBoolean(getString(R.string.settings_emergency_state), true);
            editor.putBoolean(getString(R.string.settings_note_state), true);
            editor.putBoolean(getString(R.string.settings_notset_state), true);
            // 格納した値を保存
            editor.commit();
        }

        // 設定値が格納されたSharedPreferencesオブジェクトを取得
        SharedPreferences preferences = MainActivity.this.getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        // 設定値がまだ保存されていない場合
        if(preferences.getAll().size() <= 0) {
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
        }
    }

    /**
     * アクティビティが表示される直前に呼ばれるメソッド
     */
    @Override
    protected void onStart() {
        super.onStart();

        // データベースヘルパーオブジェクトを生成
        // シングルトンの場合
        //_helper = TaskDatabaseHelper.getInstance(MainActivity.this);
        // シングルトンでない場合
        _helper = new TaskDatabaseHelper(MainActivity.this);

        // データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        _db = _helper.getWritableDatabase();

        // フラグメントマネージャーを取得
        FragmentManager manager = getSupportFragmentManager();
        // フラグメントトランザクションを取得
        FragmentTransaction transaction = manager.beginTransaction();

        // 登録されているタスク数を検索するSQL文字列の用意
        String allTaskSql = "SELECT _id FROM " + _helper.getDatabaseName() + " WHERE delete_at IS NULL";
        // SQLの実行
        Cursor allTaskCursor = _db.rawQuery(allTaskSql, null);
        // 削除されたタスクを除いて、登録されているタスクが存在しない場合…
        if(allTaskCursor.getCount() <= 0) {
            // フラグメントをFrameLayoutに置き換える
            transaction.replace(R.id.tasksFrame, UnregisteredFragment.class, bundle);
            // フラグメントトランザクションのコミット
            transaction.commit();
            // カーソルオブジェクトを解放
            // テキストには載っていませんが、開いたものを閉じる必要があるため、使用しなくなったものはclose()で解放します。
            allTaskCursor.close();
            // フラグメントを配置し、今後の処理を行わないので、returnする
            return;
        }
        // カーソルオブジェクトを解放
        allTaskCursor.close();

        // 設定部分のコードなので深く気にしない
        // 設定を取得
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        // 設定の「完了済みのタスクの表示」に、格納されている値が、「表示する」となる「0」だった場合…
        if(preferences.getInt(getString(R.string.settings_completed_task_display_type), -1) == 0) {
            // タスクリストフラグメントを表示する
            transaction.replace(R.id.tasksFrame, TaskListFragment.class, bundle);
            transaction.commit();
        }
        else {
            // 「表示しない」となる「1」だった場合…
            // 完了済みではないタスク数を検索するSQL文字列の用意
            String incompletedTaskSql = "SELECT _id FROM " + _helper.getDatabaseName() + " WHERE status != ? AND delete_at IS NULL";
            // SQLの？に該当する、完了済みとなる番号「3」を配列に入れる
            String[] selectionArgs = {"3"};
            // SQLの実行
            Cursor incompletedTaskCursor = _db.rawQuery(incompletedTaskSql, selectionArgs);

            // 検索結果の数に寄って分岐
            if(incompletedTaskCursor.getCount() <= 0) {
                // 検索結果が、0の場合…
                // 全タスク完了のフラグメントを表示する
                transaction.replace(R.id.tasksFrame, CompletedAllTasksFragment.class, bundle);
                transaction.commit();
            }
            else {
                // 検索結果が、1以上存在する場合…
                // タスクリストフラグメントを表示する
                transaction.replace(R.id.tasksFrame, TaskListFragment.class, bundle);
                transaction.commit();
            }

            // カーソルオブジェクトを解放
            incompletedTaskCursor.close();
        }
    }

    /**
     * アクティビティが破棄される際に呼ばれるメソッド
     */
    @Override
    public void onDestroy() {
        // データベースオブジェクトの解放
        _helper.close();
        _db.close();
        super.onDestroy();
    }

    /**
     * オプションメニューが作成される際に呼ばれるメソッド
     * @param menu メニューオブジェクト
     * @return メニューが正常に作成された場合はtrue
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューインフレータを取得
        MenuInflater inflater = getMenuInflater();
        // ホーム画面に表示するオプションメニューをインフレート
        inflater.inflate(R.menu.home_option_menu, menu);
        return true;
    }

    /**
     * オプションメニューの項目が選択された際に呼ばれるメソッド
     * @param item 選択されたメニューアイテム
     * @return 処理が正常に行われた場合はtrue
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        // オプションメニューの「設定」以外の場合は、その後の処理を行わず、親クラスの戻り値を返す
        if(itemId != R.id.optionsMenuSettings) {
            return super.onOptionsItemSelected(item);
        }

        // 設定画面へ遷移
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);

        return true;
    }

    /**
     * ボタンのリスナクラス
     */
    private class ButtonClickListener implements View.OnClickListener {
        /**
         * ボタンがクリックされた際の処理
         * @param v クリックされたビュー
         */
        @Override
        public void onClick(View v) {
            // 登録画面へ遷移
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    }
}