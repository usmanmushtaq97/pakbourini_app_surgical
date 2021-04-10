package com.tss.pakbourini.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.tss.pakbourini.DBhelperclasses.CartsTableModel;
import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.tss.pakbourini.Activity.SignUp.userpref;

public class GetUserDetails extends AppCompatActivity {
    Button mSubmit;
    TextInputEditText mFirstNameET;
    TextInputEditText mLastNameET;
    TextInputEditText mPhone;
    TextInputEditText mEmailEt;
    String name;
    String email;
    String currentuser = "users";
    String mLastName;
    String mobileNumber;
    String mDiningStatus, mPostStatus, mTimeReceive;
    DataBaseCarts dataBaseCarts;
    String totalpayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);
        dataBaseCarts = DataBaseCarts.getInstance(this);
           if(FirebaseAuth.getInstance().getCurrentUser() != null) {
               currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
           }
        SharedPreferences sharedPreferences =getSharedPreferences(userpref,MODE_PRIVATE);
        email = sharedPreferences.getString("email", "email");
        name = sharedPreferences.getString("fname","fname");
        mLastName  = sharedPreferences.getString("lname","lastname");
        mobileNumber = sharedPreferences.getString("phoneNumber","+923001234567");
        init();
        setTextData();
        getIntentValue();
         mSubmit.setOnClickListener(v -> {
               if(IsNotEmpty()) {
                   ReplaceOder();
               }
         });
    }
    private void setTextData(){
        mFirstNameET.setText(name);
        mLastNameET.setText(mLastName);
        mEmailEt.setText(email);
        mPhone.setText(mobileNumber);
    }
    private void init() {
        mSubmit = findViewById(R.id.submit_o);
        mFirstNameET = findViewById(R.id.fullname_o);
        mLastNameET = findViewById(R.id.lname_o);
        mEmailEt= findViewById(R.id.email_o);
        mPhone = findViewById(R.id.mobilenumber_o);
    }
    private Boolean IsNotEmpty() {
        name = mFirstNameET.getText().toString();
        mLastName = mLastNameET.getText().toString();
        email = mEmailEt.getText().toString();
       mobileNumber = mPhone.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter The Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLastName.isEmpty()) {
            Toast.makeText(this, "Enter LastName", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobileNumber.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
     private  void getIntentValue(){
        Intent intent = getIntent();
         mDiningStatus = intent.getStringExtra("poststatus");
         mPostStatus = intent.getStringExtra("diningstatus");
         mTimeReceive= intent.getStringExtra("timereceive");
         totalpayment = intent.getStringExtra("totalprice");
     }
    private void ReplaceOder(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://technicaltea.e-sialkot.com/Admin/apis/oderdetails.php", response -> {
            Log.d("check",response);
            if (response.equalsIgnoreCase("Successfully Submitted")) {
                progressDialog.dismiss();
                Toast.makeText(GetUserDetails.this, "Successfully Send Request", Toast.LENGTH_SHORT).show();
                List< CartsTableModel > oderitem = dataBaseCarts.daoCarts().getAll();
                dataBaseCarts.daoCarts().DeleteAllCarts(oderitem);
                Intent intent = new Intent(GetUserDetails.this, Sucessfully.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(GetUserDetails.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(GetUserDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                List<CartsTableModel> oderitem = dataBaseCarts.daoCarts().getAll();
                Gson gson = new Gson();
                String data = gson.toJson(oderitem);
                Log.d("orderdatacheck",data);
                params.put("fname",name);
                params.put("user_id",currentuser);
                params.put("lname",mLastName);
                params.put("email",email );
                params.put("ph", mobileNumber);
                params.put("oder_post_pr", "PreOder");
                params.put("total_price",totalpayment);
                params.put("dining_type", "Dining");
                params.put("time_receive", mTimeReceive);
                params.put("oderitems",data);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}