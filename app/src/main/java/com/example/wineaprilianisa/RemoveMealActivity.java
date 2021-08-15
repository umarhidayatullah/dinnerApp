package com.example.wineaprilianisa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class RemoveMealActivity extends Activity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info);
    }

    protected void onStart() {
        super.onStart();

        TextView heading = findViewById(R.id.textView_info_heading);
        heading.setText(getResources().getText(R.string.dislike_dinner_heading));

        TextView tv = findViewById(R.id.textView_info);
        String dinner = getIntent().getStringExtra(selectedDinnerExtrasKey);
        tv.setText(dinner + " \n\n" + getResources().getText(R.string.dislike_dinner));
    }
}
