package fokia.gq.zhifu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.Income;

/**
 * Created by archie on 6/12/17.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> implements View.OnClickListener{
    private List<Income> mIncomes;
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        //注意这里使用getTag方法获取position
        mOnItemClickListener.onItemClick(v,(Income)v.getTag());
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView incomeMoney;
        TextView incomeNote;
        TextView incomeDate;
        TextView incomeType;
        TextView incomeHandler;

        //ImageView incomeImage;
        public ViewHolder(View view){
            super(view);
            incomeMoney = (TextView) view.findViewById(R.id.update_money);
            incomeNote = (TextView) view.findViewById(R.id.update_note);
            incomeDate = (TextView) view.findViewById(R.id.payment_date);
            incomeType = (TextView) view.findViewById(R.id.update_type);
            incomeHandler = (TextView) view.findViewById(R.id.update_handler_address);
            //incomeImage = (ImageView) view.findViewById(R.id.payment_image);
        }
    }
    public IncomeAdapter(List<Income> incomes){
        mIncomes = incomes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.constrain_payment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Income income = mIncomes.get(position);
        holder.incomeMoney.setText(income.getMoney()+"");
        holder.incomeNote.setText(income.getNote());
        holder.incomeDate.setText(income.getDate());
        holder.incomeType.setText(income.getType());
        holder.incomeHandler.setText(income.gethandler());

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(income);
    }

    @Override
    public int getItemCount() {
        return mIncomes.size();
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , Income data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
