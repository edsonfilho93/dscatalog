package com.edson.dscatalog.dto;

import com.edson.dscatalog.entities.Category;
import com.edson.dscatalog.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Double price;
    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, String imgUrl, Double price, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.imgUrl = entity.getImgUrl();
        this.price = entity.getPrice();
        this.date = entity.getDate();
    }

    public ProductDTO(Product entity, Set<Category> list) {
        this(entity);
        list.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }
}
