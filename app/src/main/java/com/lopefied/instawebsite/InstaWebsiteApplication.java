package com.lopefied.instawebsite;

import android.app.Application;
import android.content.Context;

import com.lopefied.instawebsite.module.AppComponent;
import com.lopefied.instawebsite.module.DaggerAppComponent;
import com.lopefied.sphereandroidsdk.auth.SphereAuthConfig;
import com.lopefied.sphereandroidsdk.client.SphereApiConfig;

/**
 * Created by lope on 4/30/15.
 */
public class InstaWebsiteApplication extends Application {

    private static InstaWebsiteApplication instance;

    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.Initializer.buildAndInject(this);
    }

    public static AppComponent component(Context context) {
        return app(context).component;
    }

    public static InstaWebsiteApplication app(Context context) {
        return (InstaWebsiteApplication) context.getApplicationContext();
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

    public static InstaWebsiteApplication getInstance() {
        return instance;
    }

}