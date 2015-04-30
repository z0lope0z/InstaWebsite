package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.InstaWebsiteApplication;

import dagger.Component;

/**
 * Created by lope on 4/30/15.
 */
@Component(
        modules = {JobQueueModule.class}
)
public interface ApplicationComponent {
    void inject(InstaWebsiteApplication application);
}