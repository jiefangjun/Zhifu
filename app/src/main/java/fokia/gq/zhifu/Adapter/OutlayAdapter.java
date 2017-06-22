package fokia.gq.zhifu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.Income;
import fokia.gq.zhifu.model.Outlay;

/**
 * Created by archie on 6/14/17.
 */

public class OutlayAdapter extends RecyclerView.Adapter<OutlayAdapter.ViewHolder> implements View.OnClickListener{

    private List<Outlay> mOutlays;
    private OutlayAdapter.OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v,(Outlay) v.getTag());
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView outlayMoney;
        TextView outlayNote;
        TextView outlayDate;
        TextView outlayType;
        TextView outlayAddress;
        public ViewHolder(View view){
            super(view);
            outlayMoney = (TextView) view.findViewById(R.id.update_money);
            outlayNote = (TextView) view.findViewById(R.id.update_note);
            outlayDate = (TextView) view.findViewById(R.id.payment_date);
            outlayType = (TextView) view.findViewById(R.id.update_type);
            outlayAddress = (TextView) view.findViewById(R.id.update_handler_address);
        }
    }
    public OutlayAdapter(List<Outlay> outlays){
        mOutlays = outlays;
    }

    @Override
    public OutlayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.constrain_payment_item, parent, false);
        OutlayAdapter.ViewHolder holder = new OutlayAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(OutlayAdapter.ViewHolder holder, int position) {
        Outlay outlay = mOutlays.get(position);
        holder.outlayMoney.setText(outlay.getMoney()+"");
        holder.outlayNote.setText(outlay.getNote());
        holder.outlayDate.setText(outlay.getDate());
        holder.outlayType.setText(outlay.getType());
        holder.outlayAddress.setText(outlay.getAddress());
        holder.itemView.setTag(outlay);
    }

    @Override
    public int getItemCount() {
        return mOutlays.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , Outlay data);
    }

    public void setOnItemClickListener(OutlayAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
