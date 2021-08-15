package com.example.wineaprilianisa;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

public class MyApplication extends Application {
    public Tracker tracker;
    public ContainerHolder containerHolder;
    public TagManager tagManager;

    public TagManager getTagManager() {
        if(tagManager == null) {
            tagManager = TagManager.getInstance(this);
        }
        return tagManager;
    }

    public Tracker getTracker() {
        startTracking();
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public void setContainerHolder(ContainerHolder containerHolder) {
        this.containerHolder = containerHolder;
    }

    public void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }

    public void startTracking() {
        if(tracker == null) {
            GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
            tracker = ga.newTracker(R.xml.track_app);
            ga.enableAutoActivityReports(this);
            ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

}
