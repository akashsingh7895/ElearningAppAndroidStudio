package com.example.hathraswarrior.classes;

import java.util.List;

public class HomepageModel {

    public static final int BANNER = 0;
    public static final int HORIZONTAL = 1;
    public static final int GRID = 2;

   public int type;
   public String layoutTitle;
   public List<HorizontalItemModel> horizontalLst;
   public String bgColor;
   public List<ViewAllModel>viewAllModelList;
   public List<ViewAllModel>viewAllGridList;
   public List<String> postersList;
   public List<String> productList;

    public HomepageModel(int type, String layoutTitle,List<String> productList) {
        this.type = type;
        this.layoutTitle = layoutTitle;
       this.productList = productList;

    }


    public HomepageModel(int type, String layoutTitle, String bgColor,List<String> productList) {
        this.type = type;
        this.layoutTitle = layoutTitle;
        this.bgColor = bgColor;
        this.productList = productList;

    }

    public HomepageModel(int type, List<String> postersList) {
        this.type = type;
        this.postersList = postersList;
    }

    public List<String> getPostersList() {
        return postersList;
    }

    public void setPostersList(List<String> postersList) {
        this.postersList = postersList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLayoutTitle() {
        return layoutTitle;
    }

    public void setLayoutTitle(String layoutTitle) {
        this.layoutTitle = layoutTitle;
    }

    public List<HorizontalItemModel> getHorizontalLst() {
        return horizontalLst;
    }

    public void setHorizontalLst(List<HorizontalItemModel> horizontalLst) {
        this.horizontalLst = horizontalLst;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<ViewAllModel> getViewAllModelList() {
        return viewAllModelList;
    }

    public void setViewAllModelList(List<ViewAllModel> viewAllModelList) {
        this.viewAllModelList = viewAllModelList;
    }

    public List<ViewAllModel> getViewAllGridList() {
        return viewAllGridList;
    }

    public void setViewAllGridList(List<ViewAllModel> viewAllGridList) {
        this.viewAllGridList = viewAllGridList;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }
}
