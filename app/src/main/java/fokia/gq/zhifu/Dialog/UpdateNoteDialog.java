package fokia.gq.zhifu.Dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.zip.Inflater;

import fokia.gq.zhifu.Dao.NoteDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/22/17.
 */

public class UpdateNoteDialog extends BaseDialog {

    private int id;

    private EditText updateNote;
    private ImageButton updateOk;
    public UpdateNoteDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return BaseDialog.DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.update_note_dialog, null);
        updateNote = (EditText) view.findViewById(R.id.update_note);
        updateOk = (ImageButton) view.findViewById(R.id.update_note_ok);

        saveData();
        return view;
    }

    public BaseDialog setData(String content, int id){
        updateNote.setText(content);
        updateNote.setSelection(content.length());
        this.id = id;
        return this;
    }

    public void saveData(){
        updateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDao noteDao = new NoteDao(db, null, updateNote.getText().toString());
                noteDao.update(db, Integer.toString(id));
                dismiss();
            }
        });
    }
}
