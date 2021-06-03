package com.edson.dscatalog.services;

import com.edson.dscatalog.dto.ProductDTO;
import com.edson.dscatalog.entities.Product;
import com.edson.dscatalog.repositories.ProductRepository;
import com.edson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
        return new ProductDTO(entity, entity.getCategories());
    }

//    public ProductDTO insert(ProductDTO dto) {
//        Product entity = new Product();
//        entity.setName(dto.getName());
//        entity.setDescription(dto.getDescription());
//        entity.setDate(dto.getDate());
//        entity.
//    }
}
