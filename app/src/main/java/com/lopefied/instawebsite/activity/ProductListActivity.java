package com.lopefied.instawebsite.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;

import com.lopefied.instawebsite.R;
import com.lopefied.instawebsite.adapter.ProductListAdapter;
import com.lopefied.instawebsite.product.BarcodeProductService;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProductListActivity extends ActionBarActivity {
    public static final String TAG = ProductListActivity.class.getSimpleName();

    @InjectView(R.id.rec_product_list)
    RecyclerView recProductList;

    ProductListAdapter productListAdapter;
    BarcodeProductService barcodeProductService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.inject(this);
        productListAdapter = new ProductListAdapter(barcodeProductService.getBarcodeProducts());
    }

}