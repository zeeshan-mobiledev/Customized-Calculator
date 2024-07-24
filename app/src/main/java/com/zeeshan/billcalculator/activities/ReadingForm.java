package com.zeeshan.billcalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.zeeshan.billcalculator.R;
import com.zeeshan.billcalculator.utils.MySharedPreferences;

import java.util.HashMap;

import androidx.annotation.Nullable;

public class ReadingForm extends BaseActivity implements View.OnClickListener{

    MaterialButton btn_submit;
    TextInputEditText consumer_number;
    TextInputEditText meter_reading;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_layout);

        btn_submit = findViewById(R.id.btn_submit);
        consumer_number = findViewById(R.id.consumer_number);
        meter_reading = findViewById(R.id.meter_reading);
        btn_submit.setOnClickListener(this);
        setTextLimit(consumer_number, 10);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit){

            boolean is_errors = validateFields(consumer_number.getText().toString(), meter_reading.getText().toString());
            if (!is_errors){

                HashMap<String, String> map = MySharedPreferences.getPreviousBill(ReadingForm.this);

//                String previous_reading = MySharedPreferences.getString(ReadingForm.this, MySharedPreferences.PREVIOUS_READING);
//                if (previous_reading == null)
//                    previous_reading = "0";

                Bundle bundle = new Bundle();
                bundle.putInt("current_bill", Integer.parseInt(meter_reading.getText().toString()));
//                bundle.putInt("previous_bill", Integer.parseInt(previous_reading));
                if (map!=null && map.size()>0 && map.containsKey(consumer_number.getText().toString())){
                    bundle.putInt("previous_bill", Integer.parseInt(map.get(consumer_number.getText().toString())));
                }
                else{
                    bundle.putInt("previous_bill", 0);
                }

                HashMap<String, String> map_value = new HashMap<>();
                map_value.put(consumer_number.getText().toString(), meter_reading.getText().toString());

                MySharedPreferences.savePreviousBill(ReadingForm.this, map_value);

//                MySharedPreferences.putString(ReadingForm.this, MySharedPreferences.CONSUMER_NO, consumer_number.getText().toString());
//                MySharedPreferences.putString(ReadingForm.this, MySharedPreferences.PREVIOUS_READING, meter_reading.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MyBill.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}
