package com.iqcollections;

public class Wish {
    String wishName;
    String wishDesc;
    String imgWishUrl;

    public Wish(String wishName, String wishDesc, String imgWishUrl) {
        this.wishName = wishName;
        this.wishDesc = wishDesc;
        this.imgWishUrl = imgWishUrl;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public String getWishDesc() {
        return wishDesc;
    }

    public void setWishDesc(String wishDesc) {
        this.wishDesc = wishDesc;
    }

    public String getImgWishUrl() {
        return imgWishUrl;
    }

    public void setImgWishUrl(String imgWishUrl) {
        this.imgWishUrl = imgWishUrl;
    }
}
