package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.activity.UploadProductActivity;
import com.lopefied.instawebsite.module.scope.ActivityScope;

import dagger.Component;

/**
 * Created by lope on 5/4/15.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = InstaWebsiteModule.class)
public interface ActivityComponent extends AppComponent {
    void inject(UploadProductActivity activity);
}
