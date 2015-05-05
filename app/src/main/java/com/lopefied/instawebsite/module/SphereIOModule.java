package com.lopefied.instawebsite.module;

import com.lopefied.sphereandroidsdk.client.SphereApiConfig;
import com.lopefied.sphereandroidsdk.client.SphereClient;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.product.ProductServiceImpl;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeServiceImpl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.client.OkClient;

/**
 * Created by lope on 4/30/15.
 */
@Module
public class SphereIOModule {
    SphereApiConfig sphereApiConfig;

    public SphereIOModule(SphereApiConfig sphereApiConfig) {
        this.sphereApiConfig = sphereApiConfig;
    }

    @Provides
    @Singleton
    public SphereClient provideSphereClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                return chain.proceed(request);
            }
        });
        return new SphereClient(sphereApiConfig, new OkClient(okHttpClient));
    }

    @Provides
    @Singleton
    public ProductTypeService provideProductTypeService(SphereClient sphereClient) {
        return new ProductTypeServiceImpl(sphereClient);
    }

    @Provides
    @Singleton
    public ProductService provideProductService(SphereClient sphereClient) {
        return new ProductServiceImpl(sphereClient);
    }
}
