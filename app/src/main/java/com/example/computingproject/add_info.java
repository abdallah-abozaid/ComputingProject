package com.example.computingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class add_info extends AppCompatActivity {


Button button_INC_width,button_DEC_width,button_INC_hight,button_DEC_hight;
TextView text_Desplay_width,text_Desplay_hight;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        button_INC_width=findViewById(R.id.increase_wight);
        button_DEC_width=findViewById(R.id.decrease_wight);
        text_Desplay_width=findViewById(R.id.wight_input);

        button_INC_width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CurrentValue=text_Desplay_width.getText().toString();
                int value=Integer.parseInt(CurrentValue);
                value++;
                text_Desplay_width.setText(String.valueOf(value));
            }
        });
        button_DEC_width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CurrentValue=text_Desplay_width.getText().toString();
                int value=Integer.parseInt(CurrentValue);
                value--;
                text_Desplay_width.setText(String.valueOf(value));
            }
        });


        button_INC_hight=findViewById(R.id.increase_hight);
        button_DEC_hight=findViewById(R.id.decrease_hight);
        text_Desplay_hight=findViewById(R.id.hight_input);

        button_INC_hight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CurrentValue=text_Desplay_hight.getText().toString();
                int value=Integer.parseInt(CurrentValue);
                value++;
                text_Desplay_hight.setText(String.valueOf(value));
            }
        });
        button_DEC_hight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CurrentValue=text_Desplay_hight.getText().toString();
                int value=Integer.parseInt(CurrentValue);
                value--;
                text_Desplay_hight.setText(String.valueOf(value));
            }
        });

    }
}
