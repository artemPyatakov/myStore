package com.pyatakov.other.objectContainer;

import com.pyatakov.entity.Category;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable {

    private List<Category> categories;

    public Categories() {
    }

    public Categories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
