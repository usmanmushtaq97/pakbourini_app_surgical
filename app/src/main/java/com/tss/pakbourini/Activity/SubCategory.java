package com.tss.pakbourini.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.tss.pakbourini.controller.AutoSliderVIewAdapter;
import com.tss.pakbourini.controller.SubCategoryAdapter;
import com.tss.pakbourini.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategory extends AppCompatActivity {
    AutoSliderVIewAdapter sliderViewAdapter;
    List<CategoryModel> categoryModelList;
   SubCategoryAdapter mCategory_Adapter;
    RecyclerView    recyclerView_category;
    Toolbar toolbar;
    int category_id;
    String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        BindViewWithId();
        GetInt_Value();
        LoadCatecory();
        setupToolbar();
    }
    private void BindViewWithId() {
        recyclerView_category= findViewById(R.id.rv_sub_category);
        toolbar = findViewById(R.id.toolbar_subcategoryid);
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
    private void LoadCatecory() {
        categoryModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, "http://nomibrothers.e-sialkot.com/Admin/apis/get-category-by-sub_id.php?id="+category_id, response -> {
            Log.d("testtcode", response);
            try {
                JSONArray mMainCategory = new JSONArray(response);
                for (int i = 0; i < mMainCategory.length(); i++) {
                    JSONObject postobj = mMainCategory.getJSONObject(i);
                    int mCategory_id = postobj.getInt("id");
                    String mCategory_name = postobj.getString("category");
                    categoryModelList.add(new CategoryModel(mCategory_id, mCategory_name));
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView_category.setLayoutManager(layoutManager);
                mCategory_Adapter = new SubCategoryAdapter(this, categoryModelList);
                recyclerView_category.setAdapter(mCategory_Adapter);
                mCategory_Adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->
                Toast.makeText(this, "Category" + error.getMessage(), Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        //Volley.newRequestQueue(mContex).add(request);
    }
}