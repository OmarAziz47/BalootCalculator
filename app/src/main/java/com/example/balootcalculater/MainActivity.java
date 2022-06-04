package com.example.balootcalculater;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.widget.NumberPicker;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "word";
    int scoreRight = 0, scoreLeft = 0;
    int[] n = new int [100];
    int[] m = new int [100];
    int i,l,x = 1;
//    ScrollView ScrollDown1 = (ScrollView) findViewById(R.id.ScrollDown1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // This action removes the top action bar
        setContentView(R.layout.activity_main);
    }



    // This is where we enter each value for each game
    public void enter(View view) {
        EditText EditTextUs = (EditText) findViewById(R.id.counter_us);
        String n1 = EditTextUs.getText().toString().trim();  // يطلع الطول شف لهذي حل خله تجمع الأرقام بطال مايعد الطول
        EditText EditTextThem = (EditText) findViewById(R.id.counter_them);
        String m1 = EditTextThem.getText().toString().trim();
        EditTextUs.onEditorAction(EditorInfo.IME_ACTION_DONE);
        EditTextThem.onEditorAction(EditorInfo.IME_ACTION_DONE);

        EditTextUs.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) &&
                        (keyCode           == KeyEvent.KEYCODE_ENTER)   )
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(EditTextUs.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        } );


        Intent intent = getIntent();

        String word = intent.getStringExtra(EXTRA_MESSAGE);
        int num = intent.getIntExtra("number", 0);


        if (n1.isEmpty() || n1.length() == 0 || n1.equals(" ") || n1.equals("") || n1 == null) {
            //EditText is empty
            l = 2;

        } else {
            n[i] = Integer.parseInt(n1);  //EditText is not empty
            scoreLeft += n[i];
        }
        // يطلع الطول شف لهذي حل خله تجمع الأرقام بطال مايعد الطول
        if (m1.isEmpty() || m1.length() == 0 || m1.equals(" ") || m1.equals("") || m1 == null) {
            x = 2; //EditText is empty
        } else {
            m[i] = Integer.parseInt(m1); //EditText is not empty
            scoreRight += m[i];
        }


        // end of chicking and start of counting


        // start of chicking if input is Right
        if (m[i] > 148 || n[i] > 148 || scoreLeft - n[i] > 152 || scoreRight - m[i] > 152) {
            scoreLeft -= n[i];
            scoreRight -= m[i];
            n[i] = 0;
            m[i] = 0;
            if (i >= 2) {
                i -= 1;
            } else {
                i = 0;
            }

            AlertDialog.Builder ABuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.wrong_entry, null);
            ABuilder.setNeutralButton("رجوع", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });
            ABuilder.setView(mView);
            AlertDialog dialog = ABuilder.create();
            dialog.show();
        }
        // end of chickng if input is Right

        else if (scoreRight >= 152 && scoreLeft >= 152) { // when two teams win, who has most points -START-
            if (scoreRight > scoreLeft) {
                displayResultLeft(scoreLeft);
                displayResultRight(scoreRight);
                displayLeftOnScoll(n[i], m[i]);
                displayRightOnScoll(n[i], m[i]);
                EditTextThem.getText().clear();
                EditTextUs.getText().clear();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_right, null);
                mBuilder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int i) {
                        resetWithoutButton();
                        dialoginterface.dismiss();
                    }
                });

                mBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            } else if (scoreRight < scoreLeft) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_left, null);
                mBuilder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int i) {
                        resetWithoutButton();
                        dialoginterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            } else {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("تعادل");
                mBuilder.setMessage(" صكة جديدة ؟");
                mBuilder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int i) {
                        resetWithoutButton();
                        dialoginterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
            // when two teams win, who has most points -END-
        } else if (scoreLeft >= 152) { // checking when team them win -START-
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_left, null);

            mBuilder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    resetWithoutButton();
                    dialoginterface.dismiss();
                }
            });
            mBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
            // checking when team them win -END-
        } else if (scoreRight >= 152) { // checking when team us win -START-
            displayResultLeft(scoreLeft);
            displayResultRight(scoreRight);
            displayLeftOnScoll(n[i], m[i]);
            displayRightOnScoll(n[i], m[i]);
            EditTextThem.getText().clear();
            EditTextUs.getText().clear();
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_right, null);
            mBuilder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    resetWithoutButton();
                    dialoginterface.dismiss();
                }
            });
            mBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        }
        // checking when team us win -END-

//        TextView textViewN = new TextView(MainActivity.this);
//        textViewN.setText(String.valueOf(n[i]));
//        TextView textViewM = new TextView(MainActivity.this);
//        textViewM.setText(String.valueOf(m[i]));
//        textViewN.setGravity(Gravity.CENTER);
//        textViewM.setGravity(Gravity.CENTER);
//        textViewN.set
//        rel1.addView(textViewN);
//        rel1.addView(textViewM);

