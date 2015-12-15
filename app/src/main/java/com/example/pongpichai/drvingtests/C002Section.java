package com.example.pongpichai.drvingtests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class C002Section extends Activity {

    MyDbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;

    private Button ocb001Finish;
    private ListView olv001ListContent;
    private RadioGroup org001RadioGroup;
    private ArrayList<DriveModel> aC_DriveModel;
    //    private ArrayList<DriveModel> aC_DriverSize;
    private String[] aC_DriverSize;
    private int nC_Pos;
    private int nC_Pos1;
    private boolean myABoolean;
    private TextView timeTextView;
    private int intStartTime = 60;
    String strResult = null;

    private DriverAdapter oC_ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.w001start);

        C_SETxInitialWidget();
        C_SETxEventListener();
        C_GETxDatabase();
        C_SETxAdapter();

        //Receive Value Intent
        myABoolean = getIntent().getBooleanExtra("Status", false);
        if (myABoolean) {

            final MyCounter timer = new MyCounter(5000,1000);
            timer.start();

        }   //if




    }   // Main Method

    public class MyCounter extends CountDownTimer {

        public MyCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            showAnswerDialog();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeTextView.setText((millisUntilFinished/1000)+"");
            System.out.println("Timer  : " + (millisUntilFinished/1000));
        }
    }




    private void C_GETxDatabase() {

        try {

            mHelper = new MyDbHelper(this);
            mDb = mHelper.getWritableDatabase();

            mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_NAME + "," + MyDbHelper.COL_ANS1 + "," + MyDbHelper.COL_ANS2
                    + "," + MyDbHelper.COL_ANS3 + "," + MyDbHelper.COL_ANS4 + "," + MyDbHelper.COL_ANST
                    + " FROM " + MyDbHelper.TABLE_NAME + " WHERE  " + MyDbHelper.COL_CATe + " = '1'" + " ORDER BY random()" + " LIMIT 50", null);

            mCursor.moveToFirst();

            aC_DriveModel = new ArrayList<DriveModel>();

            while (!mCursor.isAfterLast()) {

                DriveModel oDriveModel = new DriveModel();

                oDriveModel.Question = mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_NAME));
                oDriveModel.Ans1 = mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_ANS1));
                oDriveModel.Ans2 = mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_ANS2));
                oDriveModel.Ans3 = mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_ANS3));
                oDriveModel.Ans4 = mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_ANS4));
                oDriveModel.AnsT = mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_ANST));
                oDriveModel.Select = "0";
                aC_DriveModel.add(oDriveModel);
                mCursor.moveToNext();

            }

        } catch (Exception e) {
            Log.e("Fail", "C_GETxDatabase error : " + e.getMessage());
        }
    }

    private void C_SETxAdapter() {

        try {

            aC_DriverSize = new String[aC_DriveModel.size()];
            //aC_DriverSize[1] = "12";
            if (aC_DriveModel != null && aC_DriveModel.size() > 0) {
                //DriverAdapter oListAdapter = new DriverAdapter(C002Section.this,aC_DriveModel );

                oC_ListAdapter = new DriverAdapter(C002Section.this, aC_DriveModel);
                olv001ListContent.setAdapter(oC_ListAdapter);
            } else {

            }


        } catch (Exception e) {
            Log.e("Fail", "Select error : " + e.getMessage());
        }

    }

    private void C_SETxInitialWidget() {

        try {
            olv001ListContent = (ListView) findViewById(R.id.listView1);
            ocb001Finish = (Button) findViewById(R.id.ocb001Finish);
            timeTextView = (TextView) findViewById(R.id.textView2);

//            olv001ListContent.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                    if (oC_ListAdapter != null) {
//
//                    }
//
//                    olv001ListContent.invalidate();
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                }
//            });


        } catch (Exception e) {
            Log.e("Fail", "C_SETxInitialWidget error : " + e.getMessage());
        }
    }

    private void C_SETxEventListener() {

        try {
            ocb001Finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showAnswerDialog();

                }   // event
            });

        } catch (Exception e) {
            Log.e("Fail", "C_SETxEventListener error : " + e.getMessage());
        }
    }

    private void showAnswerDialog() {

        final String tAns = C_GETxScore();    // คะแนนที่ทำได้จริง

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(C002Section.this);

        alertDialogBuilder.setTitle(" DrivingTest");
        alertDialogBuilder.setMessage("Your Score Is : " + tAns);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (myABoolean) {
                    Intent objIntent = new Intent(C002Section.this, ShowAnswerActivity.class);
                    objIntent.putExtra("Score", tAns);
                    startActivity(objIntent);

                }

                finish();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }

    private String C_GETxScore() {

        try {
            int nScore = 0;
            for (int nLoop = 0; nLoop < aC_DriveModel.size(); nLoop++) {

                String tAnsT = aC_DriveModel.get(nLoop).AnsT;
                String tsizeindex = aC_DriverSize[nLoop];

                String tAnsUser = null;

                if (tsizeindex != null) {
                    tAnsUser = tsizeindex.substring(1);
                }

                if (tAnsT.equals(tAnsUser)) {

                    nScore += 1;
                }
            }
            String tScore = String.valueOf(nScore);
            return tScore;
        } catch (Exception e) {
            Log.e("Fail", "C_GETxScore error : " + e.getMessage());
        }
        return "";
    }

    public void onPause() {
        super.onPause();
        mHelper.close();
        mDb.close();
    }

    public class DriverAdapter extends BaseAdapter {

        private Context oC_Context;
        private ArrayList<DriveModel> aC_DriverModel;
        private ViewHolder mViewHolder;

//        int countPre,countAbs,countHD;
//        private int nC_Score;

        public DriverAdapter(Context poContext, ArrayList<DriveModel> paDriveList) {

            oC_Context = poContext;
            aC_DriverModel = paDriveList;
        }

        @Override
        public int getCount() {

            return aC_DriverModel.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater mInflater = (LayoutInflater) oC_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = mInflater.inflate(R.layout.list_item, parent, false);

            mViewHolder = new ViewHolder();

            mViewHolder.otv002Question = (TextView) convertView.findViewById(R.id.otv002Question);
            mViewHolder.org001RadioGroup = (RadioGroup) convertView.findViewById(R.id.org001RadioGroup);
            mViewHolder.orbChoice1 = (RadioButton) convertView.findViewById(R.id.orbChoice1);
            mViewHolder.orbChoice2 = (RadioButton) convertView.findViewById(R.id.orbChoice2);
            mViewHolder.orbChoice3 = (RadioButton) convertView.findViewById(R.id.orbChoice3);
            mViewHolder.orbChoice4 = (RadioButton) convertView.findViewById(R.id.orbChoice4);

            mViewHolder.otv002Question.setText(String.valueOf(position + 1) + ": " +
                    aC_DriverModel.get(position).Question);
            mViewHolder.orbChoice1.setText(aC_DriverModel.get(position).Ans1);
            mViewHolder.orbChoice2.setText(aC_DriverModel.get(position).Ans2);
            mViewHolder.orbChoice3.setText(aC_DriverModel.get(position).Ans3);
            mViewHolder.orbChoice4.setText(aC_DriverModel.get(position).Ans4);

            mViewHolder.orbChoice1.setTag(position + "1");
            mViewHolder.orbChoice2.setTag(position + "2");
            mViewHolder.orbChoice3.setTag(position + "3");
            mViewHolder.orbChoice4.setTag(position + "4");

            String tTag1 = mViewHolder.orbChoice1.getTag().toString();
            mViewHolder.orbChoice1.setChecked(tTag1.equals(aC_DriverSize[position]));

            String tTag2 = mViewHolder.orbChoice2.getTag().toString();
            mViewHolder.orbChoice2.setChecked(tTag2.equals(aC_DriverSize[position]));

            String tTag3 = mViewHolder.orbChoice3.getTag().toString();
            mViewHolder.orbChoice3.setChecked(tTag3.equals(aC_DriverSize[position]));

            String tTag4 = mViewHolder.orbChoice4.getTag().toString();
            mViewHolder.orbChoice4.setChecked(tTag4.equals(aC_DriverSize[position]));


            mViewHolder.orbChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton orbTmp = (RadioButton) v;
                    aC_DriverSize[position] = orbTmp.getTag().toString();
                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice1.getTag().toString() ,Toast.LENGTH_SHORT).show();
                    //oC_ListAdapter.notifyDataSetChanged();
                    //Toast.makeText(C002Section.this,orbTmp.getTag().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            mViewHolder.orbChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton orbTmp = (RadioButton) v;
                    aC_DriverSize[position] = orbTmp.getTag().toString();
                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice2.getTag().toString() ,Toast.LENGTH_SHORT).show();
                    //oC_ListAdapter.notifyDataSetChanged();
                    //Toast.makeText(C002Section.this,orbTmp.getTag().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            mViewHolder.orbChoice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton orbTmp = (RadioButton) v;
                    aC_DriverSize[position] = orbTmp.getTag().toString();
                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice3.getTag().toString() ,Toast.LENGTH_SHORT).show();
                    //oC_ListAdapter.notifyDataSetChanged();
                    //Toast.makeText(C002Section.this,orbTmp.getTag().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            mViewHolder.orbChoice4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton orbTmp = (RadioButton) v;
                    aC_DriverSize[position] = orbTmp.getTag().toString();
                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice4.getTag().toString() ,Toast.LENGTH_SHORT).show();
                    //oC_ListAdapter.notifyDataSetChanged();
                    //Toast.makeText(C002Section.this, orbTmp.getTag().toString(), Toast.LENGTH_SHORT).show();
                }
            });


