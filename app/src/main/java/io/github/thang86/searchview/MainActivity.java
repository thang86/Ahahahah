package io.github.thang86.searchview;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.github.thang86.searchview.text.ApiClient;
import io.github.thang86.searchview.text.ApiUtils;
import io.github.thang86.searchview.text.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    Services services;

    RecyclerView recyclerView;
    BikeStationAdapter adapter;
    private SearchView mSearchView;
    private String mQuery;
    List<BikeStation> list = Collections.emptyList();
    SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "SEARCHVIEW";
    private List<Result> results = Collections.emptyList();

    private Call<MovieRespone> callBack;
    private Call<CityRespone> callBack2;

    private List<City> resultsCity = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BikeStationAdapter(this, results);
        recyclerView.setAdapter(adapter);
        //getMovie("Super");
        // getVoid();
    }

    void getVoid() {
        List<Result> listResu = new ArrayList<>();
        String KEY = "5a3e2f4478de0a043e7456cbb9c5f6c7";
        Call<MoviePopularRespone> call = ApiUtils.getMoviePopularRespone().getPopularMovies(KEY);
        call.enqueue(new Callback<MoviePopularRespone>() {
            @Override
            public void onResponse(Call<MoviePopularRespone> call, Response<MoviePopularRespone> response) {
                Log.d("BADJDJDJDJJD", "onResponse: ." + response.body().getResults().get(1));
//                listResu = response.body().getResults()
            }

            @Override
            public void onFailure(Call<MoviePopularRespone> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    public void getMovie(String query) {

        String KEY = "5a3e2f4478de0a043e7456cbb9c5f6c7";

        Call<MovieRespone> call = ApiUtils.getMoviePopularRespone().getListtMovie(KEY, "en-US", query, 1, false);
        call.enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                if (response.body().getResults() != null) {
                    for (Result rs : response.body().getResults()) {
                        results.add(rs);

                    }
                    adapter = new BikeStationAdapter(getApplicationContext(), results);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("BADJDJDJDJJD", "getMovie: " + results.size());
                }
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {
                if (call.isCanceled()) {
                    Log.d(TAG, "onFailure: is cancel");
                } else {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            }
        });
    }

    void searchResultCity(final String query) {
        String KEY = "eUoGEbOJgbqFu06fBSlocVYYO5dsAdkG";
        callBack2 = ApiUtils.getCityRespone().getCityRespone(KEY, query, "en-us");
        callBack2.enqueue(new Callback<CityRespone>() {
            @Override
            public void onResponse(Call<CityRespone> call, Response<CityRespone> response) {
                if (response.body().getCities() != null) {
                    for (City rs : response.body().getCities()) {
                        resultsCity.add(rs);

                    }
                    adapter = new BikeStationAdapter(getApplicationContext(), results);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("BADJDJDJDJJD", "getMovie: " + results.size());
                }
            }

            @Override
            public void onFailure(Call<CityRespone> call, Throwable t) {
                if (call.isCanceled()) {
                    Log.d("HHAHHAHAHA", "onFailure: is cancel");
                } else {
                    Log.d("HHAHHAHAHA", "onFailure: " + t.getMessage());
                }
            }
        });


    }

    void searchResult(final String query) {
        String KEY = "5a3e2f4478de0a043e7456cbb9c5f6c7";

        callBack = ApiUtils.getMoviePopularRespone().getListtMovie(KEY, "en-US", query, 1, false);
        callBack.enqueue(new Callback<MovieRespone>() {
            @Override
            public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                if (call != null) {
                    call.cancel();
                    call = null;
                }
                if (response.isSuccessful() && response.body().getResults() != null) {
                    Log.d("HHAHHAHAHA", "onResponse: " + response.body().getResults());
                    adapter.updateSearchResults(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieRespone> call, Throwable t) {
                if (call.isCanceled()) {
                    Log.d("HHAHHAHAHA", "onFailure: is cancel");
                } else {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            }
        });
    }


//    private void getData() {
//        services = ApiClient.createService(Services.class);
//        Call<List<BikeStation>> call = services.getBicycleStation();
//        call.enqueue(new Callback<List<BikeStation>>() {
//            @Override
//            public void onResponse(Call<List<BikeStation>> call, Response<List<BikeStation>> response) {
//                if(swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                assert response.body() != null;
//                for (BikeStation station:response.body()) {
//                    list.add(station);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Call<List<BikeStation>> call, Throwable t) {
//                if(swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (query.length() >= 2) {
//                    getMovie(query);
//                    adapter.getFilter().filter(query);
//                    adapter.notifyDataSetChanged();
//                    Log.d(TAG, "onQueryTextSubmit2: " + query);
//                } else if (query.isEmpty()) {
//                    results.clear();
//                    adapter.notifyDataSetChanged();
//                }
//
//                Log.d(TAG, "onQueryTextSubmit1: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
////                getData();
//                if (newText.length() >= 2) {
//                    getMovie(newText);
//                    adapter.getFilter().filter(newText);
//                    adapter.notifyDataSetChanged();
//                    Log.d(TAG, "onQueryTextSubmit2: " + newText);
//                } else if (newText.isEmpty()) {
//                    results.clear();
//                    adapter.notifyDataSetChanged();
//                }

                if ("".equals(newText.trim()) && newText.length() >= 2) {
                    results.clear();
                    adapter.updateSearchResults(results);
                    adapter.notifyDataSetChanged();
                } else {
                    searchResultCity(newText);
                }

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(City station, int position) {
        Toast.makeText(this, station.getLocalizedName() + " Clicked.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        list.clear();
    }
}
