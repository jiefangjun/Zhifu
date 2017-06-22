package fokia.gq.zhifu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;



/**
 * Created by archie on 6/21/17.
 */

public class BaseListener implements View.OnClickListener {
    public OnItemClickListener mOnItemClickListener = null;


    @Override
    public void onClick(View v) {

    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

}
