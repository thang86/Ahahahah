package io.github.thang86.searchview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityRespone {
    @SerializedName("results")
    @Expose
    private List<City> results = null;

    public List<City> getResults() {
        return results;
    }

    public void setResults(List<City> results) {
        this.results = results;
    }
}
