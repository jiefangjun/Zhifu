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

import fokia.gq.zhifu.R;

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
                multipleAdapter.delete(true);
                break;
        }
    }
}
