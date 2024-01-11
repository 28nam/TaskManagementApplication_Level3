package com.websarva.wings.android.taskmanagementapplication_level3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ★！このクラスは変更しないでください！★
 * タスク一覧フラグメント
 */
public class TaskListFragment extends Fragment {
    // データベースヘルパーオブジェクト
    private TaskDatabaseHelper _helper;
    // データベース接続オブジェクト
    private SQLiteDatabase _db;
    // データを格納するListを生成
    private List<Map<String, Object>> _tasks = new ArrayList<>();

    // コンストラクタ
    public TaskListFragment() {
        super(R.layout.fragment_task_list);
    }

    /**
     * ★山下コード★
     * ビューが作成された際の処理
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // データベースヘルパーオブジェクトを生成
        _helper = new TaskDatabaseHelper(getActivity());

        // データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        _db = _helper.getWritableDatabase();

        // タスクリストの中身を全削除
        _tasks.clear();

        // データベースから表示するタスクの情報取得
        _tasks = getTasks();

        //★ここは習ってない知識
        //アダプタオブジェクトを生成
        TaskListAdapter adapter = new TaskListAdapter(getActivity(), _tasks);
        // タスクを一覧表示するListViewオブジェクトを取得
        ListView lvTasks = view.findViewById(R.id.lvTasks);
        // ListViewにアダプタを設定
        lvTasks.setAdapter(adapter);
        // ListViewにリスナを設定
        lvTasks.setOnItemClickListener(new ListItemClickListener());
    }

    /**
     * ★山下コード★
     * データベースからタスク情報を取得する
     * @return
     */
    private List<Map<String, Object>> getTasks() {
        // 全検索SQL文字列の用意
        String sql = "SELECT _id, status, name, finish_at, color FROM " + _helper.getDatabaseName() + " WHERE delete_at IS NULL";
        // 「？」に代入する文字列のリストを生成
        List<String> selectionArgsList = new ArrayList<>();
        // 設定を取得
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        // 「完了済みのタスクの表示」の設定値に応じて分岐
        switch(preferences.getInt(getString(R.string.settings_completed_task_display_type), -1)) {
            case 0:
                // 表示する場合、何も条件を追加しない
                break;
            case 1:
                // 表示しない場合、タスクの対応状況が「☆完了済み」を示す、「3」以外のタスクを取得するSQLを追加
                sql += " AND status != ?";
                // 「？」に合わせて、「☆完了済み」を示す「3」をリストに追加
                selectionArgsList.add("3");
                break;
            default:
                break;
        }

        // 設定の「表示するタスクグループ」がチェックされているタスクのみ表示を行う
        // IN句の中身が空の場合、SQLの構文エラーとなるため、最初にあり得ないエラー値を追加しておく
        sql += " AND task_group IN(?";
        // タスクグループの対応値としてあり得ない「-1」を最初に追加しておく
        selectionArgsList.add("-1");
        // 「重要」のチェックボックスがチェックされているか…
        if(preferences.getBoolean(getString(R.string.settings_important_state), true)) {
            // チェックされている場合、IN句に対象のIDを追加する
            sql += ", ?";
            // 対象のIDとなる値をリストに追加
            selectionArgsList.add("0");
        }
        // 「緊急」のチェックボックスがチェックされているか…
        if(preferences.getBoolean(getString(R.string.settings_emergency_state), true)) {
            // チェックされている場合、IN句に対象のIDを追加する
            sql += ", ?";
            // 対象のIDとなる値をリストに追加
            selectionArgsList.add("1");
        }
        // 「メモ」のチェックボックスがチェックされているか…
        if(preferences.getBoolean(getString(R.string.settings_note_state), true)) {
            // チェックされている場合、IN句に対象のIDを追加する
            sql += ", ?";
            // 対象のIDとなる値をリストに追加
            selectionArgsList.add("2");
        }
        // 「未設定」のチェックボックスがチェックされているか…
        if(preferences.getBoolean(getString(R.string.settings_notset_state), true)) {
            // チェックされている場合、IN句に対象のIDを追加する
            sql += ", ?";
            // 対象のIDとなる値をリストに追加
            selectionArgsList.add("3");
        }
        // IN句の閉じ括弧をSQLに追加
        sql += ")";

        // 並び替え方法を設定値から取得し、値に応じて並び替えるSQLを追加
        switch(preferences.getInt(getString(R.string.settings_sort_type), -1)) {
            case 0:
                // 「直近の日付のタスクから表示する」が選択されている場合、完了日時を昇順にするSQLを追加
                sql += " ORDER BY finish_at ASC";
                break;
            case 1:
                // 「将来の日付のタスクから表示する」が選択されている場合、完了日時を降順にするSQLを追加
                sql += " ORDER BY finish_at DESC";
                break;
            default:
                break;
        }

        // バインドする値を格納する変数を用意
        String[] selectionArgs = new String[0];
        // Stringのリストを、String配列にしてバインドする変数に渡す
        selectionArgs = selectionArgsList.toArray(selectionArgs);

        // SQLの実行
        Cursor cursor = _db.rawQuery(sql, selectionArgs);

        // データを格納するListを生成
        List<Map<String, Object>> tasks = new ArrayList<>();

        // 検索したデータから必要なカラムをListに格納
        while(cursor.moveToNext()) {
            // タスク1つ分の表示データを格納するMapを生成
            Map<String, Object> item = new HashMap<>();

            // レコードのインデックスを登録
            int taskIndex = cursor.getColumnIndex("_id");
            item.put("_id", cursor.getInt(taskIndex));

            // リストに表示する内容のカラムインデックスをそれぞれ取得し、インデックスを元に値をMapに格納
            int taskStatusIndex = cursor.getColumnIndex("status");
            String[] taskStatusArray = getResources().getStringArray(R.array.sp_task_status);
            item.put("taskStatus", taskStatusArray[cursor.getInt(taskStatusIndex)]);

            int taskTitleIndex = cursor.getColumnIndex("name");
            item.put("taskDescription", cursor.getString(taskTitleIndex));

            int finishAtIndex = cursor.getColumnIndex("finish_at");
            item.put("finishDateTime", cursor.getLong(finishAtIndex));

            int taskBarColorIndex = cursor.getColumnIndex("color");
            item.put("taskBarColor", cursor.getInt(taskBarColorIndex));

            // 表示内容を登録したMapをリストに追加
            tasks.add(item);
        }

        // カーソルオブジェクトを解放
        // テキストには載っていませんが、開いたものを閉じる必要があるため、使用しなくなったものはclose()で解放します。
        // 記載していない場合、「W/System: A resource failed to call close.」
        // 要約すると「呼び出しに失敗したリソースが閉じます。」とエラーが出力されます。
        cursor.close();

        return tasks;
    }

