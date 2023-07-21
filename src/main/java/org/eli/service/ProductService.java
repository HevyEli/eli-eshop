package org.eli.service;

import org.eli.product.Product;
import org.eli.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductJpaRepository productRepository;

    @Autowired
    public ProductService(ProductJpaRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(long id) {
        Optional<Product> p = productRepository.findById(id);
        Product prod = p.isPresent() ? p.get() : null;
        return prod;

    }

    public boolean checkIfProductExists(long id) {
        Optional<Product> p = productRepository.findById(id);
        Product prod = p.isPresent() ? p.get() : null;
        return p.isPresent();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product deleteProductById(long id) {
        productRepository.deleteById(id);
        return new Product();
    }
//    public  Product deleteProductById(long id) {
//        Optional<Product> p = productRepository.findById(id);
//        if (p.isPresent()) {
//            Product prod = p.get();
//            productRepository.deleteById(id);
//            return prod;
//        } else {
//            return null;
//        }
//
//
//    }

    public void createProduct(Product item) {
        productRepository.save(item);
    }

    public Product editProductById(long id, String name, double price, String description, int quantity) {
        Optional<Product> p = productRepository.findById(id);
        Product product = p.get();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setQuantity(quantity);
        return productRepository.save(product);

    }

    public Product editProductQtyById(long id, int quantity) {
        Optional<Product> p = productRepository.findById(id);
        Product product = p.get();
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

}