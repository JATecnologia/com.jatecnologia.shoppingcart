package com.jatecnologia.web.shoppingcart.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class OrderItem implements Serializable, Comparable<OrderItem> {
  private static final long serialVersionUID = 7848426935997596400L;
  @ManyToOne
  @JoinColumn(name = "id_produto")
  private Product produto;
  @Column(name = "preco_unitario")
  @NotNull
  private Double precoUnitario;
  @NotNull
  private Integer quantidade;
  @Column(name = "preco_total")
  @NotNull
  private Double precoTotal;

  public OrderItem() {}

  public OrderItem(Product produto) {
    this.produto = produto;
  }

  public OrderItem(Product produto, Integer quantidade) {
    this.produto = produto;
    this.precoUnitario = produto.getPreco();
    this.quantidade = quantidade;
    calcularTotal();
  }

  public void calcularTotal() {
    precoTotal = precoUnitario * quantidade;
  }

  public void atualizarQuantidade(Integer novaQuantidade) {
    this.quantidade = novaQuantidade;
    calcularTotal();
  }

  public Product getProduto() {
    return produto;
  }

  public void setProduto(Product produto) {
    this.produto = produto;
  }

  public Double getPrecoUnitario() {
    return precoUnitario;
  }

  public void setPrecoUnitario(Double precoUnitario) {
    this.precoUnitario = precoUnitario;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public Double getPrecoTotal() {
    return precoTotal;
  }

  public void setPrecoTotal(Double precoTotal) {
    this.precoTotal = precoTotal;
  }

  @Override
  public String toString() {
    return "ItemPedido [produto=" + produto + ", precoUnitario="
        + precoUnitario + ", quantidade=" + quantidade + ", precoTotal="
        + precoTotal + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((produto == null) ? 0 : produto.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    OrderItem other = (OrderItem) obj;
    if (produto == null) {
      if (other.produto != null) return false;
    }
    else if (!produto.equals(other.produto)) return false;
    return true;
  }

  @Override
  public int compareTo(OrderItem o) {
    return produto.getTitulo().compareTo(o.getProduto().getTitulo());
  }
}