package org.eli.repository;

import org.eli.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository <Product, Long> {

}
