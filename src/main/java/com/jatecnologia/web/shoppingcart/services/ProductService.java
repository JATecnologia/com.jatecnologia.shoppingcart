package com.jatecnologia.web.shoppingcart.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jatecnologia.web.shoppingcart.entity.Product;

@Stateless
public class ProductService {
  @PersistenceContext
  private EntityManager em;

  @SuppressWarnings("unchecked")
  public List<Product> findAll() {
    return em.createQuery("SELECT p FROM Produto p ORDER BY p.titulo")
        .getResultList();
  }
}