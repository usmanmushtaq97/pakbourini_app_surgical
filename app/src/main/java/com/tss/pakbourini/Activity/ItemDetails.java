package com.tss.pakbourini.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.tss.pakbourini.DBhelperclasses.CartsTableModel;
import com.tss.pakbourini.DBhelperclasses.DataBaseCarts;
import com.tss.pakbourini.R;
import com.tss.pakbourini.controller.FoodItemQtyRecyclerAdapter;
import com.tss.pakbourini.controller.ProductsAdapter;
import com.tss.pakbourini.model.FoodItem;
import com.tss.pakbourini.model.FoodItemQty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

import static com.tss.pakbourini.Constant.GET_PRODUCT_ALL;

public class ItemDetails extends AppCompatActivity{
    private FoodItemQtyRecyclerAdapter mFoodItemQtyRecyclerAdapter;
    private ArrayList< FoodItemQty > mFoodItemQties;
    TextView tv_qty;
    private ArrayList< String > mSizeList;
    private RecyclerView mRecyclerViewQty;
    TextView title, descriptions;
    ImageView mProductsProfiles;
    TextView mPrices;
    TextView mStocks;
    String mtitle, mdescriptions;
    String price, profilesimage;
    String stocks;
    View dialogView;
    AlertDialog.Builder builder;
    String min;
    Button addtoTraybtn;
    //String delivery_status;
    int productId;
    float ratings;
    LinearLayout layout_trayadd;
    private DataBaseCarts dataBase;
    Button increament_Quantity_bt, decreament_Quantity_bt;
    private int total_price_single_product;
    int product_qunatity = 1;
    String product_size;
    AlertDialog alertDialog;
    List< FoodItem > allProductsModelList;
    ProductsAdapter productsAdapter;
    RecyclerView recyclerView_product;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bungee.slideDown(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_item_details);
        dataBase = DataBaseCarts.getInstance(ItemDetails.this);
        BindView();
        GetInt_Value();
        SetData();
        AddToTray();
        Load_Product();
    }

    private void BindView() {
        title = findViewById(R.id.list_title);
   recyclerView_product = findViewById(R.id.itemQtyRecyclerView);
        descriptions = findViewById(R.id.descreptions);
        mPrices = findViewById(R.id.price_id);
        mProductsProfiles = findViewById(R.id.profile_image);
        layout_trayadd = findViewById(R.id.idaddtoray);
        // mStocks = findViewById(R.id.)
    }

    private void GetInt_Value() {
        Intent intent = getIntent();
        productId = intent.getIntExtra("pid", 0);
        mtitle = intent.getStringExtra("name");
        mdescriptions = intent.getStringExtra("descriptions");
        price = intent.getStringExtra("price");
        total_price_single_product = Integer.parseInt(price);
        profilesimage = intent.getStringExtra("image_url");
        stocks = intent.getStringExtra("stock");
        min = intent.getStringExtra("min");
        ratings = intent.getFloatExtra("ratings", 0);
    }

    // set the value which get from perivious
    private void SetData() {
        if (mtitle != null) {
            title.setText(mtitle);
        }
        if (mdescriptions != null) {
            descriptions.setText(Html.fromHtml(mdescriptions));
        }
        if (price != null) {
            mPrices.setText("Rs" + price);
        }
        if (profilesimage != null) {
            Glide.with(this).load(profilesimage).into(mProductsProfiles);
        }

    }
    private void Load_Product() {
        allProductsModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, GET_PRODUCT_ALL, response -> {
            Log.d("response product", response);
            try {
                JSONArray productobj = new JSONArray(response);
                for (int i = 0; i < productobj.length(); i++) {
                    JSONObject postobj = productobj.getJSONObject(i);
                    int product_id = postobj.getInt("id");
                    String product_name = postobj.getString("title");
                    String product_price = postobj.getString("price");
                    String mImageUrl = postobj.getString("image_url");
                    String product_discription = postobj.getString("discription");
                    String stock = postobj.getString("stock");
                    allProductsModelList.add(new FoodItem(product_id, product_name,"3", product_price,product_discription,stock,mImageUrl,4));
                }
                LinearLayoutManager layoutManagerRecent_preoduct = new LinearLayoutManager(this);
                layoutManagerRecent_preoduct.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView_product.setLayoutManager(layoutManagerRecent_preoduct);
                productsAdapter = new ProductsAdapter(this, allProductsModelList);
                recyclerView_product.setAdapter(productsAdapter);
                productsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "internet slow down ", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    // this methods loads the size of products form Backend penal


    private void AddToTray() {
        layout_trayadd.setOnClickListener(v -> {
            builder = new AlertDialog.Builder(ItemDetails.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.oderdetailsget, viewGroup, false);
            builder.setView(dialogView);
            alertDialog = builder.create();
            alertDialog.show();
            addtoTraybtn = alertDialog.findViewById(R.id.confirmdbtn);
            increament_Quantity_bt = alertDialog.findViewById(R.id.increament_bt);
            decreament_Quantity_bt = alertDialog.findViewById(R.id.decrement_bt);
            tv_qty = alertDialog.findViewById(R.id.quantity);
            increament_Quantity_bt.setOnClickListener(v13 -> {
                if (product_qunatity > 0) {
                    product_qunatity++;
                    tv_qty.setText(String.valueOf(product_qunatity));
                    total_price_single_product = product_qunatity * Integer.parseInt(price);
                    Toast.makeText(ItemDetails.this, "increament_by+1:  " + total_price_single_product, Toast.LENGTH_SHORT).show();
                }
            });
            decreament_Quantity_bt.setOnClickListener(v12 -> {
                if (product_qunatity > 1) {
                    product_qunatity--;
                    tv_qty.setText(String.valueOf(product_qunatity));
                    total_price_single_product = product_qunatity * Integer.parseInt(price);
                    //product_Details_price_view.setText("Total Price:  " + total_price_single_product);
                }
            });
            addtoTraybtn.setOnClickListener(v1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to Add This Item In Tray")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AddDataInDB();
                                Toast.makeText(ItemDetails.this, "You Added this items" + mtitle, Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                alertDialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            });//addsubmit
        });//dialog
    }//method

    private void AddDataInDB() {
        String pqty = tv_qty.getText().toString();
        int qtyupdate = Integer.parseInt(pqty);
        dataBase.daoCarts().InsertCarts(new CartsTableModel(productId, mtitle, profilesimage, qtyupdate, total_price_single_product,"Default", price));
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
