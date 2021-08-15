package com.example.wineaprilianisa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

public class ShowDinnerActivity extends Activity {

    TextView tv;
    String dinner;
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_dinner_suggestion);


    }

    protected void onStart() {
        super.onStart();
        TextView heading_tv = (TextView) findViewById(R.id.textView_info_heading);
        heading_tv.setText(getResources().getText(R.string.dinner_heading));
        tv = (TextView) findViewById(R.id.textView_info);
        Intent myIntent = getIntent();
        dinner = myIntent.getStringExtra(selectedDinnerExtrasKey);
        tv.setText(dinner);
    }

    public void orderOnline(View view) {
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, dinner);
        startActivity(intent);

    }

    public void removeMeal(View view) {
        Intent intent = new Intent(this, RemoveMealActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, dinner);
        startActivity(intent);

        TagManager tagManager = ((MyApplication) getApplication()).getTagManager();
        DataLayer dl = tagManager.getDataLayer();
        dl.pushEvent("openScreen",
                DataLayer.mapOf(
                        "screen-name", "Dislike Dinner",
                        "selected-dinner", dinner));
    }

    public void showRecipe(View view) {
        Intent intent = new Intent(this, ShowRecipeActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, dinner);
        startActivity(intent);
    }

    public void chooseAgain(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setDinnerSuggestion(item.getItemId());
                return true;
            }
        });

        popup.show();
    }

    public void setDinnerSuggestion(int item) {
        Dinner mdinner = new Dinner(this, item);
        dinner = mdinner.getDinnerTonight();
        tv.setText(dinner);
    }

    

}
