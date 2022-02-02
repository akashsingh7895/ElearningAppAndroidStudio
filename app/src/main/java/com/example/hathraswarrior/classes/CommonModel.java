package com.example.hathraswarrior.classes;

public class CommonModel {
    public String productTitle,productSubtitle;
    public String productImage;


    public CommonModel(String productTitle, String productSubtitle, String productImage) {
        this.productTitle = productTitle;
        this.productSubtitle = productSubtitle;
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
