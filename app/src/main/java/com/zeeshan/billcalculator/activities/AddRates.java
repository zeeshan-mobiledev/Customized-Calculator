package com.zeeshan.billcalculator.activities;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.zeeshan.billcalculator.R;
import com.zeeshan.billcalculator.interfaces.DialogButtonInterface;
import com.zeeshan.billcalculator.utils.MySharedPreferences;

import androidx.annotation.Nullable;

public class AddRates extends BaseActivity{

    MaterialButton btn_submit;
    TextInputEditText price1;
    TextInputEditText price2;
    TextInputEditText price3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rates);

        btn_submit = findViewById(R.id.btn_submit);
        price1 = findViewById(R.id.price1);
        price2 = findViewById(R.id.price2);
        price3 = findViewById(R.id.price3);
        price1 = findViewById(R.id.price1);

        String v1 = MySharedPreferences.getString(AddRates.this, MySharedPreferences.FIRST_PRICE);
        String v2 = MySharedPreferences.getString(AddRates.this, MySharedPreferences.SECOND_PRICE);
        String v3 = MySharedPreferences.getString(AddRates.this, MySharedPreferences.THIRD_PRICE);

        if(v1!=null && !v1.isEmpty()){
            price1.setText(v1);
            price2.setText(v2);
            price3.setText(v3);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (price1.getText().toString().isEmpty())
                    price1.setError("Please enter price");
                else if (price2.getText().toString().isEmpty())
                    price2.setError("Please enter price");
                else if (price3.getText().toString().isEmpty())
                    price3.setError("Please enter price");
                else{

                    MySharedPreferences.putString(AddRates.this, MySharedPreferences.FIRST_PRICE, price1.getText().toString());
                    MySharedPreferences.putString(AddRates.this, MySharedPreferences.SECOND_PRICE, price2.getText().toString());
                    MySharedPreferences.putString(AddRates.this, MySharedPreferences.THIRD_PRICE, price3.getText().toString());


                    genaricDialog(AddRates.this, "Success", "Billing rates saved Successfully", new DialogButtonInterface() {
                        @Override
                        public void onButtonPress() {
                            finish();
                        }
                    });
                }
            }
        });
        setTextLimit(price1, 2);
        setTextLimit(price2, 2);
        setTextLimit(price3, 2);

    }
}
