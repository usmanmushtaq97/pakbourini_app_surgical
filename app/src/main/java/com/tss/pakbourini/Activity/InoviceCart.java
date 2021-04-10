package com.tss.pakbourini.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.tss.pakbourini.DBhelperclasses.CartsTableModel;
import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.Fragments.Carts;
import com.tss.pakbourini.R;
import com.tss.pakbourini.controller.InoviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class InoviceCart extends AppCompatActivity {
    private RecyclerView mCategoryRV;
    private InoviceAdapter mCartsAdapter;
    private List< CartsTableModel > mlist;
    private TextView mtextviewCountitem;
    private TextView tv_totalprice;
    DataBaseCarts dataBaseCarts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovice_cart);
       dataBaseCarts = DataBaseCarts.getInstance(InoviceCart.this);
        init();
       Loaditems();
        Carts obj  = new Carts();

       tv_totalprice.setText(String.valueOf(obj.TotalPriceCount()));
    }
    private void init(){
        mCategoryRV = findViewById(R.id.invoicerv);
        tv_totalprice= findViewById(R.id.tv_total_price_id);
    }
    private void Loaditems(){
        mlist = new ArrayList<>();
        mlist = dataBaseCarts.daoCarts().getAll();
        mCartsAdapter = new InoviceAdapter(this, mlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mCategoryRV.setLayoutManager(layoutManager);
        mCategoryRV.setAdapter(mCartsAdapter);
        mCartsAdapter.notifyDataSetChanged();
    }
}