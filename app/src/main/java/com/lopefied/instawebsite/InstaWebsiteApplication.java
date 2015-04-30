package com.lopefied.instawebsite;

import android.app.Application;

import com.lopefied.instawebsite.module.ApplicationComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 4/30/15.
 */
public class InstaWebsiteApplication extends Application {

    private static InstaWebsiteApplication instance;
    List<Object> objectList = new ArrayList<Object>();

    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static InstaWebsiteApplication getInstance() {
        return instance;
    }

    public static void injectMembers(Object object) {
    }

}