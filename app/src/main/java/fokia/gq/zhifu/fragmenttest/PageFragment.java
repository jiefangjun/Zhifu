package fokia.gq.zhifu.fragmenttest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.goyourfly.multiple.adapter.MultipleAdapter;
import com.goyourfly.multiple.adapter.MultipleSelect;
import com.goyourfly.multiple.adapter.viewholder.color.ColorFactory;

import java.util.ArrayList;
import java.util.List;

import fokia.gq.zhifu.Adapter.NoteAdapter;
import fokia.gq.zhifu.Adapter.OutlayAdapter;
import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.Adapter.IncomeAdapter;
import fokia.gq.zhifu.Dao.NoteDao;
import fokia.gq.zhifu.Dao.OutaccountDao;
import fokia.gq.zhifu.Dialog.UpdateIncomeDialog;
import fokia.gq.zhifu.Dialog.UpdateNoteDialog;
import fokia.gq.zhifu.Dialog.UpdateOutlayDialog;
import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.Income;
import fokia.gq.zhifu.model.MyMenuBar;
import fokia.gq.zhifu.model.Note;
import fokia.gq.zhifu.model.Outlay;

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
    private LinearLayoutManager layoutManager;

    private NoteAdapter noteAdapter;
    private IncomeAdapter incomeAdapter;
    private OutlayAdapter outlayAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    BarDataSet setIncomes = null;
    BarDataSet setOutlays = null;


    private BarChart chart;
    List<BarEntry> entrieIncomes = new ArrayList<>();
    List<BarEntry> entrieOutlays = new ArrayList<>();

    private Boolean incomeisRefresh = false;
    private Boolean outlayisRefresh = false;
    private Boolean flagisRefresh = false;


    public static MultipleAdapter multipleAdapter;



    //加载页
    public static int flag;



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
        if(getArguments() != null){
            mPage = getArguments().getInt(ARG_PAGE);
        }

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
                getFlags();
                break;
            case 4:
                getOvercome();
                break;
            default:
                break;
        }
        if(recyclerView != null){
            addItemDecoration(layoutManager);
            initSwipeRefresh(pageView);
            //打开页面即刷新,取消新线程，防止数据重复
            //refreshData();
        }
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

    private void getOvercome(){


        if (outlays.size() ==0 ){
            getOutlays();
        }
        int length;
        if (incomes.size() > outlays.size()){
            length = incomes.size();
        }
        else {
            length = outlays.size();
        }
        if (setIncomes != null){
            setIncomes.clear();
            entrieIncomes.clear();
        }
        if (setOutlays != null){
            setOutlays.clear();
            entrieIncomes.clear();
        }
        for(int i = 0; i < length; i++) {
            if(i < incomes.size()){
                entrieIncomes.add(new BarEntry(i, (float) incomes.get(i).getMoney()));
            }
            if (i < outlays.size()){
                entrieOutlays.add(new BarEntry(i, (float) outlays.get(i).getMoney()));
            }
        }

        setIncomes = new BarDataSet(entrieIncomes, "收入");
        setOutlays = new BarDataSet(entrieOutlays, "支出");

        setIncomes.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        setOutlays.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
// (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData data = new BarData(setIncomes, setOutlays);
        data.setBarWidth(barWidth); // set the width of each bar
        chart.setData(data);
        chart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping

        chart.invalidate(); // refresh


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
                pageView = createNoteView(inflater, container, savedInstanceState);
                break;
            case 4:
                pageView = createOverView(inflater, container, savedInstanceState);
                return pageView;
            default:
            break;
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
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
        if(incomeisRefresh){
            incomes.clear();
            getIncomes();
            incomeisRefresh = false;
        }
        if (outlayisRefresh){
            outlays.clear();
            getOutlays();
            outlayisRefresh = false;
        }
        if (flagisRefresh){
            noteList.clear();
            getFlags();
            flagisRefresh = false;
        }
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
        layoutManager = new LinearLayoutManager(getActivity());
        if(incomeAdapter == null){
            incomeAdapter = new IncomeAdapter(incomes);
        }
        //TODO 一个有趣的问题
        if(recyclerView.getAdapter() == null){
            recyclerView.setAdapter(incomeAdapter);
        }
        IncomeAdapter adapter = (IncomeAdapter) recyclerView.getAdapter();
        adapter.setOnItemClickListener(new IncomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Income data) {
                new UpdateIncomeDialog(getActivity()).setData(data.getMoney(), data.gethandler(), data.getType(), data.getNote(), data.getDate()).setCancelable(true).show();
                incomeisRefresh = true;
            }
        });
        return view;
    }

    protected View createOutlayView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        if(outlayAdapter == null){
            outlayAdapter = new OutlayAdapter(outlays);
        }if(recyclerView.getAdapter() != outlayAdapter){
            recyclerView.setAdapter(outlayAdapter);
        }
        OutlayAdapter adapter = (OutlayAdapter) recyclerView.getAdapter();
        adapter.setOnItemClickListener(new OutlayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Outlay data) {
                new UpdateOutlayDialog(getActivity()).setData(data.getMoney(), data.getAddress(), data.getType(), data.getNote(), data.getDate()).setCancelable(true).show();
                outlayisRefresh = true;
            }
        });
        return view;
    }

    private View createOverView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragemnt_chart, container, false);
        chart = (BarChart) view.findViewById(R.id.chart);
        return view;
    }

    protected View createNoteView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        if(noteAdapter == null){
            noteAdapter = new NoteAdapter(noteList);
        }if(recyclerView.getAdapter() != noteAdapter){
            recyclerView.setAdapter(noteAdapter);
        }
        NoteAdapter adapter = (NoteAdapter) recyclerView.getAdapter();
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Note data) {
                new UpdateNoteDialog(getActivity()).setData(data.getContent(), data.getId()).setCancelable(true).show();
                flagisRefresh = true;
            }
        });
        return view;
    }

    private void addItemDecoration(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        multipleAdapter = MultipleSelect.with(getActivity()).adapter(recyclerView.getAdapter())
                .decorateFactory(new ColorFactory())
                .customMenu(new MyMenuBar(getActivity(), R.menu.select_toolbar, ContextCompat.getColor(getActivity(), R.color.colorPrimary), Gravity.TOP))
                .build();
        recyclerView.setAdapter(multipleAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
