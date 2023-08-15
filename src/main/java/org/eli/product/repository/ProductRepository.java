package org.eli.product.repository;

import org.eli.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final List<Product> products;
    public ProductRepository() {
        this.products = new ArrayList<>();
    }

    public void createProduct (Product product) {
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }
    public Optional<Product> getProductById (long id) {
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    public void editProductById (long id, String name, double price, String description, int quantity) {
        getProductById(id).ifPresent(product -> {
                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);
                product.setQuantity(quantity);
                        }
        );

    }
    public void editProductQtyById (long id, int quantity) {
        getProductById(id).ifPresent(product ->{
            product.setQuantity(quantity);
        });
    }
    public void deleteProductById(long id) {
    getProductById(id).ifPresent(product ->{
        products.remove(product);
    });
    }
    public boolean checkIfProductExists (long id) {
        return getProductById(id).isPresent();
    }

}
