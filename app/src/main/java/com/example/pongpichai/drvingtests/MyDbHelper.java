package com.example.pongpichai.drvingtests;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Driver_Test.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "TestDrive";
    public static final String COL_NAME = "Question";
    public static final String COL_ANS1 = "Ans1";
    public static final String COL_ANS2 = "Ans2";
    public static final String COL_ANS3= "Ans3";
    public static final String COL_ANS4 = "Ans4";
    public static final String COL_CATe = "Category";
    public static final String COL_ANST = "AnsT";

    private  Context oC_Context;

    private static final String SCRIPT_CREATE_DATABASE = ("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, " + COL_ANS1 + " TEXT, "
            + COL_ANS2 + " TEXT, "
            + COL_ANS3 + " TEXT, "
            + COL_ANS4 + " TEXT, "
            + COL_CATe + " TEXT, "
            + COL_ANST + " INTEGER);");

    private SQLiteDatabase sqLiteDatabase;

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);

        oC_Context = context;
    }

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        try {

//            File database = oC_Context.getDatabasePath(DB_NAME);
            db.execSQL(SCRIPT_CREATE_DATABASE);
//            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + COL_NAME + " TEXT, " + COL_ANS1 + " TEXT, "
//                    + COL_ANS2 + " TEXT, "
//                    + COL_ANS3 + " TEXT, "
//                    + COL_ANS4 + " TEXT, "
//                    + COL_CATe + " TEXT, "

//                    + COL_ANST + " INTEGER)");
//            if (!database.exists()) {
//                // Database does not exist so copy it from assets here
//                db.execSQL(SCRIPT_CREATE_DATABASE);
//                Log.i("Database", "Not Found");
//            } else {
//                Log.i("Database", "Found");
//            }
//            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + COL_NAME + " TEXT, " + COL_ANS1 + " TEXT, "
//                    + COL_ANS2 + " TEXT, "
//                    + COL_ANS3 + " TEXT, "
//                    + COL_ANS4 + " TEXT, "
//                    + COL_CATe + " TEXT, "
//                    + COL_ANST + " INTEGER);");

            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_NAME + ", " + COL_ANS1
                    + ", " + COL_ANS2 + "," + COL_ANS3 + "," + COL_ANS4 + ") VALUES ('Question', 'A', 'B','C','D');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_NAME + ", " + COL_ANS1
                    + ", " + COL_ANS2 + "," + COL_ANS3 + "," + COL_ANS4 + ") VALUES ('Question1', 'A1', 'B1','C1','D1');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_NAME + ", " + COL_ANS1
                    + ", " + COL_ANS2 + "," + COL_ANS3 + "," + COL_ANS4 + ") VALUES ('Question2', 'A2','B2','C2','D2');");
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_NAME + ", " + COL_ANS1
                    + ", " + COL_ANS2 + "," + COL_ANS3 + "," + COL_ANS4 + ") VALUES ('Question3', 'A3', 'B3','C3','D3');");

//            db.execSQL("CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + COL_NAME + " TEXT, "
//                    + COL_QUES + " TEXT, "
//                    + COL_CHOICE_1 + " TEXT, "
//                    + COL_CHOICE_2 + " TEXT, "
//                    + COL_CHOICE_3 + " TEXT, "
//                    + COL_CHOICE_4 + " TEXT);"
//                    + COL_ANS + " INTEGER, "
//                    + COL_Cate + "TEXT");

//            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_QUES + ", " + COL_CHOICE_1
//                    + ", " + COL_CHOICE_2 + ") VALUES ('Chocolate Fudge', 95, 750);");

        } catch (Exception e){

            Log.e("Error ", e.getMessage() );
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
