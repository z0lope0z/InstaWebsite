package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.InstaWebsiteApplication;
import com.lopefied.instawebsite.module.scope.App;
import com.path.android.jobqueue.di.DependencyInjector;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lope on 4/30/15.
 */
@Component(
        dependencies = ProductComponent.class
)
public interface JobComponent {
    void inject(DependencyInjector injector);
}