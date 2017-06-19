package fokia.gq.zhifu.model;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
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

import fokia.gq.zhifu.Adapter.IncomeAdapter;
import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.Dao.InaccountDao;
import fokia.gq.zhifu.Dao.NoteDao;
import fokia.gq.zhifu.Dao.OutaccountDao;
import fokia.gq.zhifu.R;

import static fokia.gq.zhifu.Dao.InaccountDao.incomes;
import static fokia.gq.zhifu.Dao.NoteDao.noteList;
import static fokia.gq.zhifu.Dao.OutaccountDao.outlays;
import static fokia.gq.zhifu.fragmenttest.PageFragment.flag;
import static fokia.gq.zhifu.fragmenttest.PageFragment.multipleAdapter;

/**
 * Created by archie on 6/16/17.
 */

public class MyMenuBar extends CustomMenuBar {
    private DBOpenHelper dbhelper;
    private SQLiteDatabase dedb;
    public MyMenuBar(@NotNull Activity activity, int menuId, int menuBgColor, int gravity) {
        super(activity, menuId, menuBgColor, gravity);
    }

    @Override
    public void onMenuItemClick(@NotNull MenuItem menuItem, @NotNull MenuController menuController) {
        switch (menuItem.getItemId()){
            case R.id.action_all:
                multipleAdapter.selectAll();
                break;
            case R.id.action_done:
                multipleAdapter.done(true);
                break;
            case R.id.action_delete:
                ArrayList arrayList = multipleAdapter.getSelect();
                dbhelper = new DBOpenHelper(getActivity(), "ZhiFu.db", null, 1);
                dedb = dbhelper.getReadableDatabase();
                switch (flag){
                    case 1:
                        for (int i = 0; i<multipleAdapter.getSelectNum(); i++){
                            InaccountDao.staticDelete(dedb, incomes.get((int)arrayList.get(i) - i).getDate());
                            incomes.remove((int)arrayList.get(i) - i);
                        }
                        break;
                    case 2:
                        for (int i = 0; i<multipleAdapter.getSelectNum(); i++){
                            OutaccountDao.staticDelete(dedb, outlays.get((int)arrayList.get(i) - i).getDate());
                            outlays.remove((int)arrayList.get(i) - i);
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        for (int i = 0; i<multipleAdapter.getSelectNum(); i++){
                            NoteDao.staticDelete(dedb, noteList.get((int)arrayList.get(i) - i).getContent());
                            noteList.remove((int)arrayList.get(i) - i);
                        }
                        break;
                }
                dedb.close();
                multipleAdapter.selectNothing();
                }
                multipleAdapter.notifyDataSetChanged();
    }
}
