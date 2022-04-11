package com.example.lloydstest.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lloydstest.model.CatImage;
import com.example.lloydstest.viewmodel.CatFactViewModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CatImageApi {

    private static final String catImagesUrl = "https://api.thecatapi.com/v1/images/search";
    public static final String error = "Error fetching image.";
    public static final String loading = "Fetching image..";

    public static void getCatImage(CatFactViewModel catFactViewModel) {
        //Call network api with get request
        NetworkApi.getInstance().getRequest(catImagesUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("network error", e.getMessage());
                catFactViewModel.setError(error);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                CatImage[] images = NetworkApi.getInstance().getGson().fromJson(response.body().string(), CatImage[].class);
                if (images.length > 0){
                    catFactViewModel.setCatFactUrl(images[0]);
                } else {
                    catFactViewModel.setError(error);
                }
            }
        });
    }
}
