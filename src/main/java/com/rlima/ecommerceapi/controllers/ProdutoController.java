package com.rlima.ecommerceapi.controllers;

import com.rlima.ecommerceapi.entities.Produto;
import com.rlima.ecommerceapi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/itens")
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @PostMapping("/item")
    public Produto createProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }
}
