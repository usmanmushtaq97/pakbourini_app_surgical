package com.tss.pakbourini.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.tss.pakbourini.R;
import static com.tss.pakbourini.Constant.SPLASH_TIME;
public class MainActivity extends AppCompatActivity {
    Handler handler;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
           if(FirebaseAuth.getInstance().getCurrentUser() != null){
            handler = new Handler();
            handler.postDelayed(() -> {
                intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }, SPLASH_TIME);
        }
        else {
            intent = new Intent(MainActivity.this, GetMobileNumber.class);
            startActivity(intent);
            finish();
        }
    }
}
