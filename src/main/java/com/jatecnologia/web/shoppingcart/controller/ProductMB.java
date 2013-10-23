package com.jatecnologia.web.shoppingcart.controller;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jatecnologia.web.shoppingcart.entity.Product;
import com.jatecnologia.web.shoppingcart.services.ProductService;

@Named
@RequestScoped
public class ProductMB {
  @Inject
  private ProductService produtoServices;
  private List<Product> produtos;

  @PostConstruct
  public void init() {
    produtos = produtoServices.findAll();
  }

  public List<Product> getProdutos() {
    return produtos;
  }
}