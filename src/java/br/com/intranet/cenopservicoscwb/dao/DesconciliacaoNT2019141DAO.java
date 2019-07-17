/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.intranet.cenopservicoscwb.dao;

import br.com.intranet.cenopservicoscwb.bean.util.ErroSistema;
import br.com.intranet.cenopservicoscwb.bean.util.Utils;
import br.com.intranet.cenopservicoscwb.connectionfactory.ConnectionFactory;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import br.com.intranet.cenopservicoscwb.entity.Funcionario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC_LENOVO
 */
public class DesconciliacaoNT2019141DAO implements CrudDAO<DesconciliacaoOB> {

    @Override
    public void salvar(DesconciliacaoOB entidade) throws ErroSistema {
       GrupoStatusDAO statusDAO = new GrupoStatusDAO();
        GrupoTratamentoDAO tratamentoDAO = new GrupoTratamentoDAO();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            if (entidade.getDataPrimeiroTratamento() == null) {
                sql = "UPDATE rejud_ob.tb_desconciliacao_ob_paj set NPJ=?, CONTA_CONTROLE=? , CONTA_DEPOSITARIA=? , SALDO_CONTA_CONTROLE=? , SALDO_DEPOSITO=?"
                        + " , VALOR_DESCONCILIACAO=? , SITUACAO=? , DATA_SITUACAO=? , FUNCIONARIO_RESPONSAVEL_SITUACAO=?"
                        + " , NOME_TRATAMENTO=? , AVOCADO=?, DATA_AVOCACAO=?, DATA_DESCONCILIACAO=?, OBS_LIVRE = ?, DATA_PRIMEIRO_TRATAMENTO = ?, TRATADO_PRAZO = ?, CODIGO_SITUACAO = ?,CODIGO_TRATAMENTO=?  where CODIGO_DESCONCILIACAO=?";
                stmt = con.prepareStatement(sql);
                try {
                    stmt.setDate(15, Utils.getDataAtualFormatoMysql());
                } catch (Exception ex) {
                    throw new ErroSistema("Erro ao tentar verificar data atual", ex);
                }
                if (entidade.getDiasDesconciliado() <= 30) {
                    stmt.setString(16, "SIM");
                } else {
                    stmt.setString(16, "NAO");
                }
                stmt.setInt(17, entidade.getCodigoSituacao());
                stmt.setInt(18, entidade.getCodigoTratamento());
                stmt.setInt(19, entidade.getCodigoDesconciliacao());

            } else {
                sql = "UPDATE rejud_ob.tb_desconciliacao_ob_paj set NPJ=?, CONTA_CONTROLE=? , CONTA_DEPOSITARIA=? , SALDO_CONTA_CONTROLE=? , SALDO_DEPOSITO=?"
                        + " , VALOR_DESCONCILIACAO=? , SITUACAO=? , DATA_SITUACAO=? , FUNCIONARIO_RESPONSAVEL_SITUACAO=?"
                        + " , NOME_TRATAMENTO=? , AVOCADO=?, DATA_AVOCACAO=?, DATA_DESCONCILIACAO=?, OBS_LIVRE = ?,CODIGO_SITUACAO=?,CODIGO_TRATAMENTO=?,TRATADO_PRAZO = ? where CODIGO_DESCONCILIACAO=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(15, entidade.getCodigoSituacao());
                stmt.setInt(16, entidade.getCodigoTratamento());
                if (entidade.getDiasDesconciliado() <= 30) {
                    stmt.setString(17, "SIM");
                } else {
                    stmt.setString(17, "NAO");
                }

                stmt.setInt(18, entidade.getCodigoDesconciliacao());

            }

            stmt.setString(1, entidade.getNpj());
            stmt.setString(2, entidade.getContaControle());
            stmt.setString(3, entidade.getContaDepositaria());
            stmt.setDouble(4, entidade.getSaldoContaControle());
            stmt.setDouble(5, entidade.getSaldoDeposito());

            stmt.setDouble(6, entidade.getValorDesconciliacao());

            stmt.setString(7, statusDAO.buscarStatus(entidade.getCodigoSituacao()));
            entidade.setSituacao(statusDAO.buscarStatus(entidade.getCodigoSituacao()));
            try {
                stmt.setString(8, Utils.getDataHoraAtualMysql());
                entidade.setDataSituacao(Utils.getDataAtualFormatoMysql());
            } catch (Exception ex) {
                throw new ErroSistema("Erro ao tentar verificar data atual", ex);
            }
            stmt.setString(9, usuario.getChave());
            stmt.setString(10, tratamentoDAO.buscarTratamento(entidade.getCodigoTratamento()));
            entidade.setNomeTratamento(tratamentoDAO.buscarTratamento(entidade.getCodigoTratamento()));
            stmt.setString(11, "");
            stmt.setDate(12, (Date) entidade.getDataAvocacao());
            stmt.setDate(13, (Date) entidade.getDataDesconciliacao());
            stmt.setString(14, entidade.getObsLivre());

            stmt.executeUpdate();
            salvarHistorico(entidade);
            gerarOrcado(entidade);

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }


    }

    public void gerarOrcado(DesconciliacaoOB entidade) throws ErroSistema {

        GrupoStatusDAO statusDAO = new GrupoStatusDAO();
        GrupoTratamentoDAO tratamentoDAO = new GrupoTratamentoDAO();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");
        if (entidade.getTratadoPrazo() != null) {
            return;
        }

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "INSERT INTO tb_orcado_realizado_desconciliacao_ob_paj (CODIGO_DESCONCILIACAO,NPJ,VARIACAO_NPJ,SITUACAO,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,"
                    + "DATA_DESCONCILIACAO,TRATADO_PRAZO,SALDO_DEPOSITO,DATA_SITUACAO,DATA_RETORNO_AGENCIA,"
                    + "FUNCIONARIO_RESPONSAVEL_SITUACAO,FUNCIONARIO_ATUAL,NOME_TRATAMENTO,"
                    + "DIAS_DESCONCILIADO,DATA_ENTRADA_BD,DATA_PRIMEIRO_TRATAMENTO,CODIGO_SITUACAO,CODIGO_TRATAMENTO)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, entidade.getCodigoDesconciliacao());
            stmt.setString(2, entidade.getNpj());
            stmt.setInt(3, entidade.getVariacaoNpj());
            stmt.setString(4, entidade.getSituacao());
            stmt.setString(5, entidade.getAutor());
            stmt.setString(6, entidade.getContaDepositaria());
            stmt.setDouble(7, entidade.getValorDesconciliacao());

            stmt.setDate(8, (Date) entidade.getDataDesconciliacao());
            if (entidade.getDiasDesconciliado() <= 30) {
                stmt.setString(9, "SIM");
            } else {
                stmt.setString(9, "NAO");
            }

            stmt.setDouble(10, entidade.getSaldoDeposito());
            stmt.setDate(11, (Date) entidade.getDataSituacao());
            stmt.setDate(12, (Date) entidade.getDataRetornoAgencia());
            stmt.setString(13, usuario.getChave());
            stmt.setString(14, entidade.getFuncionarioAtual());
            stmt.setString(15, entidade.getNomeTratamento());
            stmt.setInt(16, entidade.getDiasDesconciliado());

            stmt.setDate(17, (Date) entidade.getDataEntradaBd());

            
            
            if(entidade.getDataPrimeiroTratamento() == null){
                try {
                    entidade.setDataPrimeiroTratamento(Utils.getDataAtualFormatoMysql());
                } catch (Exception ex) {
                   throw new ErroSistema("Erro ao tentar salvar data atual na tabela de orcado", ex);
                }
            }
            
            
            
            
            stmt.setDate(18, (Date) entidade.getDataPrimeiroTratamento());
            stmt.setInt(19,  entidade.getCodigoSituacao());
            stmt.setInt(20, entidade.getCodigoTratamento());

            stmt.executeUpdate();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar orcado", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }
    }

    public void inserirNovaDesconciliacao(DesconciliacaoOB entidade) throws ErroSistema {

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "INSERT INTO rejud_ob.tb_desconciliacao_ob_paj (NPJ,VARIACAO_NPJ,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO) VALUES (?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, entidade.getNpj());
            stmt.setInt(2, entidade.getVariacaoNpj());
            stmt.setString(3, entidade.getAutor());
            stmt.setString(4, entidade.getContaDepositaria());
            stmt.setDouble(5, entidade.getValorDesconciliacao());

            stmt.setString(6, entidade.getDataDesconciliacao().toString());

            stmt.executeUpdate();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

    public void criarTabelaTemporariaDesconciliacaoDjo(String sql) throws ErroSistema {

        try {

            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            stmt = con.prepareStatement(sql);

            stmt.execute();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

    public void inserirRegistroNovoTbTemporaria(DesconciliacaoOB entidade) throws ErroSistema {

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "INSERT INTO tb_temporaria_desconciliacao_ob_paj (NPJ,VARIACAO_NPJ,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO) VALUES (?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, entidade.getNpj());
            stmt.setInt(2, entidade.getVariacaoNpj());
            stmt.setString(3, entidade.getAutor());
            stmt.setString(4, entidade.getContaDepositaria());
            stmt.setDouble(5, entidade.getValorDesconciliacao());

            stmt.setString(6, entidade.getDataDesconciliacao().toString());

            stmt.executeUpdate();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

    @Override
    public void deletar(DesconciliacaoOB entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DesconciliacaoOB> buscar() throws ErroSistema {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        DesconciliacaoOB desconciliacao = null;
        try {
            Connection con = ConnectionFactory.conectar("rejud_ob");
            String sql = "SELECT * FROM rejud_ob.tb_desconciliacao_ob_paj where ((NOME_TRATAMENTO = '' OR NOME_TRATAMENTO is null) AND (AVOCADO = '' OR AVOCADO IS NULL OR( AVOCADO = 'SIM' AND FUNCIONARIO_ATUAL =  '" + usuario.getChave() + "' ) ) AND(DATA_ENTRADA_BD IS NOT NULL) AND (SITUACAO = '' OR SITUACAO IS NULL  ) AND  (ORIGEM = 'Nota 2019/0141' )  AND (AVOCADO = '' OR AVOCADO IS NULL OR( AVOCADO = 'SIM' AND FUNCIONARIO_ATUAL =  '" + usuario.getChave() + "' ) ) ) ORDER BY  DATA_ENTRADA_BD ASC,VALOR_DESCONCILIACAO DESC LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<DesconciliacaoOB> desconciliacoes = new ArrayList<>();

            while (rs.next()) {

                desconciliacao = new DesconciliacaoOB();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setNpj(rs.getString("NPJ"));
                // Utils.copyToClipBord(rs.getString("NPJ"));
                desconciliacao.setVariacaoNpj(rs.getInt("VARIACAO_NPJ"));
                desconciliacao.setContaControle(rs.getString("CONTA_CONTROLE"));
                desconciliacao.setContaDepositaria(rs.getString("CONTA_DEPOSITARIA"));
                desconciliacao.setSaldoContaControle(rs.getDouble("SALDO_CONTA_CONTROLE"));
                desconciliacao.setSaldoDeposito(rs.getDouble("SALDO_DEPOSITO"));
                desconciliacao.setValorDesconciliacao(rs.getDouble("VALOR_DESCONCILIACAO"));
                desconciliacao.setSituacao(rs.getString("SITUACAO"));
                desconciliacao.setDataSituacao(rs.getDate("DATA_SITUACAO"));
                desconciliacao.setFuncionarioResponsavelSituacao(rs.getString("FUNCIONARIO_RESPONSAVEL_SITUACAO"));
                desconciliacao.setFuncionarioAtual(rs.getString("FUNCIONARIO_ATUAL"));
                desconciliacao.setNomeTratamento(rs.getString("NOME_TRATAMENTO"));
                desconciliacao.setAvocado(rs.getString("AVOCADO"));
                desconciliacao.setDataAvocacao(rs.getDate("DATA_AVOCACAO"));
                desconciliacao.setDataDesconciliacao(rs.getDate("DATA_DESCONCILIACAO"));
                desconciliacao.setDiasDesconciliado(rs.getInt("DIAS_DESCONCILIADO"));
                desconciliacao.setDataEntradaBd(rs.getDate("DATA_ENTRADA_BD"));
                desconciliacao.setObsLivre(rs.getString("OBS_LIVRE"));
                desconciliacao.setAutor(rs.getString("AUTOR"));
                desconciliacao.setMateria(rs.getString("MATERIA"));
                desconciliacao.setAssunto(rs.getString("ASSUNTO"));
                desconciliacao.setDataPrimeiroTratamento(rs.getDate("DATA_PRIMEIRO_TRATAMENTO"));
                desconciliacao.setTratadoPrazo(rs.getString("TRATADO_PRAZO"));
                desconciliacao.setDataRetornoAgencia(rs.getDate("DATA_RETORNO_AGENCIA"));
                desconciliacao.setSituacaoNpj(rs.getString("SITUACAO_NPJ"));
                desconciliacao.setAdvogadoBb(rs.getString("ADVOGADO_BB"));
                desconciliacao.setDataColetaSituacaoNpj(rs.getDate("DATA_COLETA_SITUACAO_NPJ"));
                desconciliacao.setOrigem(rs.getString("ORIGEM"));
                desconciliacao.setNaturezaNpj(rs.getString("NATUREZA_NPJ"));
                
                desconciliacoes.add(desconciliacao);

            }

            if(desconciliacao != null){
              avocar(desconciliacao);  
           } 
            
            return desconciliacoes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar dados", ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    private void salvarHistorico(DesconciliacaoOB entidade) throws ErroSistema {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "INSERT INTO tb_historico_desconciliacao_ob_paj (CODIGO_DESCONCILIACAO,NPJ,VARIACAO_NPJ,CONTA_CONTROLE,CONTA_DEPOSITARIA,SALDO_CONTA_CONTROLE,"
                    + "SALDO_DEPOSITO,VALOR_DESCONCILIACAO,SITUACAO,DATA_SITUACAO,FUNCIONARIO_RESPONSAVEL_SITUACAO,NOME_TRATAMENTO,DATA_DESCONCILIACAO,DIAS_DESCONCILIADO,DATA_ENTRADA_BD,MES_TRATAMENTO,CODIGO_FUNCAO,UOR_LOCALIZACAO,CODIGO_TAREFA"
                    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, entidade.getCodigoDesconciliacao());
            stmt.setString(2, entidade.getNpj());
            stmt.setInt(3, entidade.getVariacaoNpj());
            stmt.setString(4, entidade.getContaControle());
            stmt.setString(5, entidade.getContaDepositaria());
            stmt.setDouble(6, entidade.getSaldoContaControle());
            stmt.setDouble(7, entidade.getSaldoDeposito());

            stmt.setDouble(8, entidade.getValorDesconciliacao());

            stmt.setString(9, entidade.getSituacao());

            stmt.setString(10, Utils.getDataHoraAtualMysql());

            stmt.setString(11, usuario.getChave());
            stmt.setString(12, entidade.getNomeTratamento());
//            stmt.setString(12, usuario.getChave());
//            
//            stmt.setString(14, entidade.getAvocado());
            // stmt.setDate(15, (Date) entidade.getDataAvocacao());
            stmt.setDate(13, (Date) entidade.getDataDesconciliacao());
            stmt.setInt(14, entidade.getDiasDesconciliado());
            stmt.setDate(15, (Date) entidade.getDataEntradaBd());
            stmt.setString(16, Utils.getMesAtual());
            stmt.setInt(17, usuario.getFuncao());
            stmt.setInt(18, usuario.getUORPosicao());
            if ((entidade.getValorDesconciliacao() < 50000)) {

                stmt.setInt(19, 21444);
            } else {
                stmt.setInt(19, 21443);
            }

            stmt.executeUpdate();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar histórico", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

    public void avocar(DesconciliacaoOB entidade) throws ErroSistema {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "UPDATE rejud_ob.tb_desconciliacao_ob_paj set AVOCADO=?, DATA_AVOCACAO=?, FUNCIONARIO_ATUAL=? where CODIGO_DESCONCILIACAO=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(4, entidade.getCodigoDesconciliacao());

            stmt.setString(1, "SIM");
            try {
                stmt.setString(2, Utils.getDataHoraAtualMysql());
            } catch (Exception ex) {
                throw new ErroSistema("Erro ao tentar verificar data atual", ex);
            }
            stmt.setString(3, usuario.getChave());
            entidade.setFuncionarioAtual(usuario.getChave());

            stmt.executeUpdate();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar avocar registro", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }

    }

    @Override
    public Boolean verAvocacao(DesconciliacaoOB entidade) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

        Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");
        int numeroRegistro = 0;

        try {
            Connection con = ConnectionFactory.conectar("rejud_ob");
            String sql = "SELECT * FROM rejud_ob.tb_desconciliacao_ob_paj where (AVOCADO = 'SIM' AND CODIGO_DESCONCILIACAO =? and FUNCIONARIO_ATUAL <> ? )";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, entidade.getCodigoDesconciliacao());
            stmt.setString(2, usuario.getChave());//MUDAR PARA usuario.getChave para produçao
            ResultSet rs = stmt.executeQuery();

            numeroRegistro = contareg(rs);
        } catch (Exception ex) {

            return false;

        }

        if (numeroRegistro > 0) {
            return true;

        } else {
            return false;

        }

    }

    public int contareg(ResultSet rs) throws ErroSistema {

        int numResultados = 0;
        try {
            rs.last();
            numResultados = rs.getRow();
            rs.first();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {

            ConnectionFactory.fecharConexao();

        }
        return numResultados;

    }

    public void apagaRegistrosTabela() throws ErroSistema {

        String sql;

        try {

            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "truncate rejud_ob.tb_temporaria_desconciliacao_ob_paj";
            stmt = con.prepareStatement(sql);

            stmt.execute();

        } catch (Exception ex) {

        } finally {
            ConnectionFactory.fecharConexao();
        }

    }

    @Override
    public Boolean avaliarParaSalvar(DesconciliacaoOB entidade) {
        GrupoStatusDAO statusDAO = new GrupoStatusDAO();
        GrupoTratamentoDAO tratamentoDAO = new GrupoTratamentoDAO();

        String status = null;
        String complemento = null;
        try {
            status = statusDAO.buscarStatus(entidade.getCodigoSituacao());
            complemento = tratamentoDAO.buscarTratamento(entidade.getCodigoTratamento());

        } catch (ErroSistema ex) {
        }

        if (status.contains("EM TRATAMENTO") && complemento == null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!!!", "Status EM TRATAMENTO requer tipo de tratamento"));

            return false;

        } else if (status.contains("INCONSISTENCIA") && complemento == null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!!!", "Status INCONSISTENCIA requer tipo de tratamento"));

            return false;

        }

        return true;

    }

    @Override
    public Boolean compararValor(DesconciliacaoOB entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void priorizar(DesconciliacaoOB entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void liberar(DesconciliacaoOB entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DesconciliacaoOB> buscarPorParametro(DesconciliacaoOB entidade) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DesconciliacaoOB> buscarPorParametro(String parametro) throws ErroSistema {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
