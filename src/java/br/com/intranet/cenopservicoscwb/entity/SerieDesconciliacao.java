/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_serie_historica_desconciliacao_ob_paj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SerieDesconciliacao.findAll", query = "SELECT s FROM SerieDesconciliacao s")
    , @NamedQuery(name = "SerieDesconciliacao.findByCodigo", query = "SELECT s FROM SerieDesconciliacao s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "SerieDesconciliacao.findByQuantidade", query = "SELECT s FROM SerieDesconciliacao s WHERE s.quantidade = :quantidade")
    , @NamedQuery(name = "SerieDesconciliacao.findByValor", query = "SELECT s FROM SerieDesconciliacao s WHERE s.valor = :valor")
    , @NamedQuery(name = "SerieDesconciliacao.findByData", query = "SELECT s FROM SerieDesconciliacao s WHERE s.data = :data")
    , @NamedQuery(name = "SerieDesconciliacao.findByValorAbsoluto", query = "SELECT s FROM SerieDesconciliacao s WHERE s.valorAbsoluto = :valorAbsoluto")
    , @NamedQuery(name = "SerieDesconciliacao.findByPrefixoOrigem", query = "SELECT s FROM SerieDesconciliacao s WHERE s.prefixoOrigem = :prefixoOrigem")})
public class SerieDesconciliacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Column(name = "QUANTIDADE")
    private Integer quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private Double valor;
    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "VALOR_ABSOLUTO")
    private Double valorAbsoluto;
    @Column(name = "PREFIXO_ORIGEM")
    private Integer prefixoOrigem;

    public SerieDesconciliacao() {
    }

    public SerieDesconciliacao(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValorAbsoluto() {
        return valorAbsoluto;
    }

    public void setValorAbsoluto(Double valorAbsoluto) {
        this.valorAbsoluto = valorAbsoluto;
    }

    public Integer getPrefixoOrigem() {
        return prefixoOrigem;
    }

    public void setPrefixoOrigem(Integer prefixoOrigem) {
        this.prefixoOrigem = prefixoOrigem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SerieDesconciliacao)) {
            return false;
        }
        SerieDesconciliacao other = (SerieDesconciliacao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.intranet.cenopservicoscwb.entity.SerieDesconciliacao[ codigo=" + codigo + " ]";
    }
    
}
