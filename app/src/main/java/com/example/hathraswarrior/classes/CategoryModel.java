package com.example.hathraswarrior.classes;

public class CategoryModel {
   public String categoryTitle;
   public String imageAddress;

    public CategoryModel(String categoryTitle, String imageAddress) {
        this.categoryTitle = categoryTitle;
        this.imageAddress = imageAddress;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
