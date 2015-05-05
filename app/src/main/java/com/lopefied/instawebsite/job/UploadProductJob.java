package com.lopefied.instawebsite.job;

import com.lopefied.instawebsite.model.BarcodeProduct;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.lopefied.sphereandroidsdk.product.Product;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by lope on 4/30/15.
 */
public class UploadProductJob extends Job {
    public static final int PRIORITY = 1;

    @Inject
    BarcodeProductService barcodeProductService;

    int barcodeProductId;

    public UploadProductJob(BarcodeProduct barcodeProduct) {
        super(new Params(PRIORITY).requireNetwork().persist());
        this.barcodeProductId = barcodeProduct.getId();
    }

    @Override
    public void onAdded() {
        BarcodeProduct barcodeProduct = barcodeProductService.get(barcodeProductId);
        barcodeProductService.changeStatus(barcodeProduct, BarcodeProduct.STATUS_UPLOADING);
    }

    @Override
    public void onRun() throws Throwable {
        BarcodeProduct barcodeProduct = barcodeProductService.get(barcodeProductId);
        barcodeProductService.changeStatus(barcodeProduct, BarcodeProduct.STATUS_UPLOADING);
        barcodeProductService
                .upload(barcodeProductService.get(barcodeProductId))
                .subscribe(new Observer<Product>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("");
                    }

                    @Override
                    public void onNext(Product product) {
                        System.out.println("");
                    }
                });
    }

    @Override
    protected void onCancel() {
        BarcodeProduct barcodeProduct = barcodeProductService.get(barcodeProductId);
        barcodeProductService.changeStatus(barcodeProduct, BarcodeProduct.STATUS_FAILURE);
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        BarcodeProduct barcodeProduct = barcodeProductService.get(barcodeProductId);
        barcodeProductService.changeStatus(barcodeProduct, BarcodeProduct.STATUS_FAILURE);
        return false;
    }
}
