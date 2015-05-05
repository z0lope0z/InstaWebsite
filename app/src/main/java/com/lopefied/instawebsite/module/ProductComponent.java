package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.activity.ProductListActivity;
import com.lopefied.instawebsite.activity.UploadProductActivity;
import com.lopefied.instawebsite.job.UploadProductJob;
import com.lopefied.instawebsite.module.scope.ActivityScope;

import dagger.Component;

/**
 * Created by lope on 4/30/15.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = InstaWebsiteModule.class)
public interface ProductComponent {
    void inject(UploadProductActivity activity);

    void inject(UploadProductJob job);

    void inject(ProductListActivity activity);
}