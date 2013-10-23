package com.jatecnologia.web.shoppingcart.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.jatecnologia.web.shoppingcart.entity.Customer;
import com.jatecnologia.web.shoppingcart.entity.Order;
import com.jatecnologia.web.shoppingcart.entity.OrderItem;
import com.jatecnologia.web.shoppingcart.entity.Product;
import com.jatecnologia.web.shoppingcart.exception.CustomerAlreadyExistException;
import com.jatecnologia.web.shoppingcart.exception.CustomerNotFoundException;
import com.jatecnologia.web.shoppingcart.services.CustomerService;
import com.jatecnologia.web.shoppingcart.services.OrderService;

@Named
@SessionScoped
public class ShoppingCartMB implements Serializable {
  private static final long serialVersionUID = 1172514592649335124L;
  private Order pedidoCarrinho;
  private Long idPedidoGerado;
  private Customer cliente;
  private Product produtoRemover;
  @Inject
  private transient CustomerService clienteServices;
  @Inject
  private transient OrderService pedidoServices;
  @Inject
  private transient UtilMB utilsMB;

  @PostConstruct
  public void init() {
    pedidoCarrinho = new Order();
    cliente = new Customer();
  }

  public String adicionarItem(Product produto) {
    pedidoCarrinho.adicionarItem(produto, 1);
    return "carrinho?faces-redirect=true";
  }

  public void removerItem() {
    pedidoCarrinho.removerItem(produtoRemover);
  }

  public void atualizarQuantidadeItem(Product produto, Integer novaQuantidade) {
    pedidoCarrinho.atualizarQuantidade(produto, novaQuantidade);
  }

  public String fecharPedidoUsuarioExistente() {
    return fecharPedido();
  }

  public String fecharPedidoNovoUsuario() {
    try {
      cliente = clienteServices.adicionar(cliente);
    }
    catch (CustomerAlreadyExistException e) {
      adicionarMensagem(FacesMessage.SEVERITY_ERROR, "cliente-existente");
      return null;
    }
    return fecharPedido();
  }

  private String fecharPedido() {
    try {
      pedidoCarrinho = pedidoServices.criarPedido(pedidoCarrinho,
          cliente.getEmail(), cliente.getSenha());
      idPedidoGerado = pedidoCarrinho.getId();
      init();
      return "pedidoFechado?faces-redirect=true";
    }
    catch (CustomerNotFoundException e) {
      adicionarMensagem(FacesMessage.SEVERITY_ERROR, "cliente-nao-encontrado");
      return null;
    }
  }

  private void adicionarMensagem(FacesMessage.Severity severidade, String chave)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.addMessage(null,
        new FacesMessage(severidade, utilsMB.getMessage(chave), null));
  }

  public void recalcularTotal(OrderItem itemPedido) {
    itemPedido.calcularTotal();
    pedidoCarrinho.calcularTotal();
  }

  public boolean temItens() {
    return pedidoCarrinho.getItens().size() > 0;
  }

  public Customer getCliente() {
    return cliente;
  }

  public Long getIdPedidoGerado() {
    return idPedidoGerado;
  }

  public Order getPedidoCarrinho() {
    return pedidoCarrinho;
  }

  public Product getProdutoRemover() {
    return produtoRemover;
  }

  public void setProdutoRemover(Product produtoRemover) {
    this.produtoRemover = produtoRemover;
  }
}