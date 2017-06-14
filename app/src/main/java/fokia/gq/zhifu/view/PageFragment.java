package fokia.gq.zhifu.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fokia.gq.zhifu.Adapter.NoteAdapter;
import fokia.gq.zhifu.Adapter.OutlayAdapter;
import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.Adapter.IncomeAdapter;
import fokia.gq.zhifu.Dao.NoteDao;
import fokia.gq.zhifu.Dao.OutaccountDao;
import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.Income;
import fokia.gq.zhifu.model.Note;
import fokia.gq.zhifu.model.Outlay;

import static fokia.gq.zhifu.Dao.InaccountDao.incomes;
import static fokia.gq.zhifu.Dao.NoteDao.noteList;
import static fokia.gq.zhifu.Dao.OutaccountDao.outlays;

/**
 * Created by archie on 6/12/17.
 */

public class PageFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    protected int mPage;
    protected SQLiteDatabase db;
    protected DBOpenHelper dbOpenHelper;
    private IncomeAdapter incomeAdapter;


    //填充的视图
    protected View pageView;

    public static PageFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        dbOpenHelper = new DBOpenHelper(getActivity(), "ZhiFu.db", null, 1);
        db = dbOpenHelper.getReadableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch(mPage){
            case 1:
                pageView = createIncomeView(inflater, container, savedInstanceState);
                break;
            case 2:
                pageView = createOutlayView(inflater, container, savedInstanceState);
                break;
            case 3:
                pageView = createIncomeView(inflater, container, savedInstanceState);
                break;
            case 4:
                pageView = createNoteView(inflater, container, savedInstanceState);
            default:
            break;
        }
        return pageView;
    }



    protected View createIncomeView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        InaccountDao.getIncomes(db);
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        if (incomes.size() == 0){
            //由于FragmentPageAdapter的预加载机制，换用信息提示方式，不再使用toast。
            //Toast.makeText(getActivity(), "没有数据，请添加", Toast.LENGTH_SHORT).show();
            for (int i=0; i<5; i++){
                incomes.add(new Income(0.0, "嫖娼", "昨天", "娱乐", "怡红楼"));
            }
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        if(incomeAdapter == null){
            incomeAdapter = new IncomeAdapter(incomes);
        }
        recyclerView.setAdapter(incomeAdapter);
        //recyclerView.setAdapter(incomeAdapter);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    protected View createOutlayView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        OutaccountDao.getOutlays(db);
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        if (outlays.size() == 0){
            //由于FragmentPageAdapter的预加载机制，换用信息提示方式，不再使用toast。
            //Toast.makeText(getActivity(), "没有数据，请添加", Toast.LENGTH_SHORT).show();
            for (int i=0; i<5; i++){
                outlays.add(new Outlay(0.0, "嫖娼", "昨天", "娱乐", "怡红楼"));
            }
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        OutlayAdapter adapter = new OutlayAdapter(outlays);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    /*private View createOverView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

    }*/

    protected View createNoteView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        NoteDao.getNotes(db);
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        if(noteList.size() == 0){
            for (int i = 0; i<5; i++){
                noteList.add(new Note("辣鸡"));
            }
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager
                (3, StaggeredGridLayoutManager.VERTICAL);
        NoteAdapter adapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        return view;
    }
}
