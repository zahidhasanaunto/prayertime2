package com.zahidhasan.app.prayertimeapp.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by DarkzGothic on 8/28/2017.
 */

public class DataBaseCreateHandler extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "db.sqlite";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context ctx;

    public DataBaseCreateHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        this.ctx = context;
    }

    void createDataBase() throws IOException {
        if(!checkDataBase()) {
            this.getReadableDatabase();
            copyDataBase();
            this.close();
        }
    }

    public synchronized void close(){
        if(mDataBase != null)
            mDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput =  ctx.getAssets().open(DB_NAME);
        String outfileName = DB_PATH;
        OutputStream mOutput = new FileOutputStream(outfileName);
        byte[] buffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(buffer))>0) {
            mOutput.write(buffer, 0, mLength);
        }
        mOutput.flush();
        mInput.close();
        mOutput.close();
    }

    private boolean checkDataBase() {
        File DbFile = new File(DB_PATH + DB_NAME);
        return DbFile.exists();
    }

    boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
