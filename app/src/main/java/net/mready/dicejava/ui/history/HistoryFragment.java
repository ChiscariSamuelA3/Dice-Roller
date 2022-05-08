package net.mready.dicejava.ui.history;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.mready.dicejava.MainActivity;
import net.mready.dicejava.R;
import net.mready.dicejava.model.History;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// TASK 3.1
public class HistoryFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView rvHistory;

    private List<History> historyList;
    private static final String HISTORY_LIST_KEY = "history_list_key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViewReferences(view);
        toolbar.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateBack());

        loadData(view);

        HistoryAdapter adapter = new HistoryAdapter();

        adapter.setHistoryList(historyList);
        rvHistory.setAdapter(adapter);
        // vertical list
        rvHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void getViewReferences(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        rvHistory = view.findViewById(R.id.rv_history);
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    private void loadData(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("history_storage", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        // type token of the objects
        Type type = new TypeToken<ArrayList<History>>() {
        }.getType();
        // get history list from shared preferences and convert to ArrayList - deserialize
        historyList = gson.fromJson(sharedPreferences.getString(HISTORY_LIST_KEY, null), type);
    }
}
