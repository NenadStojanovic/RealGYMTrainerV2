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

public class DBMain extends SQLiteOpenHelper {
    private static String DB_PATH= "data/data/com.rgt.nenad.realgymtrainerv2/databases/";
    private static String DB_NAME = "rgtbaza";
    private static String TABLE_Name = "Profil";
    private SQLiteDatabase dbObj;
    private final Context context;
    DBMain db;

    public DBMain(Context context) {
        super(context,  DB_NAME , null, 1);
        this.context  = context;
    }

    public void createDB() throws IOException {

        this.getReadableDatabase();
        Log.i("Readable ends","end");

        try {
            copyDB();
            Log.i("copy db ends","end");

        } catch (IOException e) {

            throw new Error("Error copying database");
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
                Cursor c= checkDB.rawQuery("SELECT * FROM Profil", null);
                Log.i("Cursor.......",c.getString(0));
                c.moveToFirst();
                String contents[]=new String[80];
                int flag=0;

                while(! c.isAfterLast())
                {
                    String temp="";
                    String s2=c.getString(0);
                    String s3=c.getString(1);
                    String s4=c.getString(2);
                    temp=temp+"\n Id:"+s2+"\tType:"+s3+"\tBal:"+s4;
                    contents[flag]=temp;
                    flag=flag+1;

                    Log.i("DB values.........",temp);
                    c.moveToNext();

                }
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
        Log.i("open DB......",dbObj.toString());
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

    public String vratiIme () {
        String dbString = "";

        try {

            SQLiteDatabase db =  getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_Name + " WHERE 1";
            Cursor c = db.rawQuery(query, null);
            //Move to first row in your result
            c.moveToFirst();
            dbString += c.getString(1);
            db.close();

        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return dbString;

    }

    public String vratiVrednost(int ind,Context c)
    {
        String dbString = "";
        DBMain db;
        db = new DBMain(c);

        try {

            db.createDB();
        } catch (IOException ioe) {

            throw new Error("Database not created....");
        }

        try {
            db.openDB();

        }catch(Exception sqle){

            throw sqle;
        }
        try {

            SQLiteDatabase db1 =  getWritableDatabase();
            //String query = "SELECT * FROM " + TABLE_Name + " WHERE id = ?";
            Cursor c1 = db1.rawQuery("SELECT * FROM " + TABLE_Name + " WHERE id = ?", new String[] {"1"});
            //Move to first row in your result
            c1.moveToFirst();
            dbString += c1.getString(ind);
            db1.close();

        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return dbString;

    }

    public void updateProf(Profil p, Context c)
    {




        try {
            SQLiteDatabase db1 = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("Ime", p.getIme());
            cv.put("Prezime", p.getPrezime());
            int pom = db1.update("Profil", cv, "ID = ? ", new String[]{"1"});
            //db1.execSQL("UPDATE Profil SET Ime=Proba WHERE ID=1");
            db1.close();
        }
        catch (SQLException e)
        {
            e.getMessage();
        }

    }
}