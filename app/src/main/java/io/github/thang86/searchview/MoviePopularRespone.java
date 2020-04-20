package io.github.thang86.searchview;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MoviePopularRespone implements Parcelable {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<MoviePopular> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<MoviePopular> getResults() {
        return results;
    }

    public void setResults(List<MoviePopular> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
    }

    protected MoviePopularRespone(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(MoviePopular.CREATOR);
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Creator<MoviePopularRespone> CREATOR = new Creator<MoviePopularRespone>() {
        @Override
        public MoviePopularRespone createFromParcel(Parcel source) {
            return new MoviePopularRespone(source);
        }

        @Override
        public MoviePopularRespone[] newArray(int size) {
            return new MoviePopularRespone[size];
        }
    };
}
