package com.rlima.ecommerceapi.repositories;

import com.rlima.ecommerceapi.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
