package com.tss.pakbourini.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tss.pakbourini.R;
import com.tss.pakbourini.controller.AutoSliderVIewAdapter;
import com.tss.pakbourini.controller.CategoryRecAdapter;
import com.tss.pakbourini.controller.ProductsAdapter;
import com.tss.pakbourini.model.CategoryModel;
import com.tss.pakbourini.model.FoodItem;
import com.tss.pakbourini.model.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tss.pakbourini.Constant.CategoryUrl;
import static com.tss.pakbourini.Constant.GET_PRODUCT_ALL;
import static com.tss.pakbourini.Constant.MAINBASEUrl;
import static com.tss.pakbourini.Constant.SliderUrl;

public class Home extends Fragment {
    View view;
    List< SliderItem > sliderItemList;
    List<FoodItem> allProductsModelList;
    SliderView mSliderView;
    Context mContext;
    AutoSliderVIewAdapter sliderViewAdapter;
    List<CategoryModel> categoryModelList;
    CategoryRecAdapter mCategory_Adapter;
    ProductsAdapter productsAdapter;
    RecyclerView    recyclerView_category;
    RecyclerView recyclerView_product;
    RecyclerView special_offers_products;
    public Home() {
        // Required empty public constructor
    }
    // attach context
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view = inflater.inflate(R.layout.fragment_home, container, false);
          BIndViewByID();
          LoadSlider();
          LoadCatecory();
          Load_Product();
          Load_Product_Specials();
        return view;
    }
    /// this method connect layout item ///
    private void BIndViewByID() {
        mSliderView = view.findViewById(R.id.imageSlider);
        recyclerView_category = view.findViewById(R.id.category__RecyclderView);
        recyclerView_product = view.findViewById(R.id.mainRecyclerView);
        special_offers_products= view.findViewById(R.id.specials_offers);
    }
    // load the slider from backend penals
    private void LoadSlider() {
        mSliderView.startAutoCycle();
        mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mSliderView.setIndicatorSelectedColor(Color.GREEN);
        mSliderView.setIndicatorUnselectedColor(Color.GRAY);
        mSliderView.setScrollTimeInSec(4);
        sliderItemList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, SliderUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testtcode", response);
                try {
                    JSONArray mMainCategory = new JSONArray(response);
                    for (int i = 0; i < mMainCategory.length(); i++) {
                        JSONObject postobj = mMainCategory.getJSONObject(i);
                        int sliderid = postobj.getInt("id");
                        String mImageUrl = postobj.getString("image_url");

                        sliderItemList.add(new SliderItem(sliderid,mImageUrl));
                    }
                    sliderViewAdapter = new AutoSliderVIewAdapter(mContext, sliderItemList);
                    mSliderView.setSliderAdapter(sliderViewAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(mContext, "errorrr", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        Volley.newRequestQueue(mContext).add(request);
    }
    ///
    private void LoadCatecory() {
        categoryModelList = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, CategoryUrl, response -> {
            Log.d("testtcode", response);
            try {
                JSONArray mMainCategory = new JSONArray(response);
                for (int i = 0; i < mMainCategory.length(); i++) {
                    JSONObject postobj = mMainCategory.getJSONObject(i);
                    int mCategory_id = postobj.getInt("id");
                    String mCategory_name = postobj.getString("category");
                    String mImageUrl = MAINBASEUrl+postobj.getString("image_url");
                    categoryModelList.add(new CategoryModel(mCategory_id, mCategory_name, mImageUrl));
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView_category.setLayoutManager(layoutManager);
                mCategory_Adapter = new CategoryRecAdapter(mContext, categoryModelList);
                recyclerView_category.setAdapter(mCategory_Adapter);
                mCategory_Adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->
                Toast.makeText(mContext, "Category" + error.getMessage(), Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
        //Volley.newRequestQueue(mContex).add(request);
    }
    ///

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
                    String mImageUrl =postobj.getString("image_url");
                    String product_discription = postobj.getString("discription");
                    String stock = postobj.getString("stock");
                    String  policy = postobj.getString("policy");
                    allProductsModelList.add(new FoodItem(product_id, product_name,"3", product_price,product_discription,stock,mImageUrl,4));
                }
                LinearLayoutManager layoutManagerRecent_preoduct = new LinearLayoutManager(mContext);
                layoutManagerRecent_preoduct.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView_product.setLayoutManager(layoutManagerRecent_preoduct);
                productsAdapter = new ProductsAdapter(mContext, allProductsModelList);
                recyclerView_product.setAdapter(productsAdapter);
                productsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(mContext, "internet slow down ", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }
    private void Load_Product_Specials() {
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
                LinearLayoutManager layoutManagerRecent_preoduct = new LinearLayoutManager(mContext);
                layoutManagerRecent_preoduct.setOrientation(RecyclerView.HORIZONTAL);
                special_offers_products.setLayoutManager(layoutManagerRecent_preoduct);
                productsAdapter = new ProductsAdapter(mContext, allProductsModelList);
                special_offers_products.setAdapter(productsAdapter);
                productsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(mContext, "internet slow down ", Toast.LENGTH_SHORT).show());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );

        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }
}