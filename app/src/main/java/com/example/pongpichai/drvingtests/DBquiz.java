package com.example.pongpichai.drvingtests;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBquiz extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private static final String DB_NAME = "Driver_Test";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "TestDrive";
    public static final String COL_NAME = "Question";
    public static final String COL_ANS1 = "Ans1";
    public static final String COL_ANS2 = "Ans2";
    public static final String COL_ANS3= "Ans3";
    public static final String COL_ANS4 = "Ans4";
    public static final String COL_CATe = "Category";
    public static final String COL_ANST = "AnsT";

    private static final String SCRIPT_CREATE_DATABASE = ("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, " + COL_ANS1 + " TEXT, "
            + COL_ANS2 + " TEXT, "
            + COL_ANS3 + " TEXT, "
            + COL_ANS4 + " TEXT, "
            + COL_CATe + " TEXT, "
            + COL_ANST + " INTEGER);");

//    public DBquiz(Context context) {
////        super(context, String.valueOf(Quiz.DATABASE_NAME), null, Quiz.DATABASE_VERSION);
//    }


    public DBquiz(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL(SCRIPT_CREATE_DATABASE);

        }catch (Exception e){

            Log.d("Fail", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
//    public List<String> getQuizList() {
//        List<String> Quiz = new ArrayList<String>();
//
//        sqLiteDatabase = this.getWritableDatabase();
//
//        Cursor cursor = sqLiteDatabase.query
//                (, null, null, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        while(!cursor.isAfterLast()) {
//
//            Quiz.add(cursor.getLong(0) + " " +
//                    cursor.getString(1) + " " +
//                    cursor.getString(2));
//
//            cursor.moveToNext();
//        }
//
//        sqLiteDatabase.close();
//
//        return Quiz;
//    }
}