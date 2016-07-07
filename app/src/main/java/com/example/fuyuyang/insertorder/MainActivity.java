package com.example.fuyuyang.insertorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuyuyang.insertorder.SubArrary;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    int[] numberList = null;
    TextView textView = null;
    String string = "";
    Button button = null;

    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String str = "0";
        textView = (TextView) findViewById(R.id.textViewRan);
        button = (Button) findViewById(R.id.buttonStart);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Square square = new Square();
                int[][] sq1 = square.getSquart(256, 256, 0, 100);
                int[][] sq2 = square.getSquart(256, 256, 0, 100);

//                int[][] sq1 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
//                int[][] sq2 = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 16}};

                int[][] res = square.multiplyingSquart(sq1, sq2);
                int[][] res2 = square.mergeSquartSub(sq1, sq2);

                for(int i=0;i<res.length;i++)
                {
                    for(int j=0;j<res[i].length;j++)
                    {
                        if(res[i][j]!=res2[i][j])
                        {
                            Toast.makeText(MainActivity.this, "NOT OK+" + square.count, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                if (res.length > 2) {

                    Toast.makeText(MainActivity.this, "OK+" + square.count, Toast.LENGTH_LONG).show();
                    textView.setText(textView.getText().toString() + "\r\n\r\n" + square.count);
                }
            }
        });

        getRanList getRanList = new getRanList();
        numberList = getRanList.getList(0, 200, 10);
        numberList[0] = 100;
//        dislpayIntList(textView, numberList);
    }

    public void dislpayIntList(TextView textView, int[] list) {
        String string = "";
        for (int i = 0; i < list.length; i++) {
            string += list[i] + " ";
        }
        textView.setText(textView.getText().toString() + "\r\n\r\n" + string);
    }

    public boolean checkEqu(int[] a1, int[] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

}
