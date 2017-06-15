package fokia.gq.zhifu.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fokia.gq.zhifu.model.Note;

/**
 * Created by archie on 6/14/17.
 */

public class NoteDao extends BaseDBDao {
    public static List<Note> noteList = new ArrayList<>();
    private String content;
    public NoteDao(SQLiteDatabase db, String id) {
        super(db, id);
    }

    public NoteDao(SQLiteDatabase db, String id, String content) {
        super(db, id);
        this.content = content;
    }

    @Override
    public void insert(SQLiteDatabase db, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("flag", content);
        db.insert("tb_note", null, contentValues);
        noteList.add(noteList.size(), new Note(content));
    }

    @Override
    public void delete(SQLiteDatabase db, String id) {
        db.delete("tb_note", "id=?", new String[]{id});
    }

    @Override
    public void update(SQLiteDatabase db, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("flag", content);
        db.update("tb_note", contentValues, "id=?", new String[]{id});
    }

    @Override
    public Cursor query(SQLiteDatabase db, String id) {
        return  db.query("tb_note", null, "where=?", new String[]{id}, null, null, null);
    }

    public static void getNotes(SQLiteDatabase db) {
        Cursor cursor = db.query("tb_note", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            noteList.add(new Note(cursor.getString(1)));
        }
    }
}
