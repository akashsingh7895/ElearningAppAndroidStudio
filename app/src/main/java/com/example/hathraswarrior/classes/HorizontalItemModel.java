package com.example.hathraswarrior.classes;

public class HorizontalItemModel {
   public String imageResource;
   public String title,subTitle,Price,productId;

    public HorizontalItemModel(String productId ,String imageResource, String title, String subTitle, String price) {
        this.productId = productId;
        this.imageResource = imageResource;
        this.title = title;
        this.subTitle = subTitle;
        Price = price;
    }



    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
