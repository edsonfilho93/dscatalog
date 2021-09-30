package com.edson.dscatalog.services;

import com.edson.dscatalog.entities.Product;
import com.edson.dscatalog.factories.Factory;
import com.edson.dscatalog.repositories.ProductRepository;
import com.edson.dscatalog.services.exceptions.DatabaseException;
import com.edson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Long existingId;

    private Long nonExistingId;

    private Long dependentId;

    private Product product;

    private PageImpl<Product> page;

    @BeforeEach
    private void setUp() throws Exception {
        this.existingId = 1L;
        this.nonExistingId = 2L;
        this.dependentId = 3L;
        product = Factory.createProduct();

        page = new PageImpl<>(of(product));

        doNothing().when(repository).deleteById(existingId);
        doThrow(ResourceNotFoundException.class).when(repository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.findAll((Pageable) any())).thenReturn(page);
        when(repository.save(any())).thenReturn(product);
        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
    }

    @Test
    public void findAllShouldReturnProductWhenIdExists() {
        assertDoesNotThrow(() -> service.findById(existingId));
        verify(repository, times(2)).findById(existingId);
    }

    @Test
    public void findAllShouldThrowResourceNotFoundExceptionWhenIdNonExists() {
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        assertNotNull(service.findAll(pageable));
        verify(repository, times(1)).findAll(pageable);
    }


    @Test
    public void deleteShouldDotNothingWhenIdExists() {
        assertDoesNotThrow(() -> service.delete(existingId));
        verify(repository).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowsExceptionsWhenIdNonExists() {
        assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
        verify(repository).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowsExceptionsWhenDependentId() {
        assertThrows(DatabaseException.class, () -> service.delete(dependentId));
        verify(repository).deleteById(dependentId);
    }
}
