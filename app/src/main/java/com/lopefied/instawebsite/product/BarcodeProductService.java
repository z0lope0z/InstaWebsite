package com.lopefied.instawebsite.product;

import com.lopefied.instawebsite.model.BarcodeProduct;

import java.io.IOException;
import java.util.List;

/**
 * Created by lope on 4/30/15.
 */
public interface BarcodeProductService {
    public void upload(BarcodeProduct barcodeProduct) throws IOException;

    public void launchUploadJob(String name, String barcode, byte[] imageBytes);

    public void changeStatus(BarcodeProduct barcodeProduct, int status);

    public BarcodeProduct get(int id);

    /**
     * Returns a list of products created by the user. Ordered by failed products,
     * pending products, uploading products, and uploaded products.
     *
     * @return list of barcode products
     */
    public List<BarcodeProduct> getBarcodeProducts();
}
