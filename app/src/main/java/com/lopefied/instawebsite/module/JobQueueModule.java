package com.lopefied.instawebsite.module;

import android.content.Context;
import android.util.Log;

import com.lopefied.instawebsite.InstaWebsiteApplication;
import com.lopefied.instawebsite.module.SphereIOModule;
import com.lopefied.instawebsite.module.scope.App;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.lopefied.instawebsite.product.BarcodeProductServiceImpl;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.path.android.jobqueue.BaseJob;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.path.android.jobqueue.log.CustomLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class JobQueueModule {
    Context mContext;
    Configuration mConfiguration;

    public JobQueueModule(final InstaWebsiteApplication application) {
        mContext = application;
        mConfiguration = new Configuration.Builder(application)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(BaseJob job) {
                        JobComponent component = DaggerJobComponent.builder()
                                .productComponent(application.getProductComponent())
                                .build();
                        component.inject(this);
                    }
                })
                .build();
    }

    @Provides
    public JobManager provideJobManager() {
        return new JobManager(mContext, mConfiguration);
    }

}
