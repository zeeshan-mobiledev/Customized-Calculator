//package com.toma.driver.activities;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import androidx.cardview.widget.CardView;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import com.toma.driver.R;
//import com.toma.driver.general.Shared;
//import com.toma.driver.constants.GenaricConstants;
//import com.toma.driver.models.DriverInformation;
//import com.toma.driver.utilities.FontTextViewLight;
//import com.toma.driver.utilities.OkhttpUtilities;
//import com.toma.driver.utilities.RandomUtils;
//import com.toma.driver.utilities.SharedPrefrenceUtils;
//import com.toma.driver.utilities.ValidationsCheckUtilities;
//import com.toma.driver.utilities.WebConstants;
//
//public class LoginActivity extends BaseActivity implements View.OnClickListener {
//
//    private EditText etEmail;
//    private EditText etPassword;
//    FontTextViewLight tv_Privacy_policy;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);
//        initializeViews();
//    }
//
//    private void initializeViews() {
//        etEmail = findViewById(R.id.et_email);
//        etPassword = findViewById(R.id.et_password);
//        CardView cvBtnLoginSection = findViewById(R.id.cv_btn_login_section);
//        CardView cv_btn_home = findViewById(R.id.cv_btn_home);
//        tv_Privacy_policy = findViewById(R.id.tv_Privacy_policy);
//        LinearLayout rlSignupLink = findViewById(R.id.rl_signup_link);
//        TextView tvForgotPassword = findViewById(R.id.tv_forgot_password);
//        tv_Privacy_policy.setOnClickListener(this);
//        tvForgotPassword.setOnClickListener(this);
//        cvBtnLoginSection.setOnClickListener(this);
//        rlSignupLink.setOnClickListener(this);
//        cv_btn_home.setOnClickListener(this);
//
//
//        String last_login_email = mySharedPreferences.getLastLoginEmail();
//        if (last_login_email!=null && !last_login_email.equals("")){
//            etEmail.setText(last_login_email);
//            etEmail.setSelection(etEmail.getText().length());
//
//        }
//    }
//
//
//    // Click listeners of this activity.
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.cv_btn_home:
//                onBackPressed();
//                break;
//            case R.id.cv_btn_login_section:
//                if (!TextUtils.isEmpty(etEmail.getText()) && ValidationsCheckUtilities.isValidEmail(etEmail.getText().toString())) {
//                    if (!TextUtils.isEmpty(etPassword.getText())) {
//                        if (ValidationsCheckUtilities.isInternetAvailable(LoginActivity.this)) {
//                            SignInCall();
//                        } else {
//                            showAlertDialog(getResources().getString(R.string.internet_error));
//                        }
//
//                    } else {
//                        etPassword.requestFocus();
//                        etPassword.setError(getResources().getString(R.string.enter_password_error));
//                    }
//
//                } else {
//                    etEmail.requestFocus();
//                    etEmail.setError(getResources().getString(R.string.enter_email_error));
//                }
//                break;
//            case R.id.rl_signup_link:
//                Shared.getInstance().callIntent(getActivity(), PhoneNumberSignUp.class);
//                break;
//            case R.id.tv_forgot_password:
//                RandomUtils.startActivity(LoginActivity.this, ForgotPassword.class);
//                finish();
//                break;
//            case R.id.tv_Privacy_policy:
//
//                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.gotoma.com/privacy"));
//                startActivity(intent);
//
//                break;
//        }
//    }
//
//
//    // API Call and Response handling to set the Sign up call.
//    private void SignInCall() {
//        showProgressDialog(getResources().getString(R.string.signingin));
//
//        RequestBody formBody = new FormEncodingBuilder()
//                .add(WebConstants.SIGN_IN_PARAM_EMAIL, etEmail.getText().toString())
//                .add(WebConstants.SIGN_IN_PARAM_PASSWORD, etPassword.getText().toString())
//                .add(WebConstants.SIGN_IN_PARAM_DEVICE_TYPE, WebConstants.SIGN_UP_DEVICE_TYPE)
//                .add(WebConstants.SIGN_IN_PARAM_DEVICE_TOKEN, SharedPrefrenceUtils.getString(LoginActivity.this, GenaricConstants.TOKEN_FCM_KEY, WebConstants.DEFAULT_DEVICE_TOKEN_VALUE))
//                .add(WebConstants.SIGN_UP_PARAM_TYPE, WebConstants.SIGN_UP_PARAM_TYPE_DRIVER)
//                .build();
//
//
//        OkHttpClient okHttpClient = OkhttpUtilities.getTrustedHttpClient(new OkHttpClient());
//        final Request request = new Request.Builder()
//                .url(WebConstants.SIGN_IN)
//                .post(formBody)
//                .build();
//
//        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, final IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        hideProgressDialog();
//                        showAlertDialog(e.getMessage());
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                final String requestResult = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            JSONObject jsonObjectMain = new JSONObject(requestResult);
//
//                            JSONObject meta = jsonObjectMain.getJSONObject("meta");
//
//                            String code = meta.getString("code");
//                            String message = meta.getString(WebConstants.MESSAGE);
//                            if (code.equalsIgnoreCase(WebConstants.SUCCESS_RESPONCE_CODE)) {
//                                int badgeCount = jsonObjectMain.getInt("badge");
//                                setBadge(badgeCount);
//
//                                JSONObject jsonObject = jsonObjectMain.getJSONObject("result");
//                                String user_id = jsonObject.getString("user_id");
//                                String first_name = jsonObject.getString("first_name");
//                                String last_name= jsonObject.getString("last_name");
//                                String email = jsonObject.getString("email");
//                                String verified = jsonObject.getString("verified");
//                                String auth_code = jsonObject.getString("auth_code");
//                                String profile_id = jsonObject.getString("profile_id");
//
//                                DriverInformation driverInformation = null;
//                                if (SharedPrefrenceUtils.getString(LoginActivity.this, GenaricConstants.USER_OBJECT) != null) {
//                                    driverInformation = new Gson().fromJson(SharedPrefrenceUtils.getString(LoginActivity.this, GenaricConstants.USER_OBJECT), DriverInformation.class);
//                                } else {
//                                    driverInformation = new DriverInformation();
//                                }
//
//                                driverInformation.setProfile_id(profile_id);
//                                driverInformation.setPassword(etPassword.getText().toString());
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.USER_OBJECT, driverInformation.toJsonString());
//
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.USER_ID_KEY, user_id);
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.LOG_IN_STATUS, GenaricConstants.TRUE_VALUE);
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.FIRS_NAME_KEY, first_name);
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.LAST_NAME_KEY, last_name);
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.EMAIL_KEY, email);
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.SIGN_UP_STATUS, GenaricConstants.TRUE_VALUE);
//                                SharedPrefrenceUtils.putString(LoginActivity.this, GenaricConstants.AUTH_CODE_KEY, auth_code);
//
//                                RandomUtils.startActivityRemoveStact(LoginActivity.this, Dashboard.class);
//                                finish();
//
//                            } else {
//                                String title = meta.optString("title");
//                                String btn_text = meta.optString("btn_action");
//                                String link_text = meta.optString("btn_link");
//
//                                newDialogGenaric(title, message, btn_text, link_text, "", null);
//                            }
//                        } catch (Exception e) {
//                            showAlertDialog(e.getMessage());
//                        }
//                        hideProgressDialog();
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//}