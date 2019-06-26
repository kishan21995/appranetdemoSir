package com.e.testdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.testdemo.R;
import com.e.testdemo.adapter.PostAdapter;
import com.e.testdemo.models.PostResponse;
import com.e.testdemo.retrofit.ApiClient;
import com.e.testdemo.retrofit.ApiInterface;
import com.e.testdemo.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private Button getPostBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo);
        recyclerView = findViewById(R.id.recycler);
        getPostBtn = findViewById(R.id.getPostBtn);
        getPostBtn.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onClick(View v) {

        if (!AppUtils.isInternetConnected(this)) {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            return;
        }
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<PostResponse> call = apiService.getTest();
        AppUtils.showProgressDialog(this, "Please wait...");
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                AppUtils.dismissProgressDialog();
                PostResponse postResponse = response.body();
                if (postResponse != null) {
                    updateList(postResponse);
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                AppUtils.dismissProgressDialog();

                Toast.makeText(PostDemoActivity.this, "Unable to fetch post, please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * Method to update recyclerView with data
     *
     * @param postResponse
     */
    private void updateList(PostResponse postResponse) {
        postAdapter = new PostAdapter(postResponse, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(PostDemoActivity.this));
        recyclerView.setAdapter(postAdapter);
// handle visibility of button and recyclerView
        getPostBtn.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }

}

