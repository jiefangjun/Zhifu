package fokia.gq.zhifu.fragmenttest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import fokia.gq.zhifu.Dao.DBOpenHelper;

/**
 * Created by archie on 6/14/17.
 */

public class BaseFragment extends Fragment {
    public DBOpenHelper dbOpenHelper;
    public SQLiteDatabase db;
    protected Boolean isVisible = false;
    public Boolean isPrepared = false;

    /**
     * setUserVisibleHint是在onCreateView之前调用的
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /**
         * 判断是否可见
         */
        if(getUserVisibleHint()) {
            isVisible = true;
            //执行可见方法-初始化数据之类
            onVisible();
        } else {
            isVisible = false;
            //不可见
            onInvisible();
        }
    }

    public void onVisible(){
        lazyLoad();
    }

    public void onInvisible(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里 初始化view的各控件 数据
        isPrepared = true;
        lazyLoad();
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        /**
         * 判断是否可见，或者 初始化view的各控件
         */
        if(!isVisible || !isPrepared) {
            return;
        }
        //可见 或者 控件初始化完成 就 加载数据
        initData();
    }

    public void initData(){
    }
}
