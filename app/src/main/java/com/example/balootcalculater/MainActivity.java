package com.example.balootcalculater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.widget.NumberPicker;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {
    int scoreRight = 0, scoreLeft = 0;
    int[] n = new int [100];
    int[] m = new int [100];
    int i,l = 1;


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

        if (n1.isEmpty()||n1.length() == 0||n1.equals("")||n1 == null){
            n[i] = 0;   //EditText is empty
        }else{ n[i] = Integer.parseInt(n1);  //EditText is not empty
            }
        EditText EditTextThem = (EditText) findViewById(R.id.counter_them);
        String m1 = EditTextThem.getText().toString();  // يطلع الطول شف لهذي حل خله تجمع الأرقام بطال مايعد الطول
        if (m1.isEmpty()||m1.length() == 0||m1.equals("")||m1 == null) {
            m[i] = 0;   //EditText is empty
        } else {
            m[i] = Integer.parseInt(m1); //EditText is not empty
        }

        // end of chicking and start of counting
        scoreLeft += n[i];
        scoreRight += m[i];
        displayLeftOnScoll(n[i],m[i]);
        displayRightOnScoll(n[i],m[i]);

        // start of chicking if input is Right
        if (m[i] > 148 || n[i] > 148 || scoreLeft - n[i] > 152 || scoreRight - m[i] > 152) {
            scoreLeft -= n[i];
            scoreRight -= m[i];
            n[i] = 0;
            m[i] = 0;
            AlertDialog.Builder ABuilder = new AlertDialog.Builder(MainActivity.this);
            ABuilder.setTitle("إدخال خاطأ");
            ABuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.dismiss();
                }
            });
            AlertDialog dialog = ABuilder.create();
            dialog.show();
        }
        // end of chickng if input is Right

        else if (scoreRight >= 152 && scoreLeft >= 152) { // when two teams win, who has most points -START-
            if (scoreRight > scoreLeft) {
                displayResultLeft(scoreLeft);
                displayResultRight(scoreRight);
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

        // No conditions left here, this belongs to the main functions
        i += 1;
        displayResultLeft(scoreLeft);
        displayResultRight(scoreRight);
        EditTextThem.getText().clear();
        EditTextUs.getText().clear();
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
                displayLeftOnScoll(0,0);
                displayRightOnScoll(0,0);
            }

        }
         }

    public void reset(View view) {
        if (scoreLeft != 0 || scoreRight != 0) {
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
        displayLeftOnScoll(0,0);
        displayRightOnScoll(0,0);
        i = 0; }

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

    /* public void weWon(){
        String we = "We Won" ;
        TextView textView =(TextView) findViewById(R.id.text);
        textView.setText(String.valueOf(we));}

    public void theyWon(){
        String they = "They Won";
        TextView textView =(TextView) findViewById(R.id.text);
        textView.setText(String.valueOf(they));}*/


}