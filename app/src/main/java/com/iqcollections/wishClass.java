package com.iqcollections;

// this class stores all the constructors as well as the get and set methods for the wishlist function
public class wishClass {
    private String wishName;
    private String wishDesc;

    public wishClass() {

    }

    public wishClass(String wishName, String wishDesc) {
        this.wishName = wishName;
        this.wishDesc = wishDesc;

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
}
