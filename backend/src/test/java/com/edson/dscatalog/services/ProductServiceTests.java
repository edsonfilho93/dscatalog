package com.edson.dscatalog.services;

import com.edson.dscatalog.repositories.ProductRepository;
import com.edson.dscatalog.services.exceptions.DatabaseException;
import com.edson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @BeforeEach
    private void setUp() throws Exception {
        this.existingId = 1L;
        this.nonExistingId = 2L;
        this.dependentId = 3L;

        doNothing().when(repository).deleteById(existingId);

        doThrow(ResourceNotFoundException.class)
                .when(repository).deleteById(nonExistingId);

        doThrow(DataIntegrityViolationException.class)
                .when(repository).deleteById(dependentId);

    }

    @Test
    public void deleteShouldDotNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        verify(repository).deleteById(existingId);
        doNothing().when(repository).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowsExceptionsWhenIdNonExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        verify(repository).deleteById(nonExistingId);
        doThrow(ResourceNotFoundException.class)
                .when(repository).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowsExceptionsWhenDependentId() {
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

        verify(repository).deleteById(dependentId);
        doThrow(DatabaseException.class)
                .when(repository).deleteById(dependentId);
    }
}
