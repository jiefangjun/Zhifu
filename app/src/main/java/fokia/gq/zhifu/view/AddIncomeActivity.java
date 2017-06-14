package fokia.gq.zhifu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;

import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/14/17.
 */

public class AddIncomeActivity extends AppCompatActivity{
    private EditText money;
    private EditText handler;
    private Spinner type;
    private EditText note;
    private EditText date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addincome_fragment);

    }
}
