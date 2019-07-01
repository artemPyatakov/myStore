package com.pyatakov.repository;

import com.pyatakov.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository
        extends CrudRepository<Category, Long> {

}
