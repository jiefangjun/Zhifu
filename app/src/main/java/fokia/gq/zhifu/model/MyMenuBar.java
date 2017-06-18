package fokia.gq.zhifu.model;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.goyourfly.multiple.adapter.MultipleAdapter;
import com.goyourfly.multiple.adapter.menu.CustomMenuBar;
import com.goyourfly.multiple.adapter.menu.MenuController;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fokia.gq.zhifu.R;

import static fokia.gq.zhifu.Dao.InaccountDao.incomes;

/**
 * Created by archie on 6/16/17.
 */

public class MyMenuBar extends CustomMenuBar {
    private RecyclerView recyclerView;
    private MultipleAdapter multipleAdapter;
    public MyMenuBar(@NotNull Activity activity, int menuId, int menuBgColor, int gravity) {
        super(activity, menuId, menuBgColor, gravity);
    }

    @Override
    public void onMenuItemClick(@NotNull MenuItem menuItem, @NotNull MenuController menuController) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        multipleAdapter = (MultipleAdapter) recyclerView.getAdapter();
        switch (menuItem.getItemId()){
            case R.id.action_all:
                multipleAdapter.selectAll();
                break;
            case R.id.action_done:
                multipleAdapter.done(true);
                break;
            case R.id.action_delete:
                //下面函数会调用listener里的ondelete方法
                //multipleAdapter.delete(true);
                multipleAdapter.getSelectNum();
                Log.d("selectNum", multipleAdapter.getSelectNum()+"");
                ArrayList arrayList = multipleAdapter.getSelect();
                Log.d("selectNumArray", multipleAdapter.getSelect()+"");
                for (int i = 0; i<multipleAdapter.getSelectNum(); i++){
                    incomes.remove((int)arrayList.get(i));
                }
                Log.d("incomes", incomes .size()+"");
                multipleAdapter.notifyDataSetChanged();

                break;
        }
    }
}
