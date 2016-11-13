package com.chanhbc.ailatrieuphu.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.chanhbc.ailatrieuphu.model.Question;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseManager {
    private Context context;
    private static final String DATA_NAME = "Question";
    private static final String PATH_DATA = Environment.getDataDirectory().getAbsolutePath() + "/data/com.chanhbc.ailatrieuphu/";
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context) {
        this.context = context;
        copyDatabase();
    }

    private void copyDatabase() {
        try {
            File f = new File(PATH_DATA + DATA_NAME);
            if (f.exists()) {
                return;
            }
            AssetManager am = context.getAssets();
            DataInputStream dis = new DataInputStream(am.open(DATA_NAME));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = dis.read(bytes)) >= 0) {
                dos.write(bytes, 0, length);
            }
            dis.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDatabase() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen())
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH_DATA + DATA_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void closeDatabase() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public Question getOneQuestion(String tableName) {
        openDatabase();
        Question question = new Question("", "", "", "", "", 0);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + tableName + " ORDER BY Random() LIMIT 1", null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        String q = cursor.getString(cursor.getColumnIndex("Question"));
        String a = cursor.getString(cursor.getColumnIndex("CaseA"));
        String b = cursor.getString(cursor.getColumnIndex("CaseB"));
        String c = cursor.getString(cursor.getColumnIndex("CaseC"));
        String d = cursor.getString(cursor.getColumnIndex("CaseD"));
        int tc = cursor.getInt(cursor.getColumnIndex("TrueCase"));
        question = new Question(q, a, b, c, d, tc);
        closeDatabase();
        return question;
    }

    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Question question = getOneQuestion("Question" + i);
            if (question != null) {
                questions.add(question);
            }
        }
        return questions;
    }
}
