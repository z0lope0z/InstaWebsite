package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.activity.ProductListActivity;
import com.lopefied.instawebsite.activity.UploadProductActivity;
import com.lopefied.instawebsite.module.scope.ActivityScope;
import com.path.android.jobqueue.di.DependencyInjector;

import dagger.Component;

/**
 * Created by lope on 4/30/15.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = InstaWebsiteModule.class)
public interface ProductComponent {
    void inject(UploadProductActivity activity);

    void inject(DependencyInjector injector);

    void inject(ProductListActivity activity);
}