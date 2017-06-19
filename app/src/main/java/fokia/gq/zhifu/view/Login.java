package fokia.gq.zhifu.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.PasswordDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/13/17.
 */

public class Login extends BaseActivity {
    private TextView passwordEdit;
    private FloatingActionButton loginButton;
    private PasswordDao passwordDao;
    public DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        passwordEdit = (TextView) findViewById(R.id.password_edit);
        loginButton = (FloatingActionButton) findViewById(R.id.login_button);
        dbOpenHelper = new DBOpenHelper(this, "ZhiFu.db", null, 1);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                passwordDao = new PasswordDao(db, passwordEdit.getText().toString());
                if (passwordEdit.getText().toString().equals(passwordDao.query(db))) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                    dialog.setItems(new String[]{"密码错误", "初始密码123"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    dialog.cancel();
                                    break;
                                case 1:
                                    dialog.cancel();
                                    break;
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}
