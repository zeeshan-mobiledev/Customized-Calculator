package com.zeeshan.billcalculator.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.zeeshan.billcalculator.R;
import com.zeeshan.billcalculator.utils.MySharedPreferences;

import androidx.annotation.Nullable;

public class WelcomeScreen  extends BaseActivity implements View.OnClickListener{

    MaterialButton btn_rate;
    MaterialButton btn_bill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        btn_bill = findViewById(R.id.btn_bill);
        btn_rate = findViewById(R.id.btn_rate);
        btn_bill.setOnClickListener(this);
        btn_rate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_bill){

            String price = MySharedPreferences.getString(WelcomeScreen.this, MySharedPreferences.FIRST_PRICE);
            if(price == null || (price!=null && price.isEmpty())){
                genaricDialog(WelcomeScreen.this, "Error", "Please add billing rates first", null);
            }
            else{
                callIntent(this, ReadingForm.class);
            }

        }
        else if (v.getId() == R.id.btn_rate){
            callIntent(this, AddRates.class);

        }
    }

}
