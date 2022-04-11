package com.example.lloydstest.model;

import com.google.gson.annotations.SerializedName;

public class CatFact {
    @SerializedName("text")
    private String fact;

    public String getFact() { return fact; }
}
