package com.lopefied.instawebsite.module;

import android.content.Context;

import com.lopefied.instawebsite.module.scope.ActivityScope;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.lopefied.instawebsite.product.BarcodeProductServiceImpl;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.path.android.jobqueue.JobManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lope on 5/4/15.
 */
@Module
public class InstaWebsiteModule {

    Context context;

    public InstaWebsiteModule(Context context) {
        this.context = context;
    }

    @Provides
    @ActivityScope
    public BarcodeProductService provideBarcodeProductService(ProductTypeService productTypeService,
                                                              ProductService productService,
                                                              JobManager jobManager) {
        return new BarcodeProductServiceImpl(productTypeService, productService, jobManager,
                context);
    }

}