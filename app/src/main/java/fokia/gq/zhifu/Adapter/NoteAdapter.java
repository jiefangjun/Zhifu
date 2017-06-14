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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private List<Note> notes = new ArrayList<>();
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
        return holder;
    }

    public NoteAdapter(List<Note> noteList){
        notes = noteList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
