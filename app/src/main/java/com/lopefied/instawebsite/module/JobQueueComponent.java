package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.InstaWebsiteApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lope on 4/30/15.
 */

@Component(
        modules = {JobQueueModule.class}
)
public interface JobQueueComponent {
}