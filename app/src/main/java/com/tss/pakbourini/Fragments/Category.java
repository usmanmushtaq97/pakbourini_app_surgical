package com.tss.pakbourini.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tss.pakbourini.R;
import com.tss.pakbourini.controller.CategoryRecAdapter;
import com.tss.pakbourini.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.tss.pakbourini.Constant.CategoryUrl;
import static com.tss.pakbourini.Constant.MAINBASEUrl;

public class Category extends Fragment {
    View view;
    Context mContext;
    List< CategoryModel > categoryModelList;
    CategoryRecAdapter mCategory_Adapter;
    RecyclerView recyclerView_category;

    public Category() {
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
        view = inflater.inflate(R.layout.fragment_table_chart, container, false);
        BindView();
        LoadCatecory();
        return view;
    }

    //bind
    private void BindView() {
        recyclerView_category = view.findViewById(R.id.rv_cat);
    }

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
                GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
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
        //Volley.newRequestQueue(mContext).add(request);
    }
}