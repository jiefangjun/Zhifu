package fokia.gq.zhifu.Dialog;

import android.content.Context;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.zip.Inflater;

import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/22/17.
 */

public class UpdateIncomeDialog extends BaseDialog {

    private EditText updateMoney;
    private EditText updateHandler;
    private EditText updateNote;
    private TextView updateDate;
    private ImageButton updateOk;
    private TextView updateHint;
    private Spinner spinner;

    public UpdateIncomeDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return BaseDialog.DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
        updateMoney = (EditText) view.findViewById(R.id.update_money);
        updateHandler = (EditText) view.findViewById(R.id.update_handler_address);
        updateNote = (EditText) view.findViewById(R.id.update_note);
        updateHint = (TextView) view.findViewById(R.id.update_hint);
        spinner = (Spinner) view.findViewById(R.id.update_type);
        updateDate = (TextView) view.findViewById(R.id.update_date);
        updateOk = (ImageButton) view.findViewById(R.id.update_ok);

        saveData();

        return view;
    }


    public BaseDialog setData(Double money, String handler, String type, String note, String date){
        updateMoney.setText(Double.toString(money));
        updateHandler.setText(handler);
        updateNote.setText(note);
        updateDate.setText(date);

        initSpinner(spinner);

        return this;
    }

    public void saveData(){
        updateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InaccountDao inaccountDao = new InaccountDao(db, null, Double.parseDouble(updateMoney.getText().toString()), updateDate.getText().toString(), type, updateHandler.getText().toString(), updateNote.getText().toString());
                inaccountDao.update(db, updateDate.getText().toString());
                dismiss();
            }
        });
    }

}
