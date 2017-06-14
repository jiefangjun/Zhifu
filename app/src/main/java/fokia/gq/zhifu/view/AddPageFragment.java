package fokia.gq.zhifu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.R;

/**
 * Created by archie on 6/14/17.
 */

public class AddPageFragment extends PageFragment {

    //获取activity float button 用来保存操作
    private FloatingActionButton floatingActionButton;

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

    @Override
    public void onResume() {
        super.onResume();
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
                Log.i("saveDate","保存数据");
                Snackbar.make(v, "保存成功", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    private void saveDate(){
        switch (mPage){
            case 1:
                InaccountDao inaccountDao = new InaccountDao(db, null, 10, "2017", "娱乐", "妓院", "小红不错");
                inaccountDao.insert(db, null);
                break;
            case 2:
                InaccountDao inaccountDao1 = new InaccountDao(db, null, 10, "2017", "娱乐", "妓院", "小红不错");
                inaccountDao1.insert(db, null);
                break;
            case 3:
                InaccountDao inaccountDao2 = new InaccountDao(db, null, 10, "2017", "娱乐", "妓院", "小红不错");
                inaccountDao2.insert(db, null);
                break;
            default:
                break;
        }


    }

    private View createAddIncome(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addincome_fragment, container, false);
        return view;
    }

    private View createAddOutlay(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addoutlay_fragment, container, false);
        return view;
    }

    private View createAddNote(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addnote_fragment, container, false);
        return view;
    }


}
