package net.mready.dicejava.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import net.mready.dicejava.MainActivity;
import net.mready.dicejava.R;

// TASK 3.2
public class HistoryEmptyFragment extends Fragment {

    Toolbar toolbar;

    public static HistoryEmptyFragment newInstance() {
        return new HistoryEmptyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_empty, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViewReferences(view);
        toolbar.setOnClickListener((v) -> ((MainActivity) requireActivity()).navigateBack());
    }


    private void getViewReferences(View view) {
        toolbar = view.findViewById(R.id.toolbar_empty);
    }

}