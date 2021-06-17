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
        ProductCategory kernel = new ProductCategory(1, "Linux kernel", "Hardware", "The world's most popular open-source operating systems.");
        productCategoryDataStore.add(kernel);

        ProductCategory edibleNuts = new ProductCategory(2, "Edible Nut", "Nut", "A nut you can eat.");
        productCategoryDataStore.add(edibleNuts);

        ProductCategory nuclearCore = new ProductCategory(3, "Nuclear cores", "Nuclear", "A nut you cannot eat.");
        productCategoryDataStore.add(nuclearCore);

        ProductCategory nutrients = new ProductCategory(4, "Nutrients", "Muscle", "FOCUS ON YOUR HEALTH. YOUR LIFE DEPENDS ON IT");
        productCategoryDataStore.add(nutrients);

        //setting up products and printing it
        productDataStore.add(new Product(1,"Linux Kernel 4.13", 1149.9f, "USD", "The stuff with your favourite penguin sliding game.", kernel, linux));
        productDataStore.add(new Product(2,"Linux Kernel 5.0", 979.9f, "USD", "Szamitson szojatekra.", kernel, linux));
        productDataStore.add(new Product(3,"Linux Kernel 5.8", 666.6f, "USD", "Good as hell!", kernel, linux));
        productDataStore.add(new Product(4,"Linux Kernel 5.12", 44.4f, "USD", "Fresh and reliable as a nut.", kernel, linux));

        productDataStore.add(new Product(5,"Regular nut", 249.9f, "USD", "For regular occasions.", edibleNuts, nutKing));
        productDataStore.add(new Product(6,"Christmas nut", 4345.9f, "USD", "Share with your family.", edibleNuts, nutKing));
        productDataStore.add(new Product(7,"Oily nut", 3429.9f, "USD", "Good for your skin.", edibleNuts, nutKing));
        productDataStore.add(new Product(8,"Misterious Nutto", 1000.9f, "USD", "Try me - Misterious Nutto", edibleNuts, nutKing));

        productDataStore.add(new Product(9,"Type 1 Nuclear Core", 300.9f, "USD", "Don't ask me.", nuclearCore, paks2));
        productDataStore.add(new Product(10,"Not so Xplody Core", 475900.9f, "USD", "For the nation.", nuclearCore, paks2));
        productDataStore.add(new Product(11,"Leaky Core", 26546.9f, "USD", "Don't ask, don't tell.", nuclearCore, paks2));
        productDataStore.add(new Product(12,"Indestructible core", 1000.9f, "USD", "Barely used.", nuclearCore, paks2));

        productDataStore.add(new Product(13,"Whey", 469.1f, "USD", "This the whey.", nutrients, gymBro));
        productDataStore.add(new Product(14,"Grains", 649.1f, "USD", "This is the grai...it doesn't work.", nutrients, gymBro));
        productDataStore.add(new Product(15,"Vegetables", 496.1f, "USD", "It's more likely a food, than a table.", nutrients, gymBro));
        productDataStore.add(new Product(16,"Fruits", 694.1f, "USD", "Smooth some juice!", nutrients, gymBro));

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
