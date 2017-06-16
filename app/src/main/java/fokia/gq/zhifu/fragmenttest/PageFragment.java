package fokia.gq.zhifu.fragmenttest;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.goyourfly.multiple.adapter.MultipleAdapter;
import com.goyourfly.multiple.adapter.MultipleSelect;
import com.goyourfly.multiple.adapter.menu.CustomMenuBar;
import com.goyourfly.multiple.adapter.menu.MenuBar;
import com.goyourfly.multiple.adapter.menu.SimpleDeleteMenuBar;
import com.goyourfly.multiple.adapter.menu.SimpleDeleteSelectAllMenuBar;
import com.goyourfly.multiple.adapter.viewholder.color.ColorFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import fokia.gq.zhifu.Adapter.NoteAdapter;
import fokia.gq.zhifu.Adapter.OutlayAdapter;
import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.Adapter.IncomeAdapter;
import fokia.gq.zhifu.Dao.NoteDao;
import fokia.gq.zhifu.Dao.OutaccountDao;
import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.ChangeListener;
import fokia.gq.zhifu.model.Income;
import fokia.gq.zhifu.model.MyMenuBar;
import fokia.gq.zhifu.model.Note;
import fokia.gq.zhifu.model.Outlay;
import fokia.gq.zhifu.model.RecyclerItemClickListener;

import static fokia.gq.zhifu.Dao.InaccountDao.incomes;
import static fokia.gq.zhifu.Dao.NoteDao.noteList;
import static fokia.gq.zhifu.Dao.OutaccountDao.outlays;

/**
 * Created by archie on 6/12/17.
 */

public class PageFragment extends BaseFragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    protected int mPage;
    protected SQLiteDatabase db;
    protected DBOpenHelper dbOpenHelper;

    private NoteAdapter noteAdapter;
    private IncomeAdapter incomeAdapter;
    private OutlayAdapter outlayAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    //加载页
    private int flag;



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

    @Override
    public void initData() {
        flag = mPage;
        switch (flag){
            case 1:
                getIncomes();
                break;
            case 2:
                getOutlays();
                break;
            case 3:
                break;
            case 4:
                getFlags();
                break;
            default:
                break;
        }
        initSwipeRefresh(pageView);
        //打开页面即刷新
        refreshData();
    }

    private void getIncomes(){
        if(incomes.size() == 0){
            InaccountDao.getIncomes(db);
        }
        if (incomes.size() == 0) {
            //由于FragmentPageAdapter的预加载机制，换用信息提示方式，不再使用toast。
            //使用懒加载，继续使用Toast
            Toast.makeText(getActivity(), "没有数据，请添加", Toast.LENGTH_SHORT).show();
        }
    }

    private void getOutlays(){
        if(outlays.size() == 0){
           OutaccountDao.getOutlays(db);
        }
        if (outlays.size() == 0) {
            Toast.makeText(getActivity(), "没有数据，请添加", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFlags(){
        if (noteList.size() ==0 ){
            NoteDao.getNotes(db);
        }
        if (noteList.size() == 0){
            Toast.makeText(getActivity(), "没有数据，请添加", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPayments(){
        //TODO 获取支出和收入数据
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

    private void initSwipeRefresh(View view){

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData(){
        swipeRefreshLayout.setRefreshing(true);
        //TODO 判断是否需要刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.getAdapter().notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1200);
    }


    protected View createIncomeView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        if(incomeAdapter == null){
            incomeAdapter = new IncomeAdapter(incomes);
        }
        //TODO 一个有趣的问题
        if(recyclerView.getAdapter() == null){
            recyclerView.setAdapter(incomeAdapter);
        }
        addItemDecoration(layoutManager);
        return view;
    }

    protected View createOutlayView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        if(outlayAdapter == null){
            outlayAdapter = new OutlayAdapter(outlays);
        }if(recyclerView.getAdapter() != outlayAdapter){
            recyclerView.setAdapter(outlayAdapter);
        }
        addItemDecoration(layoutManager);
        return view;
    }

    /*private View createOverView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

    }*/

    protected View createNoteView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        if(noteAdapter == null){
            noteAdapter = new NoteAdapter(noteList);
        }if(recyclerView.getAdapter() != noteAdapter){
            recyclerView.setAdapter(noteAdapter);
        }
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void addItemDecoration(RecyclerView.LayoutManager layoutManager){
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        MultipleAdapter adapter = MultipleSelect.with(getActivity()).adapter(recyclerView.getAdapter())
                .stateChangeListener(new ChangeListener())
                .decorateFactory(new ColorFactory())
                .customMenu(new MyMenuBar(getActivity(), R.menu.select_toolbar, ContextCompat.getColor(getActivity(), R.color.colorPrimary), Gravity.TOP))
                .build();
        recyclerView.setAdapter(adapter);
        //添加监听器
       /* recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));*/
    }
}
