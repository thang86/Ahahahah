package io.github.thang86.searchview.text;

public class ApiUtils {
    public static Services getMoviePopularRespone() {
        return ApiClient.getApiClient("http://api.themoviedb.org/3/").create(Services.class);
    }

    public static Services getCityRespone() {
        return ApiClient.getApiClient("http://dataservice.accuweather.com/locations/v1/").create(Services.class);
    }
}
