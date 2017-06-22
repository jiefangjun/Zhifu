package fokia.gq.zhifu.fragmenttest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.Dao.NoteDao;
import fokia.gq.zhifu.Dao.OutaccountDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/14/17.
 */


public class AddPageFragment extends PageFragment implements TimePickerFragment.DataCallBack{

    //获取activity float button 用来保存操作
    private FloatingActionButton floatingActionButton;

    private EditText editMoney;
    private EditText editHandler;
    private TextView textDate;
    private EditText editNote;
    private ImageButton calendarButton;
    private Spinner editType;

    private int currentPage;

    private EditText editFlag;
    private String flagContent;


    public static AddPageFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AddPageFragment addPageFragment = new AddPageFragment();
        addPageFragment.setArguments(args);
        return addPageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = dbOpenHelper.getWritableDatabase();
    }


    //为了使函数命名更加规范，重写onCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch(mPage){
            case 1:
                pageView = createAddIncome(inflater, container, savedInstanceState);
                break;
            case 2:
                pageView = createAddOutlay(inflater, container, savedInstanceState);
                break;
            case 3:
                pageView = createAddNote(inflater, container, savedInstanceState);
                break;
            default:
                break;
        }
        return pageView;
    }

    @Override
    public void initData() {
        currentPage = mPage;
        if(currentPage ==1 || currentPage == 2)
        {
            initControls();
        }else {
            editFlag = (EditText) pageView.findViewById(R.id.flag_edit);
        }
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
                Snackbar.make(v, "保存成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initControls(){
        editMoney = (EditText) pageView.findViewById(R.id.update_money);
        editHandler = (EditText) pageView.findViewById(R.id.update_handler_address);
        editNote = (EditText) pageView.findViewById(R.id.update_note);
        editType = (Spinner) pageView.findViewById(R.id.update_type);
        calendarButton = (ImageButton) pageView.findViewById(R.id.payment_calendar);
        textDate = (TextView) pageView.findViewById(R.id.payment_date);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPick(textDate);
            }
        });
    }

    private void showDialogPick(final TextView timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //实例化TimePickerDialog对象
        final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            //选择完时间后会调用该回调函数
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(" "  + hourOfDay + ":" + minute);
                //设置TextView显示最终选择的时间
                timeText.setText(time);
            }
        }, hour, minute, true);
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                time.append(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                //选择完日期后弹出选择时间对话框
                timePickerDialog.show();
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }

    private void saveDate(){
        switch (currentPage){
            case 1:
                InaccountDao inaccountDao = new InaccountDao(db, null, getEditTextDouble(editMoney.getText().toString()), textDate.getText().toString(),
                        "娱乐", editHandler.getText().toString(),  editNote.getText().toString());
                inaccountDao.insert(db, null);
                break;
            case 2:
                OutaccountDao outaccountDao = new OutaccountDao(db, null, getEditTextDouble(editMoney.getText().toString()), editNote.getText().toString(),
                        textDate.getText().toString(), "娱乐", editHandler.getText().toString());
                outaccountDao.insert(db, null);
                break;
            case 3:
                flagContent = editFlag.getText().toString();
                NoteDao noteDao = new NoteDao(db, null, flagContent);
                noteDao.insert(db, null);
                break;
            default:
                break;
        }


    }

    @Override
    public void getData(String data) {
        //data即为fragment调用该函数传回的日期时间
       textDate.setText(data);
    }

    private double getEditTextDouble(String s)
    {
        if (s.equals("") || s.equals("."))
        {
            return 0;
        }
        else
        {
            return Double.parseDouble(s);
        }
    }

    private View createAddIncome(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addpayment_fragment, container, false);
        return view;
    }

    private View createAddOutlay(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addpayment_fragment, container, false);
        return view;
    }

    private View createAddNote(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addnote_fragment, container, false);
        return view;
    }


}
