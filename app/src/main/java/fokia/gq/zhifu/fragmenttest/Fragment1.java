package fokia.gq.zhifu.fragmenttest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.Adapter.IncomeAdapter;
import fokia.gq.zhifu.R;

import static fokia.gq.zhifu.Dao.InaccountDao.incomes;

/**
 * Created by archie on 6/13/17.
 */

public class Fragment1 extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper = new DBOpenHelper(getActivity(), "ZhiFu.db", null, 1);
        db = dbOpenHelper.getReadableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        InaccountDao.getIncomes(db);
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        if (incomes.size() != 0){
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            IncomeAdapter adapter = new IncomeAdapter(incomes);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }else {
            Toast.makeText(getActivity(), "没有数据，请添加", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
