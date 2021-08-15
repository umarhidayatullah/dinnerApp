package com.example.wineaprilianisa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;

import java.nio.channels.AcceptPendingException;

public class ShowDailySpecialActivity extends Activity {
    String selectedDinnerExtrasKey = String.valueOf(R.string.selected_dinner);
    public String myDaily = "Fried egg with kit kat rashers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_daily_special);

        TextView heading = findViewById(R.id.textView_info_heading);
        heading.setText("Today's special");
        updateDailySpecial();
    }

    private void updateDailySpecial() {
        ContainerHolder holder = ((MyApplication) getApplication()).getContainerHolder();

        myDaily = holder.getContainer().getString("daily-special");

        TextView tv_daily  = (TextView) findViewById(R.id.textView_info);
        tv_daily.setText(myDaily);
    }

    public void orderOnline(View view) {
        Intent intent = new Intent(this, OrderDinnerActivity.class);
        intent.putExtra(selectedDinnerExtrasKey, myDaily);
        startActivity(intent);
    }
}
