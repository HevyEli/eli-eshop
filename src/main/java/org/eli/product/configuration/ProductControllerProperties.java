package org.eli.product.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")

public class ProductControllerProperties {
    private ProductControllerProperties productId;

    public ProductControllerProperties getProductId() {
        return productId;
    }

    public void setProductId(ProductControllerProperties productId) {
        this.productId = productId;
    }
}

class ProductControllerValidation {
    private String validation;

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}