//        RelativeLayout rel1 = (RelativeLayout) findViewById(R.id.Rel1);
//
//            TextView textViewN = new TextView(this);
//            TextView textViewM = new TextView(this);
//            textViewN.setText(String.valueOf(n[l]));
//            textViewM.setText(String.valueOf(m[l]));
//            textViewN.setGravity(Gravity.LEFT);
//            textViewM.setGravity(Gravity.RIGHT);
//            textViewN.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            textViewM.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//
//            rel1.addView(textViewN);
//            rel1.addView(textViewM);

//
//        LinearLayout rel1 = (LinearLayout) findViewById(R.id.Rel1);
//        TextView textViewN = new TextView(this);
//        TextView textViewM = new TextView(this);
//        textViewN.setText(String.valueOf(n[i]));
//        textViewM.setText(String.valueOf(m[i]));
//        textViewN.setGravity(Gravity.LEFT);
//        textViewM.setGravity(Gravity.RIGHT);
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//        );
//        textViewN.setLayoutParams(params);
//        textViewM.setLayoutParams(params);
//
//        rel1.addView(textViewN);
//        rel1.addView(textViewM);

        int k = 1;
        int j = 1;
        // No conditions left here, this belongs to the main functions
        if (n1.isEmpty() || n1.length() == 0 || n1.equals(" ") || n1.equals("") || n1 == null) {
            //EditText is empty
            k = 2;
        }
        // يطلع الطول شف لهذي حل خله تجمع الأرقام بطال مايعد الطول
        if (m1.isEmpty() || m1.length() == 0 || m1.equals(" ") || m1.equals("") || m1 == null) {
            j = 2;
        }

        if (k == 2 && j == 2) {
            //
        }else{
            i+=1;
            displayResultLeft(scoreLeft);
            displayResultRight(scoreRight);
            displayLeftOnScoll(n[i-1],m[i-1]);
            displayRightOnScoll(n[i-1],m[i-1]);

        }
        EditTextThem.getText().clear();
        EditTextUs.getText().clear();
        l = 1;
        x = 1;

    }




    public void back(View view) {
        if (i >= 1) {
            i-=1;
            scoreLeft -= n[i];
            scoreRight -= m[i];
            displayResultLeft(scoreLeft);
            displayResultRight(scoreRight);
            if(i>=1){
                displayLeftOnScoll(n[i-1],m[i-1]);
                displayRightOnScoll(n[i-1],m[i-1]);}
            else {
                n = new int [100];
                m = new int [100];
                displayLeftOnScoll(0,0);
                displayRightOnScoll(0,0);
            }

        }
    }
//        else {
//            n = new int [100];
//            m = new int [100];
//
//            displayResultLeft(scoreLeft);
//            displayResultRight(scoreRight);
//            displayLeftOnScoll(0,0);
//            displayRightOnScoll(0,0);
//            i = 1;
//        }


    public void reset(View view) {
        if (scoreLeft != 0 || scoreRight != 0) {
            n = new int [100];
            m = new int [100];
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.reset, null);

            mBuilder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    resetWithoutButton(); // it will go to that function that will reset everything
                    dialoginterface.dismiss(); // this will dismiss the pop up
                }
            });
            mBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();

        }

    }

    public void resetWithoutButton() {
        scoreRight = 0;
        scoreLeft = 0;
        displayResultLeft(scoreLeft);
        displayResultRight(scoreRight);
        n[i]=0;
        m[i]=0;
        displayLeftOnScoll(0,0);
        displayRightOnScoll(0,0);
        i = 1; }

    public void displayLeftOnScoll(int nn, int mm){
        TextView teamATextView = (TextView) findViewById(R.id.SecLeft2);
        teamATextView.setText(String.valueOf(nn));
    }
    public void displayRightOnScoll(int nn, int mm){
        TextView teamATextView = (TextView) findViewById(R.id.SecRight2);
        teamATextView.setText(String.valueOf(mm));
    }


    public void displayResultLeft(int scoreL) {
        TextView teamATextView = (TextView) findViewById(R.id.SecLeft);
        teamATextView.setText(String.valueOf(scoreL));
    }
    public void displayResultRight(int scoreR) {
        TextView teamBTextView = (TextView) findViewById(R.id.SecRight);
        teamBTextView.setText(String.valueOf(scoreR));
    }

    public void clickhere(View view) {
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
    View mView = getLayoutInflater().inflate(R.layout.click_here, null);
    mBuilder.setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialoginterface, int i) {
            dialoginterface.dismiss();
        }
    });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    /* public void weWon(){
        String we = "We Won" ;
        TextView textView =(TextView) findViewById(R.id.text);
        textView.setText(String.valueOf(we));}

    public void theyWon(){
        String they = "They Won";
        TextView textView =(TextView) findViewById(R.id.text);
        textView.setText(String.valueOf(they));}*/


}