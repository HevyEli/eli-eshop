package org.eli.product.repository;

import org.eli.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository <Product, Long> {

}
