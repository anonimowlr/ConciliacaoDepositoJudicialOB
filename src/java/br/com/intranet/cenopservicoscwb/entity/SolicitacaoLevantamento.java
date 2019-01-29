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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author f5078775
 */
@Entity
@Table(name = "tb_solicitacao_levantamento_ob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitacaoLevantamento.findAll", query = "SELECT s FROM SolicitacaoLevantamento s")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByCodigo", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByNpj", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.npj = :npj")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByVariacaoNpj", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.variacaoNpj = :variacaoNpj")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByOficio", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.oficio = :oficio")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByDataOficio", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.dataOficio = :dataOficio")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByHistorico", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.historico = :historico")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByValorSolicitacao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.valorSolicitacao = :valorSolicitacao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByEnderecoDocumento1", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.enderecoDocumento1 = :enderecoDocumento1")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByAdversoBb", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.adversoBb = :adversoBb")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByContaJudicialOb", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.contaJudicialOb = :contaJudicialOb")
    , @NamedQuery(name = "SolicitacaoLevantamento.findBySaldo", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.saldo = :saldo")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByResponsavelSolicitacaoInclusao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.responsavelSolicitacaoInclusao = :responsavelSolicitacaoInclusao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByDataSolicitacaoInclusao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.dataSolicitacaoInclusao = :dataSolicitacaoInclusao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByResponsavelInclusao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.responsavelInclusao = :responsavelInclusao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByDataInclusao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.dataInclusao = :dataInclusao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByObs", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.obs = :obs")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByContaJudicial", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.contaJudicial = :contaJudicial")
    , @NamedQuery(name = "SolicitacaoLevantamento.findBySaldoContabil", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.saldoContabil = :saldoContabil")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByEnderecoDocumento2", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.enderecoDocumento2 = :enderecoDocumento2")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByLevantador", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.levantador = :levantador")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByTipoLevantamento", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.tipoLevantamento = :tipoLevantamento")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByTipoDestinacao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.tipoDestinacao = :tipoDestinacao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByEspecificaDestinacao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.especificaDestinacao = :especificaDestinacao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByBeneficiario", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.beneficiario = :beneficiario")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByCpfBeneficiario", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.cpfBeneficiario = :cpfBeneficiario")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByIdSituacao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.idSituacao = :idSituacao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findBySaldoColetadoPortal", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.saldoColetadoPortal = :saldoColetadoPortal")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByObsDespacho", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.obsDespacho = :obsDespacho")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByObsContabilizacao", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.obsContabilizacao = :obsContabilizacao")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByObsIncluirParte", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.obsIncluirParte = :obsIncluirParte")
    , @NamedQuery(name = "SolicitacaoLevantamento.findByIncluirParte", query = "SELECT s FROM SolicitacaoLevantamento s WHERE s.incluirParte = :incluirParte")})
