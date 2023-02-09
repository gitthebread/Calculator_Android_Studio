package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;
    private static List<String> calculationHistory = new ArrayList<>();
    private static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = findViewById(R.id.historyListView);

        /*These 2 lines add the stuff from the array list into the ListView*/
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, calculationHistory);
        historyListView.setAdapter(adapter);
    }

    public void addCalculationToHistory(String calculation) {
        calculationHistory.add(calculation);
    }
}