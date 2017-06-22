package fokia.gq.zhifu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fokia.gq.zhifu.R;
import fokia.gq.zhifu.model.Note;

/**
 * Created by archie on 6/14/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements View.OnClickListener{

    private List<Note> notes = new ArrayList<>();
    private NoteAdapter.OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v,(Note) v.getTag());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.note_content);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    public NoteAdapter(List<Note> noteList){
        notes = noteList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.content.setText(note.getContent());
        holder.itemView.setTag(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , Note data);
    }

    public void setOnItemClickListener(NoteAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
