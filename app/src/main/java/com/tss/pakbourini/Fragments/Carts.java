package com.tss.pakbourini.Fragments;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tss.pakbourini.Activity.HomeActivity;
import com.tss.pakbourini.Activity.InoviceCart;
import com.tss.pakbourini.DBhelperclasses.CartsTableModel;
import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.R;
import com.tss.pakbourini.controller.CartsAdapter;

import java.util.ArrayList;
import java.util.List;
public class Carts extends Fragment {
    private Context mContext;
    private RecyclerView mCategoryRV;
    private CartsAdapter mCartsAdapter;
    private static List< CartsTableModel > mlist;
    private TextView mtextviewCountitem;
    private TextView tv_totalprice;
    private View view;
    CardView cartsdetailslayout;
    LinearLayout constraintLayout;
    static DataBaseCarts dataBaseCarts;
    private Toolbar carts_toolbar;
    String mDiningStatus, mPostStatus;
    TextView timereceive;
    Button bt_next;
    Button checkout;
    Button continueShoping;
    public Carts() {
        // Required empty public constructor
    }
    // attach context
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // access the data base instance
        dataBaseCarts = DataBaseCarts.getInstance(mContext);
        view = inflater.inflate(R.layout.fragment_carts_tray, container, false);
        //Inflate the layout for this fragment
        ConviewWithId();
        SetUpToolbar();
        mlist = new ArrayList<>();
        mlist = dataBaseCarts.daoCarts().getAll();
        mCartsAdapter = new CartsAdapter(mContext, mlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mCategoryRV.setLayoutManager(layoutManager);
        mCategoryRV.setAdapter(mCartsAdapter);
        mCartsAdapter.notifyDataSetChanged();
        AddItemCount();
        ShopingContinue();
       // Toast.makeText(mContext, "total" + TotalPriceCount(), Toast.LENGTH_SHORT).show();
        tv_totalprice.setText(String.valueOf(TotalPriceCount()) + "PKR");
        Toast.makeText(mContext, "T:" + TotalPriceCount(), Toast.LENGTH_SHORT).show();
        checkout = view.findViewById(R.id.bt_check_out);
        checkout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you Ready For CheckOut")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            Toast.makeText(mContext, "c", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mContext, InoviceCart.class);
                                   // intent.putExtra("totalprice",tv_totalprice.getText().toString());
                                    mContext.startActivity(intent);

                        })
                        .setNegativeButton("No", (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }// count the how many item in carts

    private void ConviewWithId() {
        mCategoryRV = view.findViewById(R.id.cartsitem);
        mtextviewCountitem = view.findViewById(R.id.added_item_count);
        constraintLayout = view.findViewById(R.id.cartbg);
        cartsdetailslayout = view.findViewById(R.id.carts_details_item);
        carts_toolbar= view.findViewById(R.id.cart_toolbarid);
        tv_totalprice = view.findViewById(R.id.totalprice);
        continueShoping = view.findViewById(R.id.continueShoping_id);
    }
    // count the how many item in carts
    private void AddItemCount() {
        mCartsAdapter.Countitem(mtextviewCountitem);
        mCartsAdapter.ViabilityCarts(cartsdetailslayout,constraintLayout);
    }
    private void SetUpToolbar(){
        ((AppCompatActivity)getActivity()).setSupportActionBar(carts_toolbar);
    }

 //   / attach the menu items
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbarcartsmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        /// return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_carts:
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setTitle("Confirmation");
                builder.setMessage("Clear All oder");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show();
                         DeleteAllCartsItem();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // create dialog
                android.app.AlertDialog dialog  = builder.create();
                dialog.show();
                Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.BLUE);
                Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void DeleteAllCartsItem() {
        if (mlist.size() > 0) {
            dataBaseCarts.daoCarts().DeleteAllCarts(mlist);
            mlist.clear();;
            mCartsAdapter.notifyDataSetChanged();
            mCategoryRV.setAdapter(mCartsAdapter);
            mCartsAdapter.Countitem(mtextviewCountitem);
            mCartsAdapter.ViabilityCarts(cartsdetailslayout,constraintLayout);
            Toast.makeText(mContext, "you are delete all items", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mContext, "Not have item for delete", Toast.LENGTH_SHORT).show();
        }
    }
    private void ShopingContinue(){
        continueShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomeActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
    // this method calculate the  total price
    public static int TotalPriceCount() {
        int totalprice = 0;
        mlist = dataBaseCarts.daoCarts().getAll();
        for (CartsTableModel dataBaseCarts : mlist) {
            totalprice += dataBaseCarts.getMprice();
        }
        return totalprice;
    }
}