package com.lopefied.instawebsite;

import android.app.Application;

import com.lopefied.instawebsite.module.JobQueueComponent;
import com.lopefied.instawebsite.module.DaggerProductComponent;
import com.lopefied.instawebsite.module.JobQueueModule;
import com.lopefied.instawebsite.module.ProductComponent;
import com.lopefied.instawebsite.module.SphereIOModule;
import com.lopefied.sphereandroidsdk.auth.SphereAuthConfig;
import com.lopefied.sphereandroidsdk.client.SphereApiConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 4/30/15.
 */
public class InstaWebsiteApplication extends Application {

    private static InstaWebsiteApplication instance;
    List<Object> objectList = new ArrayList<Object>();

    JobQueueComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public JobQueueComponent getComponent() {
        return component;
    }

    public SphereApiConfig getSphereApiConfig() {
        SphereAuthConfig sphereAuthConfig = new SphereAuthConfig.Builder()
                .authUrl(getString(R.string.auth_url))
                .projectKey(getString(R.string.project_key))
                .clientId(getString(R.string.client_id))
                .clientSecret(getString(R.string.client_secret))
                .build();
        return new SphereApiConfig.Builder()
                .apiUrl(getString(R.string.sphere_api_url))
                .authConfig(sphereAuthConfig)
                .build();
    }

    public ProductComponent getProductComponent() {
        return DaggerProductComponent.builder()
                .sphereIOModule(new SphereIOModule(InstaWebsiteApplication.getInstance().getSphereApiConfig()))
                .jobQueueModule(new JobQueueModule(this))
                .build();
    }

    public static InstaWebsiteApplication getInstance() {
        return instance;
    }

    public static void injectMembers(Object object) {
    }

    public static class SphereApiConfigNotSetException extends Exception {

    }
}