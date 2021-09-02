package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    static ProductService service;
    static ProductCategory cat = new ProductCategory(1, "cic", "cic", "cic");
    static ProductCategory cat2 = new ProductCategory(2, "cic", "cic", "cic");
    static ProductCategory cat3 = new ProductCategory(3, "cic", "cic", "cic");
    static Product prod = new Product(1, "cica", 207.1f, "USD", "cica",
            cat, new Supplier(1, "cic", "cic"));
    static Product prod2 = new Product(2, "cica", 250.1f, "USD", "cica",
            cat2, new Supplier(2, "cic", "cic"));
    static Product prod3 = new Product(3, "cica", 210.1f, "USD", "cica",
            cat2, new Supplier(3, "cic", "cic"));


    @Test
    void getEveryProduct_no_products_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        when(productDao.getAll()).thenReturn(null);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        service = new ProductService(productDao, productCategoryDao);
        assertNull(service.getEveryProducts());
    }

    @Test
    void getEveryProduct_one_product_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        when(productDao.getAll()).thenReturn(Collections.singletonList(prod));
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        service = new ProductService(productDao, productCategoryDao);
        List<Product> expected = new ArrayList<>();
        expected.add(prod);
        assertEquals(expected, service.getEveryProducts());
    }

    @Test
    void getEveryProduct_multiple_products_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        when(productDao.getAll()).thenReturn(Arrays.asList(prod, prod2, prod3));
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        service = new ProductService(productDao, productCategoryDao);
        List<Product> expected = new ArrayList<>();
        expected.add(prod);
        expected.add(prod2);
        expected.add(prod3);
        assertEquals(expected, service.getEveryProducts());
    }

    @Test
    void getEveryProductCategory_no_categories_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.getAll()).thenReturn(null);
        service = new ProductService(productDao, productCategoryDao);
        assertNull(service.getEveryProductCategory());
    }

    @Test
    void getEveryProductCategory_one_category_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.getAll()).thenReturn(Collections.singletonList(cat));
        service = new ProductService(productDao, productCategoryDao);
        List<ProductCategory> expected = new ArrayList<>();
        expected.add(cat);
        assertEquals(expected, service.getEveryProductCategory());
    }

    @Test
    void getEveryProductCategory_multiple_categories_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.getAll()).thenReturn(Arrays.asList(cat, cat2, cat3));
        service = new ProductService(productDao, productCategoryDao);
        List<ProductCategory> expected = new ArrayList<>();
        expected.add(cat);
        expected.add(cat2);
        expected.add(cat3);
        assertEquals(expected, service.getEveryProductCategory());
    }

    @Test
    void getProductCategory_no_category_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.find(1)).thenReturn(null);
        service = new ProductService(productDao, productCategoryDao);
        assertNull(service.getProductCategory(1));
    }

    @Test
    void getProductCategory_correct_category_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.find(1)).thenReturn(cat);
        service = new ProductService(productDao, productCategoryDao);
        assertEquals(cat, service.getProductCategory(1));
    }

    @Test
    void getProductsForCategory_no_products_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productDao.getBy(cat)).thenReturn(null);
        service = new ProductService(productDao, productCategoryDao);
        assertEquals(new ArrayList<>(), service.getProductsForCategory(1));
    }

    @Test
    void getProductsForCategory_one_product_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.find(1)).thenReturn(cat);
        when(productDao.getBy(cat)).thenReturn(Arrays.asList(prod));
        service = new ProductService(productDao, productCategoryDao);
        List<Product> expected = new ArrayList<>();
        expected.add(prod);
        assertEquals(expected, service.getProductsForCategory(1));
    }

    @Test
    void getProductsForCategory_multiple_products_test() {
        ProductDao productDao = mock(ProductDaoMem.class);
        ProductCategoryDao productCategoryDao = mock(ProductCategoryDaoMem.class);
        when(productCategoryDao.find(2)).thenReturn(cat2);
        when(productDao.getBy(cat2)).thenReturn(Arrays.asList(prod2, prod3));
        service = new ProductService(productDao, productCategoryDao);
        List<Product> expected = new ArrayList<>();
        expected.add(prod2);
        expected.add(prod3);
        assertEquals(expected, service.getProductsForCategory(2));
    }

}
