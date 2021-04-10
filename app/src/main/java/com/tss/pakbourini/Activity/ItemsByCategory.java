package com.tss.pakbourini.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tss.pakbourini.R;
import com.tss.pakbourini.controller.ProductsAdapter;
import com.tss.pakbourini.model.FoodItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class ItemsByCategory extends AppCompatActivity {
    int category_id;
    String categoryName;
    RecyclerView recyclerView_product;
    Toolbar toolbar;
    ProductsAdapter productsAdapter;
    List<FoodItem> allProductsModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_by_category);
        GetInt_Value();
        BindViewWithId();
        setupToolbar();
        Load_Product();
    }
    private void BindViewWithId() {
        recyclerView_product = findViewById(R.id.recycler_product);
        toolbar = findViewById(R.id.toolbar_all_product);
    }
    private void GetInt_Value() {
        Intent intent = getIntent();
        category_id = intent.getIntExtra("id", 0);
        categoryName = intent.getStringExtra("name");
        Toast.makeText(this, "id"+category_id, Toast.LENGTH_SHORT).show();
    }
    public void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            (getSupportActionBar()).setTitle(categoryName);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void Load_Product() {
        allProductsModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, "http://nomibrothers.e-sialkot.com/Admin/apis/get-products-by-id.php?id="+category_id, response -> {
            Log.d("response product", response);
            try {
                JSONArray productobj = new JSONArray(response);
                for (int i = 0; i < productobj.length(); i++) {
                    JSONObject postobj = productobj.getJSONObject(i);
                    int product_id = postobj.getInt("id");
                    String product_name = postobj.getString("title");
                    String product_discription = postobj.getString("discription");
                    String product_price = postobj.getString("price");
                    String stock = postobj.getString("stock");
                    String mImageUrl = postobj.getString("image_url");
                  //  String policy = postobj.getString("policy");
                    allProductsModelList.add(new FoodItem(product_id, product_name, "3", product_price, product_discription, stock, mImageUrl, 4));
                }
                GridLayoutManager layoutManagerRecent_preoduct = new GridLayoutManager(ItemsByCategory.this, 2);
                recyclerView_product.setLayoutManager(layoutManagerRecent_preoduct);
                productsAdapter = new ProductsAdapter(ItemsByCategory.this, allProductsModelList);
                recyclerView_product.setAdapter(productsAdapter);
                productsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ItemsByCategory.this, "internet slow down ", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(ItemsByCategory.this);
        requestQueue.add(request);
    }
}