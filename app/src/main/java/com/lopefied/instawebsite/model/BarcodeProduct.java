package com.lopefied.instawebsite.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lope on 4/30/15.
 */
public class BarcodeProduct extends RealmObject {
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_UPLOADING = 1;
    public static final int STATUS_UPLOADED = 2;
    public static final int STATUS_FAILURE = 3;

    @PrimaryKey
    private int id;
    private String name;
    private String barcode;
    private byte[] image;
    private int status;
    private Date dateCreated;

    public BarcodeProduct() {
    }

    public BarcodeProduct(int id, String name, String barcode, byte[] image, int status, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.image = image;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Builder {
        private int id;
        private String name;
        private String barcode;
        private byte[] image;
        private int status;
        private Date dateCreated = new Date();

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder barcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder dateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public BarcodeProduct build() {
            return new BarcodeProduct(id, name, barcode, image, status, dateCreated);
        }
    }
}
