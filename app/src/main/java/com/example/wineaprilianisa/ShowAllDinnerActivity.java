package com.example.wineaprilianisa;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class ShowAllDinnerActivity extends ListActivity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_all_dinners);

        long startTime = System.nanoTime();

        Dinner dinner = new Dinner();
        String[] allDinners = dinner.getAllDinners(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.show_dinner_in_row, R.id.textview_dinner_row, allDinners);

        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        long stopTime = System.nanoTime();
        long elapsedTime = (stopTime - startTime) / 1000000;

        sendAnalyticsTimingHit(elapsedTime);
    }

    private void sendAnalyticsTimingHit(long t){
        Tracker tracker = ((MyApplication) getApplication()).getTracker();

        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory("List all dinners")
                .setValue(t)
                .setLabel("display duration")
                .setVariable("duration")
                .build());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String value = (String) getListView().getItemAtPosition(position);

        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, value);
        startActivity(intent);
    }
}
