package com.websarva.wings.android.taskmanagementapplication_level3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

/**
 * ★！このクラスは変更しないでください！★
 * ★通知などの設定をしているクラスになります
 * 設定アクティビティ
 */
public class SettingsActivity extends AppCompatActivity {
    // 設定のそれぞれのオブジェクトを格納するフィールド
    RadioGroup _rgSort;
    RadioGroup _rgFinishedTaskSetting;

    CheckBox _cbImportant;
    CheckBox _cbEmergency;
    CheckBox _cbNote;
    CheckBox _cbNotSet;

    /**
     * ★山下コード★
     * アクティビティが作成される際に呼ばれるメソッド
     * @param savedInstanceState 以前に保存された状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 設定のオブジェクトをそれぞれ取得
        _rgSort = findViewById(R.id.rgSort);
        _rgFinishedTaskSetting = findViewById(R.id.rgFinishedTaskSetting);

        _cbImportant = findViewById(R.id.cbImportant);
        _cbEmergency = findViewById(R.id.cbEmergency);
        _cbNote = findViewById(R.id.cbNote);
        _cbNotSet = findViewById(R.id.cbNotSet);

        // ボタンにそれぞれタップ時のリスナを設定
        Button btReset = findViewById(R.id.btReset);
        btReset.setOnClickListener(new ButtonClickListener());

        Button btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new ButtonClickListener());

        // アクションバーを取得
        ActionBar actionBar = getSupportActionBar();
        // アクションバーの「戻る」メニューを有効に設定
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 設定値が格納されたSharedPreferencesオブジェクトを取得
        SharedPreferences preferences = SettingsActivity.this.getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        // 設定値をそれぞれのオブジェクトに反映させる
        switch(preferences.getInt(getString(R.string.settings_sort_type), -1)) {
            case 0:
                //「直近の日付のタスクから表示する」のラジオボタンを選択状態にする
                _rgSort.check(R.id.rbNearDate);
                break;
            case 1:
                //「将来の日付のタスクから表示する」のラジオボタンを選択状態にする
                _rgSort.check(R.id.rbFarDate);
                break;
            default:
                break;
        }

        switch(preferences.getInt(getString(R.string.settings_completed_task_display_type), -1)) {
            case 0:
                //「表示する」のラジオボタンを選択状態にする
                _rgFinishedTaskSetting.check(R.id.rbShowTask);
                break;
            case 1:
                //「表示しない」のラジオボタンを選択状態にする
                _rgFinishedTaskSetting.check(R.id.rbHideTask);
                break;
            default:
                break;
        }

        _cbImportant.setChecked(preferences.getBoolean(getString(R.string.settings_important_state), true));
        _cbEmergency.setChecked(preferences.getBoolean(getString(R.string.settings_emergency_state), true));
        _cbNote.setChecked(preferences.getBoolean(getString(R.string.settings_note_state), true));
        _cbNotSet.setChecked(preferences.getBoolean(getString(R.string.settings_notset_state), true));
    }

    /**
     * ★山下コード★
     * オプションメニューアイテムが選択された際に呼ばれるメソッド
     * @param item 選択されたメニューアイテム
     * @return 処理が成功した場合はtrue、それ以外はfalse
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 選択されたオプションメニューが、戻るメニュー以外の場合…
        if(item.getItemId() != android.R.id.home) {
            // 親クラスの同名メソッドを呼び出し、そのままreturnする
            return super.onOptionsItemSelected(item);
        }

        // Bundleオブジェクトを生成
        Bundle bundle = new Bundle();
        // 引き継ぎデータをBundleに格納
        bundle.putString("dialogMessage", getString(R.string.settings_cancel_dialog_msg));
        bundle.putString("positiveMessage", getString(R.string.btn_revocation));
        // 確認ダイアログフラグメントオブジェクトを生成
        CancelConfirmationDialogFragment cancelDialogFragment = new CancelConfirmationDialogFragment();
        // 引き継ぎデータをダイアログフラグメントに格納
        cancelDialogFragment.setArguments(bundle);
        // ダイアログ表示
        cancelDialogFragment.show(getSupportFragmentManager(), "CancelConfirmationDialogFragment");

        return true;
    }

    /**
     * ★山下コード★
     * ボタンがクリックされた際のリスナークラス
     */
    private class ButtonClickListener implements View.OnClickListener {
        /**
         * ★山下コード★
         * ボタンがクリックされた際に呼ばれるメソッド
         * @param v クリックが発生したViewオブジェクト
         */
        @Override
        public void onClick(View v) {
            // タップされたidを取得
            int id = v.getId();

            // 引き継ぎデータを格納するBundleオブジェクトを生成
            Bundle bundle = new Bundle();

            // タップされたボタンに応じて処理を行う
            switch(id) {
                case R.id.btReset:
                    // 確認ダイアログフラグメントオブジェクトを生成
                    SettingsResetConfirmationDialogFragment resetDialogFragment = new SettingsResetConfirmationDialogFragment();
                    // 引き継ぎデータをダイアログフラグメントに格納
                    resetDialogFragment.setArguments(bundle);
                    // ダイアログ表示
                    resetDialogFragment.show(getSupportFragmentManager(), "SettingsResetConfirmationDialogFragment");
                    break;
                case R.id.btSave:
                    // Bundleオブジェクトに編集した設定値を格納
                    switch(_rgSort.getCheckedRadioButtonId()) {
                        case R.id.rbNearDate:
                            bundle.putInt(getString(R.string.settings_sort_type), 0);
                            break;
                        case R.id.rbFarDate:
                            bundle.putInt(getString(R.string.settings_sort_type), 1);
                            break;
                        default:
                            break;
                    }

                    switch(_rgFinishedTaskSetting.getCheckedRadioButtonId()) {
                        case R.id.rbShowTask:
                            bundle.putInt(getString(R.string.settings_completed_task_display_type), 0);
                            break;
                        case R.id.rbHideTask:
                            bundle.putInt(getString(R.string.settings_completed_task_display_type), 1);
                            break;
                        default:
                            break;
                    }

                    bundle.putBoolean(getString(R.string.settings_important_state), _cbImportant.isChecked());
                    bundle.putBoolean(getString(R.string.settings_emergency_state), _cbEmergency.isChecked());
                    bundle.putBoolean(getString(R.string.settings_note_state), _cbNote.isChecked());
                    bundle.putBoolean(getString(R.string.settings_notset_state), _cbNotSet.isChecked());

                    // 確認ダイアログフラグメントオブジェクトを生成
                    SettingsSaveConfirmationDialogFragment saveDialogFragment = new SettingsSaveConfirmationDialogFragment();
                    // 引き継ぎデータをダイアログフラグメントに格納
                    saveDialogFragment.setArguments(bundle);
                    // ダイアログ表示
                    saveDialogFragment.show(getSupportFragmentManager(), "SettingsSaveConfirmationDialogFragment");
                    break;
                default:
                    break;
            }
        }
    }
}