//
//            if(convertView == null){
//                convertView = mInflater.inflate(R.layout.list_item, parent, false);
//                mViewHolder = new ViewHolder();
//                mViewHolder.otv002Question = (TextView)convertView.findViewById(R.id.otv002Question);
//                mViewHolder.org001RadioGroup = (RadioGroup) convertView.findViewById(R.id.org001RadioGroup);
//                mViewHolder.orbChoice1 = (RadioButton) convertView.findViewById(R.id.orbChoice1);
//                mViewHolder.orbChoice2 = (RadioButton) convertView.findViewById(R.id.orbChoice2);
//                mViewHolder.orbChoice3 = (RadioButton) convertView.findViewById(R.id.orbChoice3);
//                mViewHolder.orbChoice4 = (RadioButton) convertView.findViewById(R.id.orbChoice4);
//
//                convertView.setTag(mViewHolder);
//            } else {
//                mViewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            mViewHolder.otv002Question.setText(String.valueOf(position+1) + ": " +
//                    aC_DriverModel.get(position).Question);
//            mViewHolder.orbChoice1.setText(aC_DriverModel.get(position).Ans1);
//            mViewHolder.orbChoice2.setText(aC_DriverModel.get(position).Ans2);
//            mViewHolder.orbChoice3.setText(aC_DriverModel.get(position).Ans3);
//            mViewHolder.orbChoice4.setText(aC_DriverModel.get(position).Ans4);
//
////            mViewHolder.orbChoice1.setTag(1);
////            mViewHolder.orbChoice2.setTag(2);
////            mViewHolder.orbChoice3.setTag(3);
////            mViewHolder.orbChoice4.setTag(4);
//
//            mViewHolder.orbChoice1.setTag(position + "1");
//            mViewHolder.orbChoice2.setTag(position + "2");
//            mViewHolder.orbChoice3.setTag(position + "3");
//            mViewHolder.orbChoice4.setTag(position + "4");
//
//            String tTag1 = mViewHolder.orbChoice1.getTag().toString();
//            Boolean bTag1 = tTag1.equals(aC_DriverSize[position]);
//            mViewHolder.orbChoice1.setChecked(bTag1);
//
//            String tTag2 = mViewHolder.orbChoice2.getTag().toString();
//            Boolean bTag2 = tTag2.equals(aC_DriverSize[position]);
//            mViewHolder.orbChoice2.setChecked(bTag2);
//
//            String tTag3 = mViewHolder.orbChoice3.getTag().toString();
//            Boolean bTag3 = tTag3.equals(aC_DriverSize[position]);
//            mViewHolder.orbChoice3.setChecked(bTag3);
//
//            String tTag4 = mViewHolder.orbChoice4.getTag().toString();
//            Boolean bTag4 = tTag4.equals(aC_DriverSize[position]);
//            mViewHolder.orbChoice4.setChecked(bTag4);
//
//            //Toast.makeText(C002Section.this,"ee " + position,Toast.LENGTH_SHORT).show();
//
//           String tsizeindex =  aC_DriverSize[position];
//
//            //mViewHolder.orbChoice1.setChecked("1".equals(tsizeindex));
//            //mViewHolder.orbChoice1.setChecked("2".equals(tsizeindex));
//
//
//
//            /*if  (tsizeindex != null) {
//
//                if (tsizeindex.equals("1")) {
//
//                    mViewHolder.orbChoice1.setChecked(true);
//                    mViewHolder.orbChoice2.setChecked(false);
//                    mViewHolder.orbChoice3.setChecked(false);
//                    mViewHolder.orbChoice4.setChecked(false);
//
//                    notifyDataSetChanged();
//
//                } else if (tsizeindex.equals("2")) {
//
//                    mViewHolder.orbChoice1.setChecked(false);
//                    mViewHolder.orbChoice2.setChecked(true);
//                    mViewHolder.orbChoice3.setChecked(false);
//                    mViewHolder.orbChoice4.setChecked(false);
//
//                    notifyDataSetChanged();
//
//                } else if (tsizeindex.equals("3")) {
//
//                    mViewHolder.orbChoice1.setChecked(false);
//                    mViewHolder.orbChoice2.setChecked(false);
//                    mViewHolder.orbChoice3.setChecked(true);
//                    mViewHolder.orbChoice4.setChecked(false);
//
//                    notifyDataSetChanged();
//
//                } else if (tsizeindex.equals("4")) {
//
//                    mViewHolder.orbChoice1.setChecked(false);
//                    mViewHolder.orbChoice2.setChecked(false);
//                    mViewHolder.orbChoice3.setChecked(false);
//                    mViewHolder.orbChoice4.setChecked(true);
//
//                    notifyDataSetChanged();
//
//                } else {
//                    mViewHolder.orbChoice1.setChecked(false);
//                    mViewHolder.orbChoice2.setChecked(false);
//                    mViewHolder.orbChoice3.setChecked(false);
//                    mViewHolder.orbChoice4.setChecked(false);
//
//                    notifyDataSetChanged();
//                }
//            } else {
//                mViewHolder.orbChoice1.setChecked(false);
//                mViewHolder.orbChoice2.setChecked(false);
//                mViewHolder.orbChoice3.setChecked(false);
//                mViewHolder.orbChoice4.setChecked(false);
//
//                notifyDataSetChanged();
//            }*/
//
//            mViewHolder.orbChoice1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RadioButton orbTmp = (RadioButton) v;
//                    aC_DriverSize[position] = orbTmp.getTag().toString();
//                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice1.getTag().toString() ,Toast.LENGTH_SHORT).show();
//                    oC_ListAdapter.notifyDataSetChanged();
//                    //Toast.makeText(C002Section.this,orbTmp.getTag().toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            mViewHolder.orbChoice2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RadioButton orbTmp = (RadioButton) v;
//                    aC_DriverSize[position] = orbTmp.getTag().toString();
//                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice2.getTag().toString() ,Toast.LENGTH_SHORT).show();
//                    //notifyDataSetChanged();
//                    //Toast.makeText(C002Section.this,orbTmp.getTag().toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            mViewHolder.orbChoice3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RadioButton orbTmp = (RadioButton) v;
//                    aC_DriverSize[position] = orbTmp.getTag().toString();
//                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice3.getTag().toString() ,Toast.LENGTH_SHORT).show();
//                    //notifyDataSetChanged();
//                    //Toast.makeText(C002Section.this,orbTmp.getTag().toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            mViewHolder.orbChoice4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RadioButton orbTmp = (RadioButton) v;
//                    aC_DriverSize[position] = orbTmp.getTag().toString();
//                    //Toast.makeText(C002Section.this,mViewHolder.orbChoice4.getTag().toString() ,Toast.LENGTH_SHORT).show();
//                    //notifyDataSetChanged();
//                    //Toast.makeText(C002Section.this, orbTmp.getTag().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
//


