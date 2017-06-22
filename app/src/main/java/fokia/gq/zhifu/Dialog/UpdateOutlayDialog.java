package fokia.gq.zhifu.Dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import fokia.gq.zhifu.Dao.OutaccountDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/22/17.
 */

public class UpdateOutlayDialog extends BaseDialog {
    private EditText updateMoney;
    private EditText updateAddress;
    private EditText updateNote;
    private TextView updateDate;
    private ImageButton updateOk;
    private TextView updateHint;
    private Spinner spinner;

    public UpdateOutlayDialog(Context context) {
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
        updateAddress = (EditText) view.findViewById(R.id.update_handler_address);
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
        updateAddress.setText(handler);
        updateNote.setText(note);
        updateDate.setText(date);
        //spinner.setSelection(1);

        return this;
    }

    public void saveData(){
        updateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutaccountDao outaccountDao = new OutaccountDao(db, null, Double.parseDouble(updateMoney.getText().toString()), updateNote.getText().toString(), updateDate.getText().toString(), "娱乐", updateAddress.getText().toString());
                outaccountDao.update(db, updateDate.getText().toString());
                dismiss();
            }
        });
    }
}