public class SolicitacaoLevantamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NPJ")
    private String npj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VARIACAO_NPJ")
    private int variacaoNpj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "OFICIO")
    private String oficio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "DATA_OFICIO")
    private String dataOficio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "HISTORICO")
    private String historico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "VALOR_SOLICITACAO")
    private String valorSolicitacao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 99)
    @Column(name = "ENDERECO_DOCUMENTO1")
    private String enderecoDocumento1;
    @Size(max = 250)
    @Column(name = "ADVERSO_BB")
    private String adversoBb;
    @Size(max = 45)
    @Column(name = "CONTA_JUDICIAL_OB")
    private String contaJudicialOb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SALDO")
    private String saldo;
    @Size(max = 45)
    @Column(name = "RESPONSAVEL_SOLICITACAO_INCLUSAO")
    private String responsavelSolicitacaoInclusao;
    @Column(name = "DATA_SOLICITACAO_INCLUSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacaoInclusao;
    @Size(max = 45)
    @Column(name = "RESPONSAVEL_INCLUSAO")
    private String responsavelInclusao;
    @Size(max = 45)
    @Column(name = "DATA_INCLUSAO")
    private String dataInclusao;
    @Size(max = 250)
    @Column(name = "OBS")
    private String obs;
    @Size(max = 45)
    @Column(name = "CONTA_JUDICIAL")
    private String contaJudicial;
    @Size(max = 45)
    @Column(name = "SALDO_CONTABIL")
    private String saldoContabil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 99)
    @Column(name = "ENDERECO_DOCUMENTO2")
    private String enderecoDocumento2;
    @Size(max = 45)
    @Column(name = "LEVANTADOR")
    private String levantador;
    @Size(max = 45)
    @Column(name = "TIPO_LEVANTAMENTO")
    private String tipoLevantamento;
    @Size(max = 100)
    @Column(name = "TIPO_DESTINACAO")
    private String tipoDestinacao;
    @Size(max = 100)
    @Column(name = "ESPECIFICA_DESTINACAO")
    private String especificaDestinacao;
    @Size(max = 200)
    @Column(name = "BENEFICIARIO")
    private String beneficiario;
    @Size(max = 45)
    @Column(name = "CPF_BENEFICIARIO")
    private String cpfBeneficiario;
    @Size(max = 200)
    @Column(name = "ID_SITUACAO")
    private String idSituacao;
    @Size(max = 50)
    @Column(name = "SALDO_COLETADO_PORTAL")
    private String saldoColetadoPortal;
    @Size(max = 200)
    @Column(name = "OBS_DESPACHO")
    private String obsDespacho;
    @Size(max = 200)
    @Column(name = "OBS_CONTABILIZACAO")
    private String obsContabilizacao;
    @Size(max = 200)
    @Column(name = "OBS_INCLUIR_PARTE")
    private String obsIncluirParte;
    @Size(max = 45)
    @Column(name = "INCLUIR_PARTE")
    private String incluirParte;
    @JoinColumn(name = "id_desconciliacao", referencedColumnName = "CODIGO_DESCONCILIACAO")
    @ManyToOne
    private DesconciliacaoOB idDesconciliacao;

    public SolicitacaoLevantamento() {
    }

    public SolicitacaoLevantamento(Integer codigo) {
        this.codigo = codigo;
    }

    public SolicitacaoLevantamento(Integer codigo, String npj, int variacaoNpj, String oficio, String dataOficio, String historico, String valorSolicitacao, String enderecoDocumento1, String saldo, String enderecoDocumento2) {
        this.codigo = codigo;
        this.npj = npj;
        this.variacaoNpj = variacaoNpj;
        this.oficio = oficio;
        this.dataOficio = dataOficio;
        this.historico = historico;
        this.valorSolicitacao = valorSolicitacao;
        this.enderecoDocumento1 = enderecoDocumento1;
        this.saldo = saldo;
        this.enderecoDocumento2 = enderecoDocumento2;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNpj() {
        return npj;
    }

    public void setNpj(String npj) {
        this.npj = npj;
    }

    public int getVariacaoNpj() {
        return variacaoNpj;
    }

    public void setVariacaoNpj(int variacaoNpj) {
        this.variacaoNpj = variacaoNpj;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getDataOficio() {
        return dataOficio;
    }

    public void setDataOficio(String dataOficio) {
        this.dataOficio = dataOficio;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getValorSolicitacao() {
        return valorSolicitacao;
    }

    public void setValorSolicitacao(String valorSolicitacao) {
        this.valorSolicitacao = valorSolicitacao;
    }

    public String getEnderecoDocumento1() {
        return enderecoDocumento1;
    }

    public void setEnderecoDocumento1(String enderecoDocumento1) {
        this.enderecoDocumento1 = enderecoDocumento1;
    }

    public String getAdversoBb() {
        return adversoBb;
    }

    public void setAdversoBb(String adversoBb) {
        this.adversoBb = adversoBb;
    }

    public String getContaJudicialOb() {
        return contaJudicialOb;
    }

    public void setContaJudicialOb(String contaJudicialOb) {
        this.contaJudicialOb = contaJudicialOb;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getResponsavelSolicitacaoInclusao() {
        return responsavelSolicitacaoInclusao;
    }

    public void setResponsavelSolicitacaoInclusao(String responsavelSolicitacaoInclusao) {
        this.responsavelSolicitacaoInclusao = responsavelSolicitacaoInclusao;
    }

    public Date getDataSolicitacaoInclusao() {
        return dataSolicitacaoInclusao;
    }

    public void setDataSolicitacaoInclusao(Date dataSolicitacaoInclusao) {
        this.dataSolicitacaoInclusao = dataSolicitacaoInclusao;
    }

    public String getResponsavelInclusao() {
        return responsavelInclusao;
    }

    public void setResponsavelInclusao(String responsavelInclusao) {
        this.responsavelInclusao = responsavelInclusao;
    }

    public String getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(String dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getContaJudicial() {
        return contaJudicial;
    }

    public void setContaJudicial(String contaJudicial) {
        this.contaJudicial = contaJudicial;
    }

    public String getSaldoContabil() {
        return saldoContabil;
    }

    public void setSaldoContabil(String saldoContabil) {
        this.saldoContabil = saldoContabil;
    }

    public String getEnderecoDocumento2() {
        return enderecoDocumento2;
    }

    public void setEnderecoDocumento2(String enderecoDocumento2) {
        this.enderecoDocumento2 = enderecoDocumento2;
    }

    public String getLevantador() {
        return levantador;
    }

    public void setLevantador(String levantador) {
        this.levantador = levantador;
    }

    public String getTipoLevantamento() {
        return tipoLevantamento;
    }

    public void setTipoLevantamento(String tipoLevantamento) {
        this.tipoLevantamento = tipoLevantamento;
    }

    public String getTipoDestinacao() {
        return tipoDestinacao;
    }

    public void setTipoDestinacao(String tipoDestinacao) {
        this.tipoDestinacao = tipoDestinacao;
    }

    public String getEspecificaDestinacao() {
        return especificaDestinacao;
    }

    public void setEspecificaDestinacao(String especificaDestinacao) {
        this.especificaDestinacao = especificaDestinacao;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCpfBeneficiario() {
        return cpfBeneficiario;
    }

    public void setCpfBeneficiario(String cpfBeneficiario) {
        this.cpfBeneficiario = cpfBeneficiario;
    }

    public String getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(String idSituacao) {
        this.idSituacao = idSituacao;
    }

    public String getSaldoColetadoPortal() {
        return saldoColetadoPortal;
    }

    public void setSaldoColetadoPortal(String saldoColetadoPortal) {
        this.saldoColetadoPortal = saldoColetadoPortal;
    }

    public String getObsDespacho() {
        return obsDespacho;
    }

    public void setObsDespacho(String obsDespacho) {
        this.obsDespacho = obsDespacho;
    }

    public String getObsContabilizacao() {
        return obsContabilizacao;
    }

    public void setObsContabilizacao(String obsContabilizacao) {
        this.obsContabilizacao = obsContabilizacao;
    }

    public String getObsIncluirParte() {
        return obsIncluirParte;
    }

    public void setObsIncluirParte(String obsIncluirParte) {
        this.obsIncluirParte = obsIncluirParte;
    }

    public String getIncluirParte() {
        return incluirParte;
    }

    public void setIncluirParte(String incluirParte) {
        this.incluirParte = incluirParte;
    }

    public DesconciliacaoOB getIdDesconciliacao() {
        return idDesconciliacao;
    }

    public void setIdDesconciliacao(DesconciliacaoOB idDesconciliacao) {
        this.idDesconciliacao = idDesconciliacao;
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
        if (!(object instanceof SolicitacaoLevantamento)) {
            return false;
        }
        SolicitacaoLevantamento other = (SolicitacaoLevantamento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.intranet.cenopservicoscwb.entity.SolicitacaoLevantamento[ codigo=" + codigo + " ]";
    }
    
}
