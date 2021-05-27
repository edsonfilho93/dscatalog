package com.edson.dscatalog.dto;

import com.edson.dscatalog.entities.Category;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
