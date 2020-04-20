package io.github.thang86.searchview.text;

import java.util.List;

import io.github.thang86.searchview.CityRespone;
import io.github.thang86.searchview.MoviePopularRespone;
import io.github.thang86.searchview.MovieRespone;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("search/movie")
    Call<MovieRespone> getListtMovie(@Query("api_key") String key, @Query("language") String language, @Query("query") String query, @Query("page") int page, @Query("include_adult") boolean include_adult);

    @GET("movie/popular")
    Call<MoviePopularRespone> getPopularMovies(@Query("api_key") String apiKey);

    @GET("cities/autocomplete")
    Call<CityRespone> getCityRespone();

}
