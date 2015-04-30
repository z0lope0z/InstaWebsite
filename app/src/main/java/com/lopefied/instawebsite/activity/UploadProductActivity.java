package com.lopefied.instawebsite.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lopefied.instawebsite.R;
import com.lopefied.instawebsite.module.ApplicationComponent;
import com.lopefied.instawebsite.module.BarcodeProductModule;
import com.lopefied.instawebsite.module.JobQueueModule;
import com.lopefied.instawebsite.module.SphereIOModule;
import com.lopefied.instawebsite.product.BarcodeProductService;
import com.lopefied.sphereandroidsdk.auth.SphereAuthConfig;
import com.lopefied.sphereandroidsdk.client.SphereApiConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import dagger.Component;

public class UploadProductActivity extends ActionBarActivity {
    public static final String TAG = UploadProductActivity.class.getSimpleName();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_BARCODE_CAPTURE = 2;

    @Inject
    BarcodeProductService barcodeProductService;

    @InjectView(R.id.edt_product_description)
    EditText edtProductDescription;

    String barcode;
    byte[] imageByteArray;

    @Singleton
    @Component(
            dependencies = {ApplicationComponent.class},
            modules = {
                    BarcodeProductModule.class,
            })
    public static interface BarcodeProductComponent {
        void inject(UploadProductActivity activity);
    }

    private BarcodeProductComponent component;

    @OnClick(R.id.btn_grab_barcode)
    public void onClickGrabBarcode(View view) {
        Intent barcodeIntent = new Intent(this, BarcodeScannerActivity.class);
        startActivityForResult(barcodeIntent, REQUEST_BARCODE_CAPTURE);
    }

    @OnClick(R.id.btn_grab_image)
    public void onClickGrabImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @OnClick(R.id.btn_upload)
    public void onClickUpload(View view) {
        try {
            upload(edtProductDescription.getText().toString(), barcode, imageByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        SphereAuthConfig sphereAuthConfig = new SphereAuthConfig.Builder()
                .authUrl("https://DtFNbGzILvE3vljBPPymIZPg:62ljT-1BbWaYy9_A5cKW4EtSsAZmbQnZ@auth.sphere.io")
                .projectKey("7-eleven")
                .clientId("DtFNbGzILvE3vljBPPymIZPg")
                .clientSecret("62ljT-1BbWaYy9_A5cKW4EtSsAZmbQnZ")
                .build();
        SphereApiConfig sphereApiConfig = new SphereApiConfig.Builder()
                .apiUrl("https://api.sphere.io")
                .authConfig(sphereAuthConfig)
                .build();
        component = DaggerUploadProductActivity_BarcodeProductComponent.builder()
                .sphereIOModule(new SphereIOModule(sphereApiConfig))
                .barcodeProductModule(new BarcodeProductModule(this))
                .jobQueueModule(new JobQueueModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageByteArray = stream.toByteArray();
        } else if (requestCode == REQUEST_BARCODE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            barcode = extras.getString(BarcodeScannerActivity.BARCODE);
        }
    }

    public void upload(String name, String barcode, byte[] imageByteArray) throws IOException {
        barcodeProductService.launchUploadJob(name, barcode, imageByteArray);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}