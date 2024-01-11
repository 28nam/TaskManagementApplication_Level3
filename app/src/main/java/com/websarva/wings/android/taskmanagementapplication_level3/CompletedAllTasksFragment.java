package com.websarva.wings.android.taskmanagementapplication_level3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ★！このクラスは変更しないでください！★
 * すべてのタスクが完了した際にホーム画面に表示させるフラグメント
 */
public class CompletedAllTasksFragment extends Fragment {
    // コンストラクタ
    public CompletedAllTasksFragment() {
        super(R.layout.fragment_completed_all_tasks);
    }

    /**
     * ★山下コード★
     * ビューが作成された際に呼び出されます。
     * @param view 作成されたビュー
     * @param savedInstanceState 以前のインスタンスの状態
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}