package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.retrofit.ApiService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private List<MainModel.Result> results = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.rvMovie);
        setupRecyclerView();
        getDataFromApi();
        return rootView;
    }

    private void setupRecyclerView() {
        mainAdapter = new MainAdapter(results, new MainAdapter.onAdapterListener() {
            @Override
            public void onClick(MainModel.Result result) {
                Intent intent = new Intent(requireContext(), DetailActivity.class);
                intent.putExtra("intent_title", result.getTitle());
                intent.putExtra("intent_image", result.getImage());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter);
    }

    private void getDataFromApi () {
        ApiService.endpoint().getData()
                .enqueue(new Callback<MainModel>() {
                    @Override
                    public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                        if (response.isSuccessful()) {
                            List<MainModel.Result> results = response.body().getResult();
                            Log.d(TAG, results.toString());
                            mainAdapter.setData(results);
                            mainAdapter.setOriginalResults(new ArrayList<>(results)); // Set data asli ke adapter
                        }
                    }

                    @Override
                    public void onFailure(Call<MainModel> call, Throwable throwable) {
                        Log.d(TAG, throwable.toString());
                        // Handle failure
                        // Snackbar.make(requireView(), "Gagal mendapatkan data", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    //make searchview work
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}
