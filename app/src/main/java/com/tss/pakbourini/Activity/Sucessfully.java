package com.tss.pakbourini.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.R;


public class Sucessfully extends AppCompatActivity {
   DataBaseCarts dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = DataBaseCarts.getInstance(Sucessfully.this);
        setContentView(R.layout.activity_sucessfully);
        Button bthome = findViewById(R.id.gotohome);
        bthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Sucessfully.this, HomeActivity.class);
                dataBase.daoCarts().DeleteAllCarts(dataBase.daoCarts().getAll());
                finish();
                startActivity(intent);


            }
        });
    }
}