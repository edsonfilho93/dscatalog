package com.edson.dscatalog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Category implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String name;
}
