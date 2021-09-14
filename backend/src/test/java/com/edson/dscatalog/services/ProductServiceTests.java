package com.edson.dscatalog.services;

import com.edson.dscatalog.repositories.ProductRepository;
import com.edson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Long existingId;

    private Long nonExistingId;

    @BeforeEach
    private void setUp() throws Exception {
        this.existingId = 1L;
        this.nonExistingId = 2L;

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(ResourceNotFoundException.class).when(repository).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldDotNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository).deleteById(existingId);

        Mockito.doNothing().when(repository).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowsExceptionsWhenIdNonExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository).deleteById(nonExistingId);
        Mockito.doThrow(ResourceNotFoundException.class)
                .when(repository).deleteById(nonExistingId);
    }
}
