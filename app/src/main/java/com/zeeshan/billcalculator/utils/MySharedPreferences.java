package com.zeeshan.billcalculator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class MySharedPreferences {

        private static String PREFERENCE_NAME = "BillingappMZ";

        public static String FIRST_PRICE = "first_hundred";
        public static String SECOND_PRICE = "upto_5hundred";
        public static String THIRD_PRICE = "above_5hundred";
        public static String PREVIOUS_READING = "previous_reading";
        public static String CONSUMER_NO = "consumer_number";
        public static String CONSUMER_DATA = "consumer_data";


        public MySharedPreferences() {

        }



    /**
         * put string preferences
         *
         * @param context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static void putString(Context context, String key, String value) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(key, value);
            editor.commit();
        }

        /**
         * get string preferences
         *
         * @param context
         * @param key     The name of the preference to retrieve
         * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
         * name that is not a string
         * @see #getString(Context, String, String)
         */
        public static String getString(Context context, String key) {
            return getString(context, key, null);
        }

        HashMap<String, String> consumer_data = new HashMap<String, String>();


    public static void savePreviousBill(Context context, HashMap<String,String> inputMap) {
        SharedPreferences pSharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            pSharedPref.edit()
                    .remove(CONSUMER_DATA)
                    .putString(CONSUMER_DATA, jsonString)
                    .apply();
        }
    }

    public static HashMap<String,String> getPreviousBill(Context context) {
        HashMap<String,String> outputMap = new HashMap<>();
        SharedPreferences pSharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString(CONSUMER_DATA, (new JSONObject()).toString());
                if (jsonString != null) {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Iterator<String> keysItr = jsonObject.keys();
                    while (keysItr.hasNext()) {
                        String key = keysItr.next();
                        String value = jsonObject.getString(key);
                        outputMap.put(key, value);
                    }
                }
            }
        } catch (JSONException e){
            e.printStackTrace();

            return null;
        } catch (Exception e){
            return null;
        }
        return outputMap;
    }


        /**
         * get string preferences
         *
         * @param context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a string
         */
        public static String getString(Context context, String key, String defaultValue) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getString(key, defaultValue);
        }

        /**
         * put int preferences
         *
         * @param context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static void putInt(Context context, String key, int value) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(key, value);
            editor.commit();
            editor.apply();
        }

        /**
         * get int preferences
         *
         * @param context
         * @param key     The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a int
         * @see #getInt(Context, String, int)
         */
        public static int getInt(Context context, String key) {
            return getInt(context, key, -1);
        }


        /**
         * get int preferences
         *
         * @param context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a int
         */
        public static int getInt(Context context, String key, int defaultValue) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getInt(key, defaultValue);
        }

        /**
         * put long preferences
         *
         * @param context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putLong(Context context, String key, long value) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong(key, value);
            return editor.commit();
        }

        /**
         * get long preferences
         *
         * @param context
         * @param key     The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a long
         * @see #getLong(Context, String, long)
         */
        public static long getLong(Context context, String key) {
            return getLong(context, key, -1);
        }

        /**
         * get long preferences
         *
         * @param context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a long
         */
        private static long getLong(Context context, String key, long defaultValue) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getLong(key, defaultValue);
        }

        /**
         * put float preferences
         *
         * @param context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static void putFloat(Context context, String key, float value) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putFloat(key, value);
            editor.commit();
            editor.apply();
        }

        /**
         * get float preferences
         *
         * @param context
         * @param key     The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a float
         * @see #getFloat(Context, String, float)
         */
        public static float getFloat(Context context, String key) {
            return getFloat(context, key, -1);
        }

        /**
         * get float preferences
         *
         * @param context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a float
         */
        private static float getFloat(Context context, String key, float defaultValue) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getFloat(key, defaultValue);
        }

        /**
         * put boolean preferences
         *
         * @param context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        public static boolean putBoolean(Context context, String key, boolean value) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }

        /**
         * get boolean preferences, default is false
         *
         * @param context
         * @param key     The name of the preference to retrieve
         * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
         * name that is not a boolean
         * @see #getBoolean(Context, String, boolean)
         */
        public static boolean getBoolean(Context context, String key) {
            return getBoolean(context, key, false);
        }

        /**
         * get boolean preferences
         *
         * @param context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a boolean
         */
        private static boolean getBoolean(Context context, String key, boolean defaultValue) {
            SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            return settings.getBoolean(key, defaultValue);
        }

//        public static void clearAllSharedPrefs(Context context) {
//            SharedPrefrenceUtils.putString(context, GenaricConstants.BANK_INFO_ADDED, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.CAR_INFO_ADDED, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.PHONE_NUMBER_VERIFIED, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.USER_ID_KEY, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.LOG_IN_STATUS, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.PHONE_NUMBER_ENTERED, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.temp_phone_saved, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.PROFILE_INFO_ADDED, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.FIRS_NAME_KEY, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.RIDE_ID_FOR_LOCATION, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.EMAIL_KEY, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.SIGN_UP_STATUS, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.AUTH_CODE_KEY, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.USER_ACCOUNT_VERIFICATION_STATUS, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.SIGN_UP_STATUS_WEB, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.LAST_NAME_KEY, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.STICKY_NOTIFICATION_RIDE_ID, "");
//            SharedPrefrenceUtils.putString(context, GenaricConstants.STICKY_NOTIFICATION_NOTIFICATION_ID, "");
//            SharedPrefrenceUtils.putBoolean(context, GenaricConstants.IS_SERVICE_RUNNING, false);
//            SharedPrefrenceUtils.putString(context, GenaricConstants.USER_OBJECT, null);
//            SharedPrefrenceUtils.putInt(context, GenaricConstants.CONTACTS, 0);
//        }
    }
