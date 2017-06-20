package fokia.gq.zhifu.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.PasswordDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/19/17.
 */

public class SettingActivity extends BaseActivity {
    private TextView passwordHint;
    private EditText passwordEdit;
    private FloatingActionButton floatingActionButton;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase database;
    private PasswordDao passwordDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password_fragment);
        passwordHint = (TextView) findViewById(R.id.password_hint);
        passwordEdit = (EditText) findViewById(R.id.password_edit);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.password_setting);
        dbOpenHelper = new DBOpenHelper(SettingActivity.this, "ZhiFu.db", null, 1);
        database = dbOpenHelper.getWritableDatabase();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDao = new PasswordDao(database, passwordEdit.getText().toString());
                if (passwordDao.query(database).equals(passwordEdit.getText().toString())){
                    passwordEdit.setText("");
                    passwordHint.setText("请输入新密码");
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PasswordDao.update(database, passwordEdit.getText().toString());
                            Toast.makeText(SettingActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    passwordEdit.setText("");
                    Toast.makeText(SettingActivity.this, "密码不正确，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