    /**
     * ★山下コード★
     * フラグメントが破棄される際の処理
     */
    @Override
    public void onDestroy() {
        // データベースオブジェクトの解放
        // テキストには載っていませんが、開いたものを閉じる必要があるため、使用しなくなったものはclose()で解放します。
        // 記載していない場合、「W/System: A resource failed to call close.」
        // 要約すると「呼び出しに失敗したリソースが閉じます。」とエラーが出力されます。
        _helper.close();
        _db.close();
        super.onDestroy();
    }

    /**
     * ★山下コード★
     * ★ここは習ってない知識
     * タスクリストのアダプタクラス
     */
    private class TaskListAdapter extends BaseAdapter {
        // レイアウト用のインフレータ
        LayoutInflater _inflater;
        // リストデータ
        List<Map<String, Object>> _listData;

        // コンストラクタ
        public TaskListAdapter(Context context, List<Map<String, Object>> listData) {
            // レイアウトインフレータを取得し、フィールドに保存
            _inflater = LayoutInflater.from(context);
            // 引数のリストデータをフィールドに保存
            _listData = listData;
        }

        /**
         * ★山下コード★
         * リストアイテムの数を返す
         * @return
         */
        @Override
        public int getCount() {
            return _listData.size();
        }

        /**
         * ★山下コード★
         * 指定位置のアイテムを返す
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return _listData.get(position);
        }

        /**
         * ★山下コード★
         * アイテムのIDを返す
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * ★山下コード★
         * 指定位置のビューを返す
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1行分のリスト項目が生成されていなかった場合
            if(convertView == null) {
                // 1行分のリスト項目を生成
                convertView = _inflater.inflate(R.layout.row, null);
            }

            // 「position」番目のタスクの情報を取得
            Map<String, Object> task = _tasks.get(position);
            // リストに表示する項目を取得し、TextViewに設定
            String taskStatus = (String) task.get("taskStatus");
            TextView tvTaskStatusRow = convertView.findViewById(R.id.tvTaskStatusRow);
            tvTaskStatusRow.setText(taskStatus);

            String taskTitle = (String) task.get("taskDescription");
            TextView tvTaskTitleRow = convertView.findViewById(R.id.tvTaskTitleRow);
            tvTaskTitleRow.setText(taskTitle);

            long finishDateTimeLong = (long) task.get("finishDateTime");
            // 完了日時のミリ秒を元にDate型をインスタンス
            Date finishDateTime = new Date(finishDateTimeLong);
            TextView tvFinishAtRow = convertView.findViewById(R.id.tvFinishAtRow);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            tvFinishAtRow.setText(dateFormat.format(finishDateTime));

            // タスクバーの色のIDを取得
            int taskBarColorId = -1;
            taskBarColorId = (Integer) task.get("taskBarColor");
            // IDに応じて色を指定
            switch(taskBarColorId) {
                case 0:
                    // 「青」が指定されているため、1行分のレイアウトの背景色に「青」を指定
                    convertView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_blue));
                    break;
                case 1:
                    // 「赤」が指定されているため、1行分のレイアウトの背景色に「赤」を指定
                    convertView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_red));
                    break;
                case 2:
                    // 「緑」が指定されているため、1行分のレイアウトの背景色に「緑」を指定
                    convertView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_green));
                    break;
                case 3:
                    // 「未設定」が指定されているため、1行分のレイアウトの背景色に「白」を指定
                    convertView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
                    break;
                default:
                    // 意図しない値であることをエラーログに表示
                    Log.e("taskBarColor", "タスクバーの色が選択されていません");
                    break;
            }

            return convertView;
        }
    }

    /**
     * ★山下コード★
     * リストアイテムがクリックされた際のリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        /**
         * ★山下コード★
         * リストアイテムがクリックされた際に呼ばれるメソッド
         * @param parent   クリックが発生した AdapterView
         * @param view     クリックされた View
         * @param position クリックされた位置
         * @param id       クリックされたアイテムのID
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップされた行のデータを取得
            Map<String, Object> selectedTask = _tasks.get(position);
            // タップされたタスクのIDを取得
            int selectedId = (Integer) selectedTask.get("_id");
            // インテントオブジェクトを生成し、詳細画面へ遷移
            Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
            // タップされたタスクのIDを登録
            intent.putExtra("_id", selectedId);
            // アクティビティを開始
            startActivity(intent);
        }
    }
}