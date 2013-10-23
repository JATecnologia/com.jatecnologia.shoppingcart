package com.jatecnologia.web.shoppingcart.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.jatecnologia.web.shoppingcart.entity.Customer;
import com.jatecnologia.web.shoppingcart.exception.CustomerAlreadyExistException;

@Stateless
public class CustomerService {
  @PersistenceContext
  private EntityManager em;

  public Customer findByEmailAndSenha(String email, String senha) {
    Query query = em
        .createQuery("SELECT c FROM Cliente c WHERE c.email = :email AND c.senha = :senha");
    query.setParameter("email", email);
    query.setParameter("senha", senha);
    try {
      return (Customer) query.getSingleResult();
    }
    catch (NoResultException e) {
      return null;
    }
  }

  public Customer adicionar(Customer cliente) throws CustomerAlreadyExistException {
    try {
      em.persist(cliente);
      return cliente;
    }
    catch (PersistenceException e) {
      throw new CustomerAlreadyExistException();
    }
  }
}