package com.lopefied.instawebsite.job;

import com.lopefied.instawebsite.model.BarcodeProduct;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

/**
 * Created by lope on 4/30/15.
 */
public class UploadProductJob extends Job {
    public static final int PRIORITY = 1;
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
        barcodeProductService.upload(barcodeProductService.get(barcodeProductId));
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
