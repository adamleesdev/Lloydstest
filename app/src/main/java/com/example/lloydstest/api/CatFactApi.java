package com.example.lloydstest.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lloydstest.model.CatFact;
import com.example.lloydstest.viewmodel.CatFactViewModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CatFactApi {

    private static final String catFactURL = "https://cat-fact.herokuapp.com/facts";
    public static final String error = "Error fetching facts.";
    public static final String loading = "Fetching facts..";

    public static void getCatFacts(CatFactViewModel catFactViewModel) {
        //Call network api with get request
        NetworkApi.getInstance().getRequest(catFactURL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("network error", e.getMessage());
                catFactViewModel.setError(error);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                catFactViewModel.setCatFactResponse(NetworkApi.getInstance().getGson().fromJson(response.body().string(), CatFact[].class));
            }
        });
    }
}
