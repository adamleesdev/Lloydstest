package com.example.lloydstest.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lloydstest.R;
import com.example.lloydstest.api.CatFactApi;
import com.example.lloydstest.api.CatImageApi;
import com.example.lloydstest.viewmodel.CatFactViewModel;

public class MainActivity extends BaseActivity {

    private TextView catFactTextView;
    private Button refresherButton;
    private ImageView catImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initObservers();
        initButtonListener();
        showLoading(CatImageApi.loading);
        //Trigger get cat data
        catFactViewModel.getData();
    }

    private void initViews(){
        catFactTextView = findViewById(R.id.catFactTextView);
        refresherButton = findViewById(R.id.refreshButton);
        catImageView = findViewById(R.id.catImageView);
    }

    private void initButtonListener(){
        refresherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(CatImageApi.loading);
                //Load next fact
                if (catFactViewModel.reachedFactCount()){
                    showLoading(CatFactApi.loading);
                }
                catFactViewModel.updateFacts();
            }
        });
    }

    private void initObservers(){
        Observer<String> catFactObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String catFact) {
                catFactTextView.setText(catFact);
            }
        };
        Observer<String> catFactErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String errorData) {
                showMessage(errorData);
            }
        };
        Observer<String> catImageUrlObserver = new Observer<String>() {
            @Override
            public void onChanged(String catImageUrl) {
                Glide.with(getApplicationContext()).load(catImageUrl).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        hideLoading();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hideLoading();
                        return false;
                    }
                }).into(catImageView);
            }
        };
        catFactViewModel.getCatImageUrl().observe(this, catImageUrlObserver);
        catFactViewModel.getCurrentFact().observe(this, catFactObserver);
        catFactViewModel.getErrorData().observe(this, catFactErrorObserver);
    }
}