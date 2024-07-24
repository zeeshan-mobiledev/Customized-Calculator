package com.zeeshan.billcalculator.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.zeeshan.billcalculator.R;
import com.zeeshan.billcalculator.interfaces.DialogButtonInterface;
import com.zeeshan.billcalculator.utils.MySharedPreferences;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;
    protected MySharedPreferences mySharedPreferences;

    Context context;
    Activity activity;
    Dialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
        setActivity(this);
        mySharedPreferences = new MySharedPreferences();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected void showProgressDialog(String message) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else {
                progressDialog.setCancelable(false);
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideprogressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public String getNumber(String number) {
        if (number != null && !number.isEmpty())
            return number.replaceAll("[^\\d]", "");
        else
            return "";

    }

    public String getFormatedNumber(String number) {
        if (number != null && !number.isEmpty())
            return number.substring(0, 3) + " " + number.substring(3, 6) + " " + number.substring(6);
        else
            return "";
    }


    protected void setTextLimit(EditText editText, int limit) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(limit);
        editText.setFilters(FilterArray);
    }


    protected void setTextEditText(EditText editText, String data) {
        editText.setText(data);
        editText.setSelection(editText.getText().length());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void gotoWelcomeScreen() {
//        Shared.getInstance().callIntentWithFinishAll(getActivity(), WelcomeScreen.class);
        callIntentWithFinishAll(getActivity(), WelcomeScreen.class);

    }
    @Override
    protected void onResume() {
        super.onResume();

    }



    public void callIntentWithFinishAll(Activity context, Class className) {
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), context.getPackageName() + ".activities." + className.getSimpleName());
        context.startActivity(intent);
        context.finish();
    }






    public void genaricDialog(final Context context, final String title, final String message, DialogButtonInterface my_interface) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//        dialog = new Dialog(context, R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen);
        dialog = new Dialog(context, R.style.MyDialogTheme);

//        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_dialog);
        dialog.setCancelable(false);
        if (!((Activity) context).isFinishing()) {
            //show dialog
            dialog.show();
        }

        RelativeLayout rlparent = dialog.findViewById(R.id.rlParent);
//        rlparent.getBackground().setAlpha(100);
        TextView alertTitle = dialog.findViewById(R.id.title);
        TextView alertMessage = dialog.findViewById(R.id.message);
        MaterialButton tvbutton = dialog.findViewById(R.id.btn_action);
//        TextView tvLink = dialog.findViewById(R.id.tvLink);


        alertTitle.setText(title);
        alertMessage.setText(message);



        tvbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(BaseActivity.this);
                dialog.dismiss();
                if(my_interface!=null)
                    my_interface.onButtonPress();
            }
        });
    }

    public boolean validateFields(String consumer_no, String reading) {

        boolean is_error = false;
        if (consumer_no.isEmpty() || reading.isEmpty()) {
            genaricDialog(this, "Error", "All Fields are required", null);
            is_error = true;
        } else if (consumer_no.isEmpty()) {
            genaricDialog(this, "Error", "Please enter consumer number", null);
            is_error = true;
        } else if (Integer.parseInt(reading) <= 0){
            is_error = true;
            genaricDialog(this, "Error", "Reading cannot be Zero or Negative value", null);
        }else {
//            if(!consumer_no.matches("^[a-zA-Z0-9]+$")){
            if(!consumer_no.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$")){
                is_error = true;
                genaricDialog(this, "Error", "Please enter valid consumer number having both alphabets and numbers", null);
                //this is true
            }
            else if(!reading.matches("^[0-9]+$")){
                is_error = true;
                genaricDialog(this, "Error", "Please enter valid reading number.", null);
                //this is true
            }
            else if(consumer_no.length()<10){
                is_error = true;
                genaricDialog(this, "Error", "Please enter valid consumer number.", null);
            }
            else{
//                String previous_reading = MySharedPreferences.getString(BaseActivity.this, MySharedPreferences.PREVIOUS_READING);
                HashMap<String, String> map = MySharedPreferences.getPreviousBill(BaseActivity.this);
                String previous_reading = map.get(consumer_no);
                if (previous_reading!=null && Integer.parseInt(previous_reading)>=Integer.parseInt(reading)){
                    is_error = true;
                    genaricDialog(this, "Error", "Your current reading is less than the previous reading", null);
                }
            }
        }


        return is_error;
    }

    public void callIntent(Activity context, Class className) {
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), context.getPackageName() + ".activities." + className.getSimpleName());
        context.startActivity(intent);
    }


}