package com.zeeshan.billcalculator.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.zeeshan.billcalculator.R;
import com.zeeshan.billcalculator.utils.MySharedPreferences;

import androidx.annotation.Nullable;

public class MyBill extends BaseActivity{

    MaterialButton btn_submit;
    TextView units;
    TextView my_bill;

    int price1, price2, price3;
    int current_bill;
    int previous_bill;
    int difference;
    int first_100, upto_500, larger_500;
    TextView bill100, bill500, billabove500;
    int unit1, unit2, unit3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_activity);

        btn_submit = findViewById(R.id.btn_submit);
        units = findViewById(R.id.units);
        my_bill = findViewById(R.id.my_bill);
        bill100 = findViewById(R.id.bill100);
        bill500 = findViewById(R.id.bill500);
        billabove500 = findViewById(R.id.billabove500);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntentWithFinishAll(MyBill.this, WelcomeScreen.class);

            }
        });

        String v1 = MySharedPreferences.getString(MyBill.this, MySharedPreferences.FIRST_PRICE);
        String v2 = MySharedPreferences.getString(MyBill.this, MySharedPreferences.SECOND_PRICE);
        String v3 = MySharedPreferences.getString(MyBill.this, MySharedPreferences.THIRD_PRICE);

        if (v1==null || v2==null || v3==null){
            genaricDialog(this, "Error", "Please refill the price list.", null);
        }
        else{
            price1 = Integer.parseInt(v1);
            price2 = Integer.parseInt(v2);
            price3 = Integer.parseInt(v3);
        }

        current_bill = getIntent().getIntExtra("current_bill", 1);
        previous_bill = getIntent().getIntExtra("previous_bill", 1);

        difference = current_bill - previous_bill;

        int bill = calculateBill(difference);
        units.setText("Your this month consumed units are : "+difference);
        my_bill.setText("Total Billing Amount : "+bill+"-/RS");


        bill100.setText("First 100 Units : "+unit1+" * "+price1+" = "+first_100);
        if (unit2>0) {
            bill500.setText("From 100 to 500 Units : " + unit2 + " * " + price2 + " = " + upto_500);
            bill500.setVisibility(View.VISIBLE);
        }
        if (unit3>0) {
            billabove500.setText("Above 500 Units : " + unit3 + " * " + price3 + " = " + larger_500);
            billabove500.setVisibility(View.VISIBLE);
        }
    }


    public int calculateBill(int units)
    {

        // Condition to find the charges
        // bar in which the units consumed
        // is fall
        if (units <= 100) {
            first_100 = units*price1;
            unit1 = units;
            return units * price1;
        }
        else if (units <= 500) {

            first_100 = 100*price1;
            upto_500 = (units-100)*price2;
            unit1 = 100;
            unit2 = units - 100;

            return (100 * price1)
                    + (units - 100)
                    * price2;
        }
        else if (units > 500) {

            first_100 = 100*price1;
            upto_500 = 400*price2;
            larger_500 = (units-500)*price3;

            unit1 = 100;
            unit2 = 400;
            unit3 = units - 500;

            return (100 * price1)
                    + (400 * price2)
                    + (units - 500)
                    * price3;
        }
        return 0;
    }
}
