package com.tss.pakbourini.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tss.pakbourini.R;


public class
SignUp extends AppCompatActivity {
    Button mNext;
    TextInputEditText mFirstNameET;
    TextInputEditText mLastNameET;
    TextInputEditText mPasswordEt;
    TextInputEditText mEmailEt;
    TextInputEditText mCountryET;
    TextInputEditText mLastCityET;
    TextInputEditText mAddressEt;
    TextInputEditText mZipCodeEt;
    String name;
    String email;
    String mLastName;
    String password;
    String mobileNumber;
    String country,city,address,zipcode;
    public static String userpref = "USER_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //methods initialize
        init();
        getIntentValue();
      //  getIntentValue();
        mNext.setOnClickListener(v ->
        {
            if (IsNotEmpty()) {
                SavedValueInSharedPref();
                User_register();
                Intent intent = new Intent(SignUp.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void init() {
        mNext = findViewById(R.id.signup_bt_next);
        mFirstNameET = findViewById(R.id.signup_fullname_id);
        mLastNameET = findViewById(R.id.sginup_username_id);
        mEmailEt = findViewById(R.id.signuemail_id);
        mPasswordEt = findViewById(R.id.signup_password_id);
        mCountryET =findViewById(R.id.signup_country_id);
        mLastCityET =findViewById(R.id.signup_city_id);
        mAddressEt =findViewById(R.id.signup_adress_id);
        mZipCodeEt =findViewById(R.id.signup_zipcode_id);
    }

    private void getIntentValue() {
        Intent intent = getIntent();
      mobileNumber = intent.getStringExtra("mobilenumber");
    }

    private Boolean IsNotEmpty() {
        name = mFirstNameET.getText().toString();
        mLastName = mLastNameET.getText().toString();
        email = mEmailEt.getText().toString();
        password = mPasswordEt.getText().toString();
        country = mCountryET.getText().toString();
        city = mLastCityET.getText().toString();
        address = mAddressEt.getText().toString();
        zipcode = mZipCodeEt.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter The Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLastName.isEmpty()) {
            Toast.makeText(this, "Enter LastName", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (country.isEmpty()) {
            Toast.makeText(this, "Enter Country", Toast.LENGTH_SHORT).show();
            return false;
        } else if (city.isEmpty()) {
            Toast.makeText(this, "Enter City", Toast.LENGTH_SHORT).show();
            return false;
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Enter adress", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (zipcode.isEmpty()) {
            Toast.makeText(this, "Enter ZioCode", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void SavedValueInSharedPref() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(userpref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phoneNumber", mobileNumber);
        editor.putString("fname", name);
        editor.putString("lname", mLastName);
        editor.putString("email", email);
        editor.putString("country", country);
        editor.putString("city", city);
        editor.putString("address", address);
        editor.putString("zipcode", zipcode);
        editor.apply();
    }
    private void User_register() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://technicaltea.e-sialkot.com/Admin/apis/registeruser.php?fname=" + name + "&lname=" + mLastName + "&email=" + email + "&mobile=" + mobileNumber + "&pas=" + password
                , response -> {
                 if(response.contentEquals("sucessfully inserted")){
                     Toast.makeText(this, "sucessfully inserted", Toast.LENGTH_SHORT).show();
                 }

                }, error -> Toast.makeText(this, "errorrr", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}