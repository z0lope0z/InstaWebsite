package com.lopefied.instawebsite.module;

import com.lopefied.instawebsite.InstaWebsiteApplication;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lope on 5/4/15.
 */
@Singleton
@Component(modules = {JobQueueModule.class, SphereIOModule.class})
public interface AppComponent {
    JobManager jobManager();

    ProductService productService();

    ProductTypeService productTypeService();

    void inject(InstaWebsiteApplication app);

    final static class Initializer {
        public static AppComponent buildAndInject(InstaWebsiteApplication app) {
            AppComponent component = DaggerAppComponent.builder()
                    .sphereIOModule(new SphereIOModule(app.getSphereApiConfig()))
                    .jobQueueModule(new JobQueueModule(app))
                    .build();
            component.inject(app);
            return component;
        }

        private Initializer() {
        }
    }
}
