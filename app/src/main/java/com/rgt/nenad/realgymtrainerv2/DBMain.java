package com.rgt.nenad.realgymtrainerv2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBMain extends SQLiteOpenHelper {
    private static String DB_PATH= "data/data/com.rgt.nenad.realgymtrainerv2/databases/";
    private static String DB_NAME = "rgtbaza";
    private static String TABLE_Name = "Profil";
    private SQLiteDatabase dbObj;
    private final Context context;
    DBMain db;

    public DBMain(Context context) {
        super(context, DB_NAME, null, 1);
        this.context  = context;
    }

    public void createDB() throws IOException {

        boolean dbExist = checkDB();
        if(dbExist){
            //DO NOTHING!!!!
        }
        else{


        this.getReadableDatabase();
        Log.i("Readable ends", "end");

        try {
            copyDB();
            Log.i("copy db ends","end");

        } catch (IOException e) {

            throw new Error("Error copying database");
        }
        }
    }

    private boolean checkDB(){

        SQLiteDatabase checkDB = null;

        try{
            String path = DB_PATH + DB_NAME;
            Log.i("myPath ......",path);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            Log.i("myPath ......",path);
            if (checkDB!=null)
            {

            checkDB.close();
            }
            else
            {
                return false;
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null){

            checkDB.close();

        }
        return checkDB != null ? true : false;
    }

    public void copyDB() throws IOException{
        try {
            Log.i("inside copyDB","start");

            InputStream ip =  context.getAssets().open(DB_NAME+".db");
            Log.i("Input Stream....",ip+"");
            String op=  DB_PATH  +  DB_NAME ;
            OutputStream output = new FileOutputStream( op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer))>0){
                output.write(buffer, 0, length);
                Log.i("Content.... ",length+"");
            }
            output.flush();
            output.close();
            ip.close();
        }
        catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.i("open DB......", dbObj.toString());
    }

    @Override
    public synchronized void close() {

        if(dbObj != null)
            dbObj.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Achievement> LoadLockedAchievements(Context context){
        ArrayList<Achievement> dostignuca = new ArrayList<>();
        try {


            SQLiteDatabase db1;
            db1 = context.openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom2 = db1.enableWriteAheadLogging();

            Cursor c = db1.rawQuery("SELECT * FROM Dostignuca WHERE active=0",null);


            c.moveToFirst();

            while (c.isAfterLast() == false) {
                Achievement a = new Achievement(c.getString(c.getColumnIndex("type")),c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("active")), c.getString(c.getColumnIndex("value")), c.getString(c.getColumnIndex("ID")));
                dostignuca.add(a);

                c.moveToNext();
            }
            db1.close();
        }
        catch (SQLException e){
            e.getMessage();
        }

        return dostignuca;

    }





}