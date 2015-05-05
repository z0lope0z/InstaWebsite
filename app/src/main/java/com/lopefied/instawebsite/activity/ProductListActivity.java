package com.lopefied.instawebsite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lopefied.instawebsite.InstaWebsiteApplication;
import com.lopefied.instawebsite.R;
import com.lopefied.instawebsite.adapter.ProductListAdapter;
import com.lopefied.instawebsite.module.DaggerProductComponent;
import com.lopefied.instawebsite.module.InstaWebsiteModule;
import com.lopefied.instawebsite.module.ProductComponent;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.melnykov.fab.FloatingActionButton;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProductListActivity extends ActionBarActivity {
    public static final String TAG = ProductListActivity.class.getSimpleName();

    @InjectView(R.id.rec_product_list)
    RecyclerView recProductList;

    ProductListAdapter productListAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    @Inject
    BarcodeProductService barcodeProductService;

    @InjectView(R.id.fab_new_product)
    FloatingActionButton fabNewProduct;

    @OnClick(R.id.fab_new_product)
    public void onClickNewProduct(View view) {
        Intent intent = new Intent(this, UploadProductActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.inject(this);
        ProductComponent component = DaggerProductComponent.builder()
                .appComponent(InstaWebsiteApplication.component(this))
                .instaWebsiteModule(new InstaWebsiteModule((InstaWebsiteApplication) getApplicationContext()))
                .build();
        component.inject(this);
        mLayoutManager = new LinearLayoutManager(this);
        recProductList.setLayoutManager(mLayoutManager);
        productListAdapter = new ProductListAdapter(barcodeProductService.getBarcodeProducts());
        recProductList.setAdapter(productListAdapter);
        fabNewProduct.attachToRecyclerView(recProductList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        productListAdapter.setItems(barcodeProductService.getBarcodeProducts());
    }
}