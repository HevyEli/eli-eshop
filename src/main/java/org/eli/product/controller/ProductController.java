package org.eli.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eli.product.configuration.ProductControllerProperties;
import org.eli.product.dto.ProductResponseMessage;
import org.eli.product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.eli.product.service.ProductService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@RestController
@Configuration
@PropertySource("classpath:application.yml")
@RequestMapping("/products")

public class ProductController {
    @Value("${app.productId.validation}")
    private String productIdValidation;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<ProductResponseMessage>  getAllProducts() throws JsonProcessingException {
        logger.info("getAllProducts productService request");
        List<Product> products = productService.getAllProducts();
        ObjectMapper objectMapper = new ObjectMapper();
        String productsJson = objectMapper.writeValueAsString(products);

        if (products.isEmpty()) {
            logger.info("getAllProducts response: There are no products available.");
            ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.NOT_FOUND, "There are no products", "[empty list!]");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        else {
            logger.info("getAllProducts response: Returned list {} of products.", products.size());
        }
        products.sort(Comparator.comparing(Product::getId));
        ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.OK, products.size()  + " product(s) returned", "{ \"product\": [ " + productsJson + " ] }");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseMessage> getProductById(@PathVariable long id) throws JsonProcessingException {
        logger.info("getProductById received request for product: {}", id);
        Product product = productService.getProductById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);
        if (product == null) {
            logger.info("There is no product with id {}.", id);
            ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.NOT_FOUND, "Product id "+ id + " not found", "Product not found" );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        logger.info("Product {} found.", id);
        logger.info(String.valueOf(product));
        ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.OK, "Product id" + product, "{ \"product\": [ " + productJson + " ] }");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseMessage> deleteProductById(@PathVariable long id) {
        logger.info("deleteProductById received request to delete product: {}.", id);
        Optional<Product> existingProduct = productService.deleteProductById(id);
        if (existingProduct.isEmpty()) {
            logger.info("No product with id {} found.", id);
            ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.NOT_FOUND, "Product " + id + " not found", "[]");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        logger.info("Product id {} has been deleted.", id);
        ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.OK, "Product " + id + " has been deleted", "[DLTD]");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

    }

    @Autowired
    private ProductControllerProperties productControllerProperties;
    @PostMapping
    public ResponseEntity<ProductResponseMessage> createProduct(@RequestBody Product product) throws JsonProcessingException {
        logger.info("createProduct received request to create product: " + product);
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);
        if ("enabled".equals(productIdValidation)) {
            logger.info("productIdValidation is enabled");
            boolean checkIfExists = productService.checkIfProductExists(product.getId());
            if (checkIfExists == true) {
                logger.info("Product {} already exists.", product.getId());
                ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.CONFLICT, "Product " + product.getId() + " already exists", "[ERROR_EXIST]");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseMessage);
            } if  ("disabled".equals(productIdValidation)) {
                logger.info("productIdValidation is disabled");
            }
        }
        productService.createProduct(product);
        logger.info("Product " + product + " has been created.");
        ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.OK, "product: " + product.getId() + " has been created.", "{ \"product\": [ " + productJson + " ] }");
        return  ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseMessage> editProductById(@PathVariable long id, @RequestBody Product newProduct)  {
        logger.info("editProductById received request to edit product {}.", id);

        Product oldProduct = productService.getProductById(id);
        if (oldProduct == null) {
            logger.info("Product {} has not been found.", id);
            ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.NOT_FOUND, "Product " + id + "has not been found!", "[ERROR]");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        Product updatedProduct = productService.editProductById(id, newProduct.getName(), newProduct.getPrice(), newProduct.getDescription(), newProduct.getQuantity());
        logger.info("Product " + id + " has been updated. " +
                "Old values Id: " + oldProduct.getId() + " name: " + oldProduct.getName() + "Price: " + oldProduct.getPrice() + " Description: " + oldProduct.getDescription() +
                "new values id:{}." + " name: " + updatedProduct.getName() + " price: " + updatedProduct.getPrice() + " description: " + updatedProduct.getDescription() + "qty: " + updatedProduct.getQuantity(), id);

        ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.OK, "", "Product: "+"id: " + id + " has been updated with these attributes, "
                + "name: " + updatedProduct.getName() + " price: " + updatedProduct.getPrice() + " quantity: "
                + updatedProduct.getQuantity() + " descripriton: " + updatedProduct.getDescription() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/editQty/{id}")
    public ResponseEntity<ProductResponseMessage> editProductQtyById(@PathVariable long id, @RequestBody Product newQty) throws JsonProcessingException {
        logger.info("editProductQtyById received request update Qty for product {}.", id);
        Product existingProduct = productService.getProductById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(existingProduct);
        if (existingProduct == null) {
            logger.info("Product {} has not been found.", id);
            ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.NOT_FOUND, "Product " + id + " has not been found!", "[ERROR]");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        Product newQuantity = productService.editProductQtyById(id, newQty.getQuantity());
        logger.info("Product {} quantity has been updated from: {} to {}.", id, existingProduct.getQuantity(), newQuantity.getQuantity());
        ProductResponseMessage responseMessage = new ProductResponseMessage(HttpStatus.OK, "Product " + id + " quantity has been update from "
                + existingProduct.getQuantity()
                + " to " + newQuantity.getQuantity() + ".", "{ \"product\": [ " + productJson + " ] }");

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
}