//            mViewHolder.org001RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
////                    org001RadioGroup.indexOfChild(findViewById(checkedId))
////                    int nC_Pos1 = mViewHolder.org001RadioGroup.indexOfChild(findViewById(checkedId));
//
//                    View radioButton = mViewHolder.org001RadioGroup.findViewById(checkedId);
//                    int index = mViewHolder.org001RadioGroup.indexOfChild(radioButton);
//                    DriveModel oDriveModel = new DriveModel();
//                    String tIndex;
//                    switch (index) {
//                        case 0 :
//                            tIndex = "1";
//                            oDriveModel.Select = "1";
//                            break;
//                        case 1 :
//                            tIndex = "2";
//                            oDriveModel.Select = "2";
//                            break;
//                        case 2 :
//                            tIndex = "3";
//                            oDriveModel.Select = "3";
//                            break;
//                        default :
//                            tIndex = "4";
//                            oDriveModel.Select = "4";
//                            break;
//                    }
//                    aC_DriverSize[position] = tIndex;
//                }
//             }
//            );

            return convertView;

        }

    }


    public class ViewHolder {
        TextView otv002Question;
        RadioGroup org001RadioGroup;
        RadioButton orbChoice1;
        RadioButton orbChoice2;
        RadioButton orbChoice3;
        RadioButton orbChoice4;
    }
}
