package com.example.fuyuyang.insertorder;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ColorActivity extends AppCompatActivity {

    EditText editTextColor1R = null;
    EditText editTextColor1G = null;
    EditText editTextColor1B = null;
    EditText editTextColor2R = null;
    EditText editTextColor2G = null;
    EditText editTextColor2B = null;
    TextView textViewColor3R = null;
    TextView textViewColor3G = null;
    TextView textViewColor3B = null;
    RelativeLayout relativeLayoutColor1=null;
    RelativeLayout relativeLayoutColor2=null;
    RelativeLayout relativeLayoutColor3=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        getViewID();

    }

    public void getViewID() {
        editTextColor1R = (EditText) findViewById(R.id.editTextColor1R);
        editTextColor1G = (EditText) findViewById(R.id.editTextColor1G);
        editTextColor1B = (EditText) findViewById(R.id.editTextColor1B);
        editTextColor2R = (EditText) findViewById(R.id.editTextColor2R);
        editTextColor2G = (EditText) findViewById(R.id.editTextColor2G);
        editTextColor2B = (EditText) findViewById(R.id.editTextColor2B);
        textViewColor3R = (TextView) findViewById(R.id.textViewColor3R);
        textViewColor3G = (TextView) findViewById(R.id.textViewColor3G);
        textViewColor3B = (TextView) findViewById(R.id.textViewColor3B);
        relativeLayoutColor1=(RelativeLayout)findViewById(R.id.relativeLayoutColor1);
        relativeLayoutColor2=(RelativeLayout)findViewById(R.id.relativeLayoutColor2);
        relativeLayoutColor3=(RelativeLayout)findViewById(R.id.relativeLayoutColor3);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addColor();
            }
        };

        editTextColor1R.addTextChangedListener(textWatcher);
        editTextColor1G.addTextChangedListener(textWatcher);
        editTextColor1B.addTextChangedListener(textWatcher);
        editTextColor2R.addTextChangedListener(textWatcher);
        editTextColor2G.addTextChangedListener(textWatcher);
        editTextColor2B.addTextChangedListener(textWatcher);
        addColor();
    }

    public void addColor() {
        int color1R = 0;
        int color1G = 0;
        int color1B = 0;
        int color2R = 0;
        int color2G = 0;
        int color2B = 0;
        int color3R = 0;
        int color3G = 0;
        int color3B = 0;

        if(editTextColor1R.getText().toString().length()!=0) {
            color1R = Integer.decode("0x" + editTextColor1R.getText().toString());
        }
        if(editTextColor1G.getText().toString().length()!=0) {
            color1G = Integer.decode("0x" + editTextColor1G.getText().toString());
        }
        if(editTextColor1B.getText().toString().length()!=0) {
            color1B = Integer.decode("0x" + editTextColor1B.getText().toString());
        }
        if(editTextColor2R.getText().toString().length()!=0) {
            color2R = Integer.decode("0x" + editTextColor2R.getText().toString());
        }
        if(editTextColor2G.getText().toString().length()!=0) {
            color2G = Integer.decode("0x" + editTextColor2G.getText().toString());
        }
        if(editTextColor2B.getText().toString().length()!=0) {
            color2B = Integer.decode("0x" + editTextColor2B.getText().toString());
        }

        textViewColor3R.setText(Integer.toHexString((color1R+color2R)/2));
        textViewColor3G.setText(Integer.toHexString((color1G+color2G)/2));
        textViewColor3B.setText(Integer.toHexString((color1B+color2B)/2));

        relativeLayoutColor1.setBackgroundColor(Color.rgb(color1R,color1G,color1B));
        relativeLayoutColor2.setBackgroundColor(Color.rgb(color2R,color2G,color2B));
        relativeLayoutColor3.setBackgroundColor(Color.rgb((color1R+color2R)/2,(color1G+color2G)/2,(color1B+color2B)/2));
    }

    public void errorMessage() {
        Toast.makeText(ColorActivity.this, "输入格式错误\r\n请输入0~9,A~F的字符", Toast.LENGTH_LONG).show();
    }

    public void errorMessage(String errorMessage) {
        Toast.makeText(ColorActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }
}