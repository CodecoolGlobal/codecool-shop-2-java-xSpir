package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce){
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier paks2 = new Supplier(1,"Paks2", "Completely normal phenomenon");
        supplierDataStore.add(paks2);
        Supplier nutKing = new Supplier(2,"NutKing", "The best nuts in town");
        supplierDataStore.add(nutKing);
        Supplier linux = new Supplier(3,"Linus Torvalds", "The biggest brain in town");
        supplierDataStore.add(linux);
        Supplier gymBro  = new Supplier(3,"Gym Bro", "Big health,big muscle");
        supplierDataStore.add(gymBro);



        //setting up a new product category
        ProductCategory tablet = new ProductCategory(1, "Linux kernel", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);

        ProductCategory edibleNuts = new ProductCategory(2, "Edible Nut", "Nut", "A nut you can eat.");
        productCategoryDataStore.add(edibleNuts);

        ProductCategory nuclearCore = new ProductCategory(3, "Nuclear cores", "Nuclear", "A nut you cannot eat.");
        productCategoryDataStore.add(nuclearCore);

        ProductCategory nutrients = new ProductCategory(4, "Nutrients", "Muscle", "FOCUS ON YOUR HEALTH. YOUR LIFE DEPENDS ON IT");
        productCategoryDataStore.add(nutrients);

        //setting up products and printing it
        productDataStore.add(new Product(1,"Amazon Fire", 49.9f, "USD", "De String.", tablet, amazon));
        productDataStore.add(new Product(2,"Lenovo IdeaPad Miix 700", 479, "USD", "Description String.", tablet, lenovo));
        productDataStore.add(new Product(3,"Amazon Randomtablet", 49.9f, "USD", "Description String.", tablet, amazon));
        productDataStore.add(new Product(4,"Nuts", 49.9f, "USD", "Description String.", edibleNuts, amazon));
        productDataStore.add(new Product(4,"Nuts", 49.9f, "USD", "Description String.", edibleNuts, amazon));

    }
}
