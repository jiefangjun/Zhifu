package fokia.gq.zhifu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.Outlay;

/**
 * Created by archie on 6/14/17.
 */

public class OutlayAdapter extends RecyclerView.Adapter<OutlayAdapter.ViewHolder> {

    private List<Outlay> mOutlays;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView outlayMoney;
        TextView outlayNote;
        TextView outlayDate;
        TextView outlayType;
        TextView outlayAddress;
        public ViewHolder(View view){
            super(view);
            outlayMoney = (TextView) view.findViewById(R.id.payment_money);
            outlayNote = (TextView) view.findViewById(R.id.payment_note);
            outlayDate = (TextView) view.findViewById(R.id.payment_date);
            outlayType = (TextView) view.findViewById(R.id.payment_type);
            outlayAddress = (TextView) view.findViewById(R.id.payment_handler_address);
        }
    }
    public OutlayAdapter(List<Outlay> outlays){
        mOutlays = outlays;
    }

    @Override
    public OutlayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.constrain_payment_item, parent, false);
        OutlayAdapter.ViewHolder holder = new OutlayAdapter.ViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        return mOutlays.size();
    }
}
