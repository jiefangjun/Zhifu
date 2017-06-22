package fokia.gq.zhifu.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import fokia.gq.zhifu.Dao.DBOpenHelper;
import fokia.gq.zhifu.R;

import static android.app.DialogFragment.STYLE_NORMAL;

/**
 * Created by archie on 6/22/17.
 */

public abstract class BaseDialog  {

    protected Context context;
    private DisplayMetrics dm;
    private Dialog dialog;
    public DBOpenHelper dbopenhelper;
    public SQLiteDatabase db;

    public static int DIALOG_COMMON_STYLE = R.style.Theme_AppCompat_Dialog;

    //对话框布局样式ID
    protected abstract int getDialogStyleId();
    //构建对话框的方法
    protected abstract View getView();

    //构造方法，实现基本的对话框
    public BaseDialog(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        //初始基础对话框
        if (getDialogStyleId() == 0){
            dialog = new Dialog(context, DIALOG_COMMON_STYLE);
        }else {
            dialog = new Dialog(context, getDialogStyleId());
        }
        // 调整dialog背景大小
        getView().setLayoutParams(new FrameLayout.LayoutParams((int) (dm.widthPixels* 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(getView());
        //隐藏系统输入盘
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dbopenhelper = new DBOpenHelper(context, "ZhiFu.db", null, 1);
        db = dbopenhelper.getWritableDatabase();

    }

    /** * Dialog 的基础方法，
     *凡是要用的就在这写出来，然后直接用对话框调本来的方法就好了，不够自己加~hhh */

    //像这类设置对话框属性的方法，就返回值写自己，这样就可以一条链式设置了
    public BaseDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public BaseDialog setdismissListeren(DialogInterface.OnDismissListener dismissListener){
        dialog.setOnDismissListener(dismissListener);
        return this;
    }

}
