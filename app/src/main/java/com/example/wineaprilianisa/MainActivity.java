package com.example.wineaprilianisa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    TagManager tagManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).startTracking();
        loadGTMContainer();
    }

    public void loadGTMContainer() {
        tagManager = ((MyApplication) getApplication()).getTagManager();
        tagManager.setVerboseLoggingEnabled(true);
        PendingResult pending = tagManager.loadContainerPreferFresh("GTM-123456",
                R.raw.gtm_default);

        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(@NonNull ContainerHolder containerHolder) {
                if (!containerHolder.getStatus().isSuccess()) {
                    return;
                }

                containerHolder.refresh();
                ((MyApplication) getApplication()).setContainerHolder(containerHolder);
            }
        }, 2, TimeUnit.SECONDS);
    }

    public void showDinnerList(View view) {
        startActivity(new Intent(this, ShowAllDinnerActivity.class));
    }

    public void showFoodPrefsMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getDinnerSuggestion(item.getItemId());
                return true;
            }
        });

        popup.show();
    }

    public String getDinnerSuggestion(int item) {
        Dinner dinner = new Dinner(this, item);
        String dinnerChoice = dinner.getDinnerTonight();
        Intent intent = new Intent(this, ShowDinnerActivity.class);
        intent.putExtra(String.valueOf(R.string.selected_dinner), dinnerChoice);
        startActivity(intent);
        return dinnerChoice;
    }

    public void showDailySpecial(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.food_prefs_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                putFoodPrefInDataLayer(item);
                startShowDailySpecialActivity();
                return true;
            }
        });

        popup.show();
    }

    public void startShowDailySpecialActivity() {
        startActivity(new Intent(this, ShowDailySpecialActivity.class));
        DataLayer dl = tagManager.getDataLayer();

        dl.pushEvent("openScreen",
                DataLayer.mapOf("screen-name", "Show Daily Special"));
    }

    private void putFoodPrefInDataLayer(MenuItem item) {
        TagManager myGTM = ((MyApplication) getApplication()).getTagManager();

        DataLayer dl = myGTM.getDataLayer();
        String selectedFoodPref = "unrestricted";
        switch (item.getItemId()) {
            case R.id.vegan_pref:
                selectedFoodPref = "vegan";
                break;
            case R.id.vegetarian_pref:
                selectedFoodPref = "vegetarian";
                break;
            case R.id.fish_pref:
                selectedFoodPref = "fish";
                break;
            case R.id.meat_pref:
                selectedFoodPref = "meat";
                break;
            case R.id.unrestricted_pref:
                selectedFoodPref = "unrestricted";
                break;
            default:
                break;
        }

        dl.push("food_pref", selectedFoodPref);
    }
}