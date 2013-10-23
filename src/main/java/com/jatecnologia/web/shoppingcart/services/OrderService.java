package com.jatecnologia.web.shoppingcart.services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jatecnologia.web.shoppingcart.entity.Customer;
import com.jatecnologia.web.shoppingcart.entity.Order;
import com.jatecnologia.web.shoppingcart.exception.CustomerNotFoundException;

@Stateless
public class OrderService {
  @PersistenceContext
  private EntityManager em;
  @Inject
  private CustomerService clienteServices;

  public Order criarPedido(Order pedido, String emailCliente,
      String senhaCliente) throws CustomerNotFoundException
  {
    Customer cliente = clienteServices.findByEmailAndSenha(emailCliente,
        senhaCliente);
    if (cliente == null) { throw new CustomerNotFoundException(); }
    pedido.setData(new Date());
    pedido.setCliente(cliente);
    em.persist(pedido);
    return pedido;
  }
}
