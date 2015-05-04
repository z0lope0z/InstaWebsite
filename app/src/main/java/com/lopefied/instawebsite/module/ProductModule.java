package com.lopefied.instawebsite.module;

import android.content.Context;

import com.lopefied.instawebsite.module.scope.App;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.lopefied.instawebsite.product.BarcodeProductServiceImpl;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lope on 4/30/15.
 */
@Module(includes = {SphereIOModule.class, JobQueueModule.class})
public class ProductModule {
    Context context;

    public ProductModule(Context context) {
        this.context = context;
    }

    @Provides
    public BarcodeProductService provideBarcodeProductService(ProductTypeService productTypeService,
                                                              ProductService productService,
                                                              JobManager jobManager) {
        return new BarcodeProductServiceImpl(productTypeService, productService, jobManager,
                context);
    }
}
