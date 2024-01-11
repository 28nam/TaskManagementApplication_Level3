package com.websarva.wings.android.taskmanagementapplication_level3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ★コードを書いていきましょう★
 * 『//★』が書いてあるコメントの下に処理を書いていきましょう！（1行とは限りません）
 * レベル3は本当に難しいですチャレンジしているだけでエライ！！
 *
 * データベースへスパークラス
 */
public class TaskDatabaseHelper extends SQLiteOpenHelper {
    // データベース名の文字列
    private static final String DATABASE_NAME = "taskmanagementlevel1.db";
    // データベースバージョンの文字列
    private static final int DATABASE_VERSION = 1;
    // テーブル名の文字列
    private static final String TABLE_NAME = "task";

    /**
     * ★山下コード★
     * コンストラクタ
     * @param context コンテキスト
     */
    public TaskDatabaseHelper(Context context) {
        // 親コンストラクタの呼び出し
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * テーブルが作成される際に呼ばれるメソッド
     * @param db データベース
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //★　ここに処理を書く



    }

    /**
     * データベースのバージョンが上がった際に呼ばれるメソッド
     * @param db         データベース
     * @param oldVersion 古いバージョン
     * @param newVersion 新しいバージョン
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * データベース名を取得するメソッド
     * @return データベース名
     */
    public String getDatabaseName() {
        return TABLE_NAME;
    }
}

