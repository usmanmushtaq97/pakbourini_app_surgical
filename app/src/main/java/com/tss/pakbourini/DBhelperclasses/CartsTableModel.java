package com.tss.pakbourini.DBhelperclasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "CartsItem")
public class CartsTableModel implements Serializable {
    //primary key auto increament
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_Id")
    private int mcartsid;
    // product id which get from server
    @ColumnInfo(name = "product_id")
    private int _product_id;
    @ColumnInfo(name = "CartProductTitle")
    private String mproductname;
    @ColumnInfo(name = "imagepath")
    private String pimagepath;
    @ColumnInfo(name ="quantity")
    private int mqunatity_product;
    @ColumnInfo(name = "price")
    private int mprice;
    @ColumnInfo(name = "P_Size")
    private String mp_size;
    @ColumnInfo(name = "delivery_status")
    private String delivery_status;
//    public CartsTableModel(int _product_id) {
//        this._product_id = _product_id;
//    }
    public CartsTableModel(int _product_id, String mproductname, String pimagepath, int mqunatity_product, int mprice, String mp_size, String delivery_status) {
        this._product_id = _product_id;
        this.mproductname = mproductname;
        this.pimagepath = pimagepath;
        this.mqunatity_product = mqunatity_product;
        this.mprice = mprice;
        this.mp_size = mp_size;
        this.delivery_status = delivery_status;
    }

    public int getMcartsid() {
        return mcartsid;
    }

    public void setMcartsid(int mcartsid) {
        this.mcartsid = mcartsid;
    }

    public int get_product_id() {
        return _product_id;
    }

    public void set_product_id(int _product_id) {
        this._product_id = _product_id;
    }

    public String getMproductname() {
        return mproductname;
    }

    public void setMproductname(String mproductname) {
        this.mproductname = mproductname;
    }

    public String getPimagepath() {
        return pimagepath;
    }

    public void setPimagepath(String pimagepath) {
        this.pimagepath = pimagepath;
    }

    public int getMqunatity_product() {
        return mqunatity_product;
    }

    public void setMqunatity_product(int mqunatity_product) {
        this.mqunatity_product = mqunatity_product;
    }

    public int getMprice() {
        return mprice;
    }

    public void setMprice(int mprice) {
        this.mprice = mprice;
    }

    public String getMp_size() {
        return mp_size;
    }

    public void setMp_size(String mp_size) {
        this.mp_size = mp_size;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }
}
