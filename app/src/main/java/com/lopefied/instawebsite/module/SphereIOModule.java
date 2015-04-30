package com.lopefied.instawebsite.module;

import com.lopefied.sphereandroidsdk.client.SphereApiConfig;
import com.lopefied.sphereandroidsdk.client.SphereClient;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.product.ProductServiceImpl;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeServiceImpl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
    public SphereClient provideSphereClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String request = chain.request().toString();
                String requestBody = chain.request().body().toString();
                Response response = chain.proceed(chain.request());
                String dirty = response.body().string();
                return response;
            }
        });
        return new SphereClient(sphereApiConfig, new OkClient(okHttpClient));
    }

    @Provides
    public ProductTypeService provideProductTypeService(SphereClient sphereClient) {
        return new ProductTypeServiceImpl(sphereClient);
    }

    @Provides
    public ProductService provideProductService(SphereClient sphereClient) {
        return new ProductServiceImpl(sphereClient);
    }
}
