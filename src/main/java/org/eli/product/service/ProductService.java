package org.eli.product.service;

import org.eli.exception.ProductNotFoundException;
import org.eli.product.model.Product;
import org.eli.product.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    @Autowired
    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public void createProduct(Product product) {
        productJpaRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productJpaRepository.findAll();
    }

    public Product getProductById(long id) {
        return productJpaRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product editProductById(long id, String name, double price, String description, int quantity) {
        Optional<Product> productOptional = productJpaRepository.findById(id);
        productOptional.ifPresent(product -> {
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            productJpaRepository.save(product);
        });
        return null;
    }

    public Product editProductQtyById(long id, int quantity) {
        Optional<Product> productOptional = productJpaRepository.findById(id);
        productOptional.ifPresent(product -> {
            product.setQuantity(quantity);
            productJpaRepository.save(product);
        });
        return null;
    }

    public Optional<Product> deleteProductById(long id) {
        if (!productJpaRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productJpaRepository.deleteById(id);
        return null;
    }

    public boolean checkIfProductExists(long id) {
        productJpaRepository.findById(id);
        return true;
    }

//
//    public Product getProductById(long id) {
//        Optional<Product> p = productJpaRepository.findById(id);
//        Product prod = p.isPresent() ? p.get() : null;
//        return prod;
//
//    }
//
//    public boolean checkIfProductExists(long id) {
//        Optional<Product> p = productJpaRepository.findById(id);
//        Product prod = p.isPresent() ? p.get() : null;
//        return p.isPresent();
//    }
//
//    public List<Product> getAllProducts() {
//        return productJpaRepository.findAll();
//    }
//
//
//    public Optional<Product> deleteProductById(long id) {
//        Optional<Product> existingProduct = productJpaRepository.findById(id);
//
//        if (existingProduct.isPresent()) {
//            productJpaRepository.deleteById(id);
//        }
//        return existingProduct;
//    }
////    public  Product deleteProductById(long id) {
////        Optional<Product> p = productRepository.findById(id);
////        if (p.isPresent()) {
////            Product prod = p.get();
////            productRepository.deleteById(id);
////            return prod;
////        } else {
////            return null;
////        }
////
////
////    }
//
//    public void createProduct(Product product) {
//        productJpaRepository.save(product);
//    }
//
//    public Product editProductById(long id, String name, double price, String description, int quantity) {
//        Optional<Product> p = productJpaRepository.findById(id);
//        Product product = p.get();
//        product.setName(name);
//        product.setPrice(price);
//        product.setDescription(description);
//        product.setQuantity(quantity);
//        return productJpaRepository.save(product);
//
//    }
//
//    public Product editProductQtyById(long id, int quantity) {
//        Optional<Product> p = productJpaRepository.findById(id);
//        Product product = p.get();
//        product.setQuantity(quantity);
//        return productJpaRepository.save(product);
//    }

}