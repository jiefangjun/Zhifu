package fokia.gq.zhifu.model;

import android.util.Log;

import com.goyourfly.multiple.adapter.StateChangeListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static fokia.gq.zhifu.Dao.InaccountDao.incomes;

/**
 * Created by archie on 6/16/17.
 */

public class ChangeListener implements StateChangeListener {

    public ArrayList<Integer> selectedItems = new ArrayList<>();

    private String TAG = "StateChangeListener";

    @Override
    public void onSelectMode() {
        Log.d(TAG, "onSelectMode");
    }

    @Override
    public void onSelect(int i, int i1) {
    }

    @Override
    public void onUnSelect(int i, int i1) {
    }

    @Override
    public void onDone(@NotNull ArrayList<Integer> arrayList) {
    }

    @Override
    public void onDelete(@NotNull ArrayList<Integer> arrayList) {
    }

    @Override
    public void onCancel() {
    }
}
