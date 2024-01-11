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
 * タスク未登録フラグメント
 */
public class UnregisteredFragment extends Fragment {
    // コンストラクタ
    public UnregisteredFragment() {
        super(R.layout.fragment_unregistered);
    }

    /**
     * ★山下コード★
     * ビューが作成された際に呼ばれるメソッド
     * @param view 作成されたビュー
     * @param savedInstanceState 保存されたインスタンスの状態
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}