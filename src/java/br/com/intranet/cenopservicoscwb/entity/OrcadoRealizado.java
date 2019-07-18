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
@Table(name = "tb_orcado_realizado_desconciliacao_ob_paj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrcadoRealizado.findAll", query = "SELECT o FROM OrcadoRealizado o")
    , @NamedQuery(name = "OrcadoRealizado.findByCodigoDesconciliacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.codigoDesconciliacao = :codigoDesconciliacao")
    , @NamedQuery(name = "OrcadoRealizado.findByNpj", query = "SELECT o FROM OrcadoRealizado o WHERE o.npj = :npj")
    , @NamedQuery(name = "OrcadoRealizado.findByVariacaoNpj", query = "SELECT o FROM OrcadoRealizado o WHERE o.variacaoNpj = :variacaoNpj")
    , @NamedQuery(name = "OrcadoRealizado.findByContaControle", query = "SELECT o FROM OrcadoRealizado o WHERE o.contaControle = :contaControle")
    , @NamedQuery(name = "OrcadoRealizado.findByContaDepositaria", query = "SELECT o FROM OrcadoRealizado o WHERE o.contaDepositaria = :contaDepositaria")
    , @NamedQuery(name = "OrcadoRealizado.findBySaldoContaControle", query = "SELECT o FROM OrcadoRealizado o WHERE o.saldoContaControle = :saldoContaControle")
    , @NamedQuery(name = "OrcadoRealizado.findBySaldoDeposito", query = "SELECT o FROM OrcadoRealizado o WHERE o.saldoDeposito = :saldoDeposito")
    , @NamedQuery(name = "OrcadoRealizado.findByValorDesconciliacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.valorDesconciliacao = :valorDesconciliacao")
    , @NamedQuery(name = "OrcadoRealizado.findBySituacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.situacao = :situacao")
    , @NamedQuery(name = "OrcadoRealizado.findByDataSituacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataSituacao = :dataSituacao")
    , @NamedQuery(name = "OrcadoRealizado.findByDataRetorno", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataRetorno = :dataRetorno")
    , @NamedQuery(name = "OrcadoRealizado.findByFuncionarioResponsavelSituacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.funcionarioResponsavelSituacao = :funcionarioResponsavelSituacao")
    , @NamedQuery(name = "OrcadoRealizado.findByFuncionarioAtual", query = "SELECT o FROM OrcadoRealizado o WHERE o.funcionarioAtual = :funcionarioAtual")
    , @NamedQuery(name = "OrcadoRealizado.findByNomeTratamento", query = "SELECT o FROM OrcadoRealizado o WHERE o.nomeTratamento = :nomeTratamento")
    , @NamedQuery(name = "OrcadoRealizado.findByAvocado", query = "SELECT o FROM OrcadoRealizado o WHERE o.avocado = :avocado")
    , @NamedQuery(name = "OrcadoRealizado.findByDataAvocacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataAvocacao = :dataAvocacao")
    , @NamedQuery(name = "OrcadoRealizado.findByDataDesconciliacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataDesconciliacao = :dataDesconciliacao")
    , @NamedQuery(name = "OrcadoRealizado.findByDiasDesconciliado", query = "SELECT o FROM OrcadoRealizado o WHERE o.diasDesconciliado = :diasDesconciliado")
    , @NamedQuery(name = "OrcadoRealizado.findByAutor", query = "SELECT o FROM OrcadoRealizado o WHERE o.autor = :autor")
    , @NamedQuery(name = "OrcadoRealizado.findByReu", query = "SELECT o FROM OrcadoRealizado o WHERE o.reu = :reu")
    , @NamedQuery(name = "OrcadoRealizado.findByDataEntradaBd", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataEntradaBd = :dataEntradaBd")
    , @NamedQuery(name = "OrcadoRealizado.findByCodigoSituacao", query = "SELECT o FROM OrcadoRealizado o WHERE o.codigoSituacao = :codigoSituacao")
    , @NamedQuery(name = "OrcadoRealizado.findByCodigoTratamento", query = "SELECT o FROM OrcadoRealizado o WHERE o.codigoTratamento = :codigoTratamento")
    , @NamedQuery(name = "OrcadoRealizado.findByNaturezaNpj", query = "SELECT o FROM OrcadoRealizado o WHERE o.naturezaNpj = :naturezaNpj")
    , @NamedQuery(name = "OrcadoRealizado.findByAssunto", query = "SELECT o FROM OrcadoRealizado o WHERE o.assunto = :assunto")
    , @NamedQuery(name = "OrcadoRealizado.findByMateria", query = "SELECT o FROM OrcadoRealizado o WHERE o.materia = :materia")
    , @NamedQuery(name = "OrcadoRealizado.findByObsLivre", query = "SELECT o FROM OrcadoRealizado o WHERE o.obsLivre = :obsLivre")
    , @NamedQuery(name = "OrcadoRealizado.findByDataPrimeiroTratamento", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataPrimeiroTratamento = :dataPrimeiroTratamento")
    , @NamedQuery(name = "OrcadoRealizado.findByTratadoPrazo", query = "SELECT o FROM OrcadoRealizado o WHERE o.tratadoPrazo = :tratadoPrazo")
    , @NamedQuery(name = "OrcadoRealizado.findByDataRetornoAgencia", query = "SELECT o FROM OrcadoRealizado o WHERE o.dataRetornoAgencia = :dataRetornoAgencia")
    , @NamedQuery(name = "OrcadoRealizado.findById", query = "SELECT o FROM OrcadoRealizado o WHERE o.id = :id")})
public class OrcadoRealizado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CODIGO_DESCONCILIACAO")
    private int codigoDesconciliacao;
    @Column(name = "NPJ")
    private String npj;
    @Column(name = "VARIACAO_NPJ")
    private Integer variacaoNpj;
    @Column(name = "CONTA_CONTROLE")
    private String contaControle;
    @Column(name = "CONTA_DEPOSITARIA")
    private String contaDepositaria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_CONTA_CONTROLE")
    private Double saldoContaControle;
    @Column(name = "SALDO_DEPOSITO")
    private Double saldoDeposito;
    @Column(name = "VALOR_DESCONCILIACAO")
    private Double valorDesconciliacao;
    @Column(name = "SITUACAO")
    private String situacao;
    @Column(name = "DATA_SITUACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSituacao;
    @Column(name = "DATA_RETORNO")
    @Temporal(TemporalType.DATE)
    private Date dataRetorno;
    @Column(name = "FUNCIONARIO_RESPONSAVEL_SITUACAO")
    private String funcionarioResponsavelSituacao;
    @Column(name = "FUNCIONARIO_ATUAL")
    private String funcionarioAtual;
    @Column(name = "NOME_TRATAMENTO")
    private String nomeTratamento;
    @Column(name = "AVOCADO")
    private String avocado;
    @Column(name = "DATA_AVOCACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAvocacao;
    @Column(name = "DATA_DESCONCILIACAO")
    @Temporal(TemporalType.DATE)
    private Date dataDesconciliacao;
    @Column(name = "DIAS_DESCONCILIADO")
    private Integer diasDesconciliado;
    @Column(name = "AUTOR")
    private String autor;
    @Column(name = "REU")
    private String reu;
    @Column(name = "DATA_ENTRADA_BD")
    @Temporal(TemporalType.DATE)
    private Date dataEntradaBd;
    @Basic(optional = false)
    @Column(name = "CODIGO_SITUACAO")
    private int codigoSituacao;
    @Basic(optional = false)
    @Column(name = "CODIGO_TRATAMENTO")
    private int codigoTratamento;
    @Column(name = "NATUREZA_NPJ")
    private String naturezaNpj;
    @Column(name = "ASSUNTO")
    private String assunto;
    @Column(name = "MATERIA")
    private String materia;
    @Column(name = "OBS_LIVRE")
    private String obsLivre;
    @Column(name = "DATA_PRIMEIRO_TRATAMENTO")
    @Temporal(TemporalType.DATE)
    private Date dataPrimeiroTratamento;
    @Column(name = "TRATADO_PRAZO")
    private String tratadoPrazo;
    @Column(name = "DATA_RETORNO_AGENCIA")
    @Temporal(TemporalType.DATE)
    private Date dataRetornoAgencia;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public OrcadoRealizado() {
    }

    public OrcadoRealizado(Integer id) {
        this.id = id;
    }

    public OrcadoRealizado(Integer id, int codigoDesconciliacao, int codigoSituacao, int codigoTratamento) {
        this.id = id;
        this.codigoDesconciliacao = codigoDesconciliacao;
        this.codigoSituacao = codigoSituacao;
        this.codigoTratamento = codigoTratamento;
    }

    public int getCodigoDesconciliacao() {
        return codigoDesconciliacao;
    }

    public void setCodigoDesconciliacao(int codigoDesconciliacao) {
        this.codigoDesconciliacao = codigoDesconciliacao;
    }

    public String getNpj() {
        return npj;
    }

    public void setNpj(String npj) {
        this.npj = npj;
    }

    public Integer getVariacaoNpj() {
        return variacaoNpj;
    }

    public void setVariacaoNpj(Integer variacaoNpj) {
        this.variacaoNpj = variacaoNpj;
    }

    public String getContaControle() {
        return contaControle;
    }

    public void setContaControle(String contaControle) {
        this.contaControle = contaControle;
    }

    public String getContaDepositaria() {
        return contaDepositaria;
    }

    public void setContaDepositaria(String contaDepositaria) {
        this.contaDepositaria = contaDepositaria;
    }

    public Double getSaldoContaControle() {
        return saldoContaControle;
    }

    public void setSaldoContaControle(Double saldoContaControle) {
        this.saldoContaControle = saldoContaControle;
    }

    public Double getSaldoDeposito() {
        return saldoDeposito;
    }

    public void setSaldoDeposito(Double saldoDeposito) {
        this.saldoDeposito = saldoDeposito;
    }

    public Double getValorDesconciliacao() {
        return valorDesconciliacao;
    }

    public void setValorDesconciliacao(Double valorDesconciliacao) {
        this.valorDesconciliacao = valorDesconciliacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDataSituacao() {
        return dataSituacao;
    }

    public void setDataSituacao(Date dataSituacao) {
        this.dataSituacao = dataSituacao;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getFuncionarioResponsavelSituacao() {
        return funcionarioResponsavelSituacao;
    }

    public void setFuncionarioResponsavelSituacao(String funcionarioResponsavelSituacao) {
        this.funcionarioResponsavelSituacao = funcionarioResponsavelSituacao;
    }

    public String getFuncionarioAtual() {
        return funcionarioAtual;
    }

    public void setFuncionarioAtual(String funcionarioAtual) {
        this.funcionarioAtual = funcionarioAtual;
    }

    public String getNomeTratamento() {
        return nomeTratamento;
    }

    public void setNomeTratamento(String nomeTratamento) {
        this.nomeTratamento = nomeTratamento;
    }

    public String getAvocado() {
        return avocado;
    }

    public void setAvocado(String avocado) {
        this.avocado = avocado;
    }

    public Date getDataAvocacao() {
        return dataAvocacao;
    }

    public void setDataAvocacao(Date dataAvocacao) {
        this.dataAvocacao = dataAvocacao;
    }

    public Date getDataDesconciliacao() {
        return dataDesconciliacao;
    }

    public void setDataDesconciliacao(Date dataDesconciliacao) {
        this.dataDesconciliacao = dataDesconciliacao;
    }

    public Integer getDiasDesconciliado() {
        return diasDesconciliado;
    }

    public void setDiasDesconciliado(Integer diasDesconciliado) {
        this.diasDesconciliado = diasDesconciliado;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getReu() {
        return reu;
    }

    public void setReu(String reu) {
        this.reu = reu;
    }

    public Date getDataEntradaBd() {
        return dataEntradaBd;
    }

    public void setDataEntradaBd(Date dataEntradaBd) {
        this.dataEntradaBd = dataEntradaBd;
    }

    public int getCodigoSituacao() {
        return codigoSituacao;
    }

    public void setCodigoSituacao(int codigoSituacao) {
        this.codigoSituacao = codigoSituacao;
    }

    public int getCodigoTratamento() {
        return codigoTratamento;
    }

    public void setCodigoTratamento(int codigoTratamento) {
        this.codigoTratamento = codigoTratamento;
    }

    public String getNaturezaNpj() {
        return naturezaNpj;
    }

    public void setNaturezaNpj(String naturezaNpj) {
        this.naturezaNpj = naturezaNpj;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getObsLivre() {
        return obsLivre;
    }

    public void setObsLivre(String obsLivre) {
        this.obsLivre = obsLivre;
    }

    public Date getDataPrimeiroTratamento() {
        return dataPrimeiroTratamento;
    }

    public void setDataPrimeiroTratamento(Date dataPrimeiroTratamento) {
        this.dataPrimeiroTratamento = dataPrimeiroTratamento;
    }

    public String getTratadoPrazo() {
        return tratadoPrazo;
    }

    public void setTratadoPrazo(String tratadoPrazo) {
        this.tratadoPrazo = tratadoPrazo;
    }

    public Date getDataRetornoAgencia() {
        return dataRetornoAgencia;
    }

    public void setDataRetornoAgencia(Date dataRetornoAgencia) {
        this.dataRetornoAgencia = dataRetornoAgencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrcadoRealizado)) {
            return false;
        }
        OrcadoRealizado other = (OrcadoRealizado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.intranet.cenopservicoscwb.entity.OrcadoRealizado[ id=" + id + " ]";
    }
    
}
