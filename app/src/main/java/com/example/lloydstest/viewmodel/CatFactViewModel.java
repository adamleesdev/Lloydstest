package com.example.lloydstest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.lloydstest.api.CatFactApi;
import com.example.lloydstest.api.CatImageApi;
import com.example.lloydstest.model.CatFact;
import com.example.lloydstest.model.CatImage;


public class CatFactViewModel extends ViewModel {

    private MutableLiveData<String> currentFact = new MutableLiveData<>();
    private MutableLiveData<String> catImageUrl = new MutableLiveData<>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private CatFact[] facts = new CatFact[]{};
    private int currentFactCount = 0;

    public MutableLiveData<String> getCurrentFact() {
        return currentFact;
    }

    public MutableLiveData<String> getCatImageUrl() {
        return catImageUrl;
    }

    public MutableLiveData<String> getErrorData() {
        return errorData;
    }

    public void updateFacts(){
        //Update cat image
        getCatImageFromApi();
        //Increment fact count or fetch new
        if (reachedFactCount()) {
            currentFactCount = 0;
            getFactsFromApi();
        } else {
            currentFactCount++;
            currentFact.postValue(facts[currentFactCount].getFact());
        }
    }

    public void setError(String error){
        errorData.postValue(error);
    }

    public void setCatFactUrl(CatImage catImage) {
        catImageUrl.postValue(catImage.getUrl());
    }

    public void setCatFactResponse(CatFact[] catFacts){
        this.facts = catFacts;
        updateFacts();
    }

    public void getData(){
        getCatImageFromApi();
        getFactsFromApi();
    }

    public void getFactsFromApi(){
        //Trigger facts api call
        CatFactApi.getCatFacts(this);
    }

    public void getCatImageFromApi(){
        //Trigger image api call
        CatImageApi.getCatImage(this);
    }

    public boolean reachedFactCount(){
        //Check if we're out of facts and need to load more
        return currentFactCount == facts.length - 1;
    }
}
