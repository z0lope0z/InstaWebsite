package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.activity.UploadProductActivity;
import com.lopefied.instawebsite.module.scope.App;

import dagger.Component;

/**
 * Created by lope on 4/30/15.
 */
@Component(
        dependencies = JobQueueComponent.class,
        modules = {ProductModule.class, SphereIOModule.class}
)
public interface ProductComponent {
    void inject(UploadProductActivity activity);
}