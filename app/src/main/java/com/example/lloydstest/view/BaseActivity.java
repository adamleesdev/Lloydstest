package com.example.lloydstest.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.lloydstest.viewmodel.CatFactViewModel;


public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public CatFactViewModel catFactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        initViewModels();
    }

    public void initViewModels() {
        catFactViewModel = new ViewModelProvider(this).get(CatFactViewModel.class);
    }

    public void showLoading(String message) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.dismiss();
    }

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
