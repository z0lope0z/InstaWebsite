package com.lopefied.instawebsite.product;

import android.content.Context;

import com.github.slugify.Slugify;
import com.lopefied.instawebsite.job.UploadProductJob;
import com.lopefied.instawebsite.model.BarcodeProduct;
import com.lopefied.sphereandroidsdk.commons.LocalizedName;
import com.lopefied.sphereandroidsdk.commons.LocalizedSlug;
import com.lopefied.sphereandroidsdk.product.Product;
import com.lopefied.sphereandroidsdk.product.ProductDraft;
import com.lopefied.sphereandroidsdk.product.ProductService;
import com.lopefied.sphereandroidsdk.producttype.ProductType;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeDraft;
import com.lopefied.sphereandroidsdk.producttype.ProductTypeService;
import com.path.android.jobqueue.JobManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmQuery;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lope on 4/30/15.
 */
public class BarcodeProductServiceImpl implements BarcodeProductService {

    ProductTypeService productTypeService;
    ProductService productService;
    JobManager jobManager;
    Context context;

    public BarcodeProductServiceImpl(ProductTypeService productTypeService,
                                     ProductService productService,
                                     JobManager jobManager,
                                     Context context) {
        this.productTypeService = productTypeService;
        this.productService = productService;
        this.jobManager = jobManager;
        this.context = context;
    }

    @Override
    public Observable<Product> upload(BarcodeProduct barcodeProduct) throws IOException {
        final String name = barcodeProduct.getName();
        final byte[] imageBytes = barcodeProduct.getImage();
        // TODO get existing version instead of draft
        ProductTypeDraft productTypeDraft = new ProductTypeDraft.Builder()
                .name("BarcodeProduct")
                .description("BarcodeProduct").build();
        return productTypeService
                .createProductTypeObs(productTypeDraft)
                .flatMap(new Func1<ProductType, Observable<Product>>() {
                    @Override
                    public Observable<Product> call(ProductType productType) {
                        try {
                            return productService.createProductObs(new ProductDraft.Builder()
                                    .name(new LocalizedName(Locale.ENGLISH.getLanguage(), name))
                                    .slug(new LocalizedSlug(Locale.ENGLISH.getLanguage(), new Slugify().slugify(name)))
                                    .productType(productType)
                                    .build());
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                })
                .flatMap(new Func1<Product, Observable<Product>>() {
                    @Override
                    public Observable<Product> call(Product product) {
                        return productService.uploadImage(product, imageBytes);
                    }
                });
    }

    @Override
    public void launchUploadJob(String name, String barcode, byte[] imageBytes) {
        BarcodeProduct barcodeProduct = new BarcodeProduct.Builder()
                .barcode(barcode)
                .name(name)
                .image(imageBytes)
                .build();
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        // WTF realm!!!!!
        // https://github.com/realm/realm-java/issues/469
        int nextID = (int) (realm.where(BarcodeProduct.class).maximumInt("id") + 1);
        barcodeProduct.setId(nextID);
        realm.copyToRealmOrUpdate(barcodeProduct);
        realm.commitTransaction();
        jobManager.addJob(new UploadProductJob(barcodeProduct));
    }

    @Override
    public void changeStatus(BarcodeProduct barcodeProduct, int status) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        barcodeProduct.setStatus(status);
        realm.copyToRealmOrUpdate(barcodeProduct);
        realm.commitTransaction();
    }

    @Override
    public BarcodeProduct get(int id) {
        Realm realm = Realm.getInstance(context);
        return realm.where(BarcodeProduct.class)
                .equalTo("id", id)
                .findFirst();
    }

    @Override
    public List<BarcodeProduct> getBarcodeProducts() {
        Realm realm = Realm.getInstance(context);
        RealmQuery<BarcodeProduct> query = realm.where(BarcodeProduct.class);
//                .beginGroup()
//                .equalTo("status", BarcodeProduct.STATUS_FAILURE)
//                .endGroup()
//                .beginGroup()
//                .equalTo("status", BarcodeProduct.STATUS_PENDING)
//                .endGroup()
//                .beginGroup()
//                .equalTo("status", BarcodeProduct.STATUS_UPLOADING)
//                .endGroup()
//                .beginGroup()
//                .equalTo("status", BarcodeProduct.STATUS_UPLOADED)
//                .endGroup();
        return query.findAll();
    }
}
