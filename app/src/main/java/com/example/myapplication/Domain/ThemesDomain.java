package com.example.myapplication.Domain;

import java.util.List;

public class ThemesDomain {
    private List<NestedDomain> itemList;
    private String itemText;
    private boolean isExpandable;

    public ThemesDomain(List<NestedDomain> itemList, String itemText) {
        this.itemList = itemList;
        this.itemText = itemText;
        isExpandable = false;
    }

    public List<NestedDomain> getItemList() {
        return itemList;
    }

    public String getItemText() {
        return itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setItemList(List<NestedDomain> itemList) {
        this.itemList = itemList;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
