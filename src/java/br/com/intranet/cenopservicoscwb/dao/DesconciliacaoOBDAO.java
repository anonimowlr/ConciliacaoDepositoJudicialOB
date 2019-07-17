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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC_LENOVO
 */
public class DesconciliacaoOBDAO implements CrudDAO<DesconciliacaoOB> {
    
     
    
    

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
                sql = "UPDATE tb_desconciliacao_ob_paj set NPJ=?,VARIACAO_NPJ = ?, CONTA_CONTROLE=? , CONTA_DEPOSITARIA=? , SALDO_CONTA_CONTROLE=? , SALDO_DEPOSITO=?"
                        + " , VALOR_DESCONCILIACAO=? , SITUACAO=? , DATA_SITUACAO=? , FUNCIONARIO_RESPONSAVEL_SITUACAO=?"
                        + " , NOME_TRATAMENTO=? , AVOCADO=?, DATA_AVOCACAO=?, DATA_DESCONCILIACAO=?, OBS_LIVRE = ?, DATA_PRIMEIRO_TRATAMENTO = ?, TRATADO_PRAZO = ?, CODIGO_SITUACAO = ?,CODIGO_TRATAMENTO=?  where CODIGO_DESCONCILIACAO=?";
                stmt = con.prepareStatement(sql);
                try {
                    stmt.setDate(16, Utils.getDataAtualFormatoMysql());
                } catch (Exception ex) {
                    throw new ErroSistema("Erro ao tentar verificar data atual", ex);
                }
                if (entidade.getDiasDesconciliado() <= 30) {
                    stmt.setString(17, "SIM");
                } else {
                    stmt.setString(17, "NAO");
                }
                stmt.setInt(18, entidade.getCodigoSituacao());
                stmt.setInt(19, entidade.getCodigoTratamento());
                stmt.setInt(20, entidade.getCodigoDesconciliacao());

            } else {
                sql = "UPDATE tb_desconciliacao_ob_paj set NPJ=?,VARIACAO_NPJ = ?, CONTA_CONTROLE=? , CONTA_DEPOSITARIA=? , SALDO_CONTA_CONTROLE=? , SALDO_DEPOSITO=?"
                        + " , VALOR_DESCONCILIACAO=? , SITUACAO=? , DATA_SITUACAO=? , FUNCIONARIO_RESPONSAVEL_SITUACAO=?"
                        + " , NOME_TRATAMENTO=? , AVOCADO=?, DATA_AVOCACAO=?, DATA_DESCONCILIACAO=?, OBS_LIVRE = ?,CODIGO_SITUACAO=?,CODIGO_TRATAMENTO=?,TRATADO_PRAZO = ? where CODIGO_DESCONCILIACAO=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(16, entidade.getCodigoSituacao());
                stmt.setInt(17, entidade.getCodigoTratamento());
                if (entidade.getDiasDesconciliado() <= 30) {
                    stmt.setString(18, "SIM");
                } else {
                    stmt.setString(18, "NAO");
                }

                stmt.setInt(19, entidade.getCodigoDesconciliacao());

            }

            stmt.setString(1, entidade.getNpj());
            stmt.setInt(2, entidade.getVariacaoNpj());
           
            stmt.setString(3, entidade.getContaControle());
            stmt.setString(4, entidade.getContaDepositaria());
            stmt.setDouble(5, entidade.getSaldoContaControle());
            stmt.setDouble(6, entidade.getSaldoDeposito());

            stmt.setDouble(7, entidade.getValorDesconciliacao());

            stmt.setString(8, statusDAO.buscarStatus(entidade.getCodigoSituacao()));
            entidade.setSituacao(statusDAO.buscarStatus(entidade.getCodigoSituacao()));
            try {
                stmt.setString(9, Utils.getDataHoraAtualMysql());
                entidade.setDataSituacao(Utils.getDataAtualFormatoMysql());
            } catch (Exception ex) {
                throw new ErroSistema("Erro ao tentar verificar data atual", ex);
            }
            stmt.setString(10, usuario.getChave());
            stmt.setString(11, tratamentoDAO.buscarTratamento(entidade.getCodigoTratamento()));
            entidade.setNomeTratamento(tratamentoDAO.buscarTratamento(entidade.getCodigoTratamento()));
            stmt.setString(12, "");
            stmt.setDate(13, (Date) entidade.getDataAvocacao());
            stmt.setDate(14, (Date) entidade.getDataDesconciliacao());
            stmt.setString(15, entidade.getObsLivre());

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

            sql = "INSERT INTO tb_desconciliacao_ob_paj (NPJ,VARIACAO_NPJ,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO) VALUES (?,?,?,?,?,?)";
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

            sql = "INSERT INTO tb_temporaria_desconciliacao_djo_paj (NPJ,VARIACAO_NPJ,AUTOR,CONTA_DEPOSITARIA,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO) VALUES (?,?,?,?,?,?)";
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
            String sql = "SELECT * FROM rejud_ob.tb_desconciliacao_ob_paj where  (AVOCADO = '' OR AVOCADO IS NULL OR( AVOCADO = 'SIM' AND FUNCIONARIO_ATUAL =  '" + usuario.getChave() + "' ) ) AND (SITUACAO = '' OR SITUACAO IS NULL OR SITUACAO ='INEDITO TRATADO' )  ORDER BY ABS(VALOR_DESCONCILIACAO)DESC LIMIT 1";
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
                desconciliacao.setBancoDepositario(rs.getString("BANCO_DEPOSITARIO"));
                desconciliacao.setIdDesconciliacaoDiris(rs.getString("ID_DESCONCILIACAO_DIRIS"));
               
                desconciliacao.setSituacaoNpj(rs.getString("SITUACAO_NPJ"));
                desconciliacao.setAdvogadoBb(rs.getString("ADVOGADO_BB"));
                desconciliacao.setDataColetaSituacaoNpj(rs.getDate("DATA_COLETA_SITUACAO_NPJ"));
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
            stmt.setDate(15,(Date) entidade.getDataEntradaBd());
            stmt.setString(16,Utils.getMesAtual());
            stmt.setInt(17,usuario.getFuncao());
            stmt.setInt(18,usuario.getUORPosicao());
            if((entidade.getValorDesconciliacao()<50000)){
                
                stmt.setInt(19,21444);
            } else{
                stmt.setInt(19,21443);
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

            sql = "UPDATE tb_desconciliacao_ob_paj set AVOCADO=?, DATA_AVOCACAO=?, FUNCIONARIO_ATUAL=? where NPJ=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(4, entidade.getNpj());

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
            String sql = "SELECT * FROM tb_desconciliacao_ob_paj where (AVOCADO = 'SIM' AND CODIGO_DESCONCILIACAO =? and FUNCIONARIO_ATUAL <> ? )";
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
        } finally{
            

            ConnectionFactory.fecharConexao();

        
        }
        return numResultados;

    } 

    
    
    public void apagaRegistrosTabela() throws ErroSistema {

        String sql;

        try {

            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "truncate rejud_ob.tb_temporaria_desconciliacao_djo_paj";
            stmt = con.prepareStatement(sql);
            
            stmt.execute();

        } catch (Exception ex) {

        } finally{
            ConnectionFactory.fecharConexao();
        }

    }

    @Override
    public Boolean avaliarParaSalvar(DesconciliacaoOB entidade) {
      GrupoStatusDAO statusDAO = new GrupoStatusDAO();
      GrupoTratamentoDAO tratamentoDAO = new GrupoTratamentoDAO();
        
        String status = null;
        String complemento= null;
        try {
            status = statusDAO.buscarStatus(entidade.getCodigoSituacao());
            complemento = tratamentoDAO.buscarTratamento(entidade.getCodigoTratamento());
            
        } catch (ErroSistema ex) {
            Logger.getLogger(DesconciliacaoOBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
          
        
        
        
                if (status.contains("EM TRATAMENTO") &&  complemento == null){
                    
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!!!", "Status EM TRATAMENTO requer tipo de tratamento" ));
                    
                    return false;
                    
                
                
                }else if(status.contains("INCONSISTENCIA") &&  complemento == null){
                    
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!!!", "Status INCONSISTENCIA requer tipo de tratamento" ));
                
                return false;
                
                }
                
                
                return true;
    




    }

    public void atualizacaoDiaria() throws ErroSistema, SQLException {
        
        
        //FacesContext fc = FacesContext.getCurrentInstance();
        //HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);

       //Funcionario usuario = (Funcionario) session.getAttribute("usuarioLogado");
        
        
        
        
        
            Connection con = null; 
            
        
        
         try {
             
             
             
             
             
             con = ConnectionFactory.conectar("rejud_ob");
            
            con.setAutoCommit(false);
            String sql = "drop table if exists rejud_ob.tb_tmp_incremento";
            PreparedStatement stmt = con.prepareStatement(sql);
           stmt.execute();
            
            
             sql = "drop table if exists rejud_ob.tb_tmp_concluidos";
             stmt = con.prepareStatement(sql);
            stmt.execute();
             
             
             sql = "drop table if exists rejud_ob.tb_falso_regularizado";
             stmt = con.prepareStatement(sql);
            stmt.execute();
             
             
             
             sql = "create table if not exists rejud_ob.tb_tmp_incremento as select * from rejud_ob.tb_temporaria_desconciliacao_djo_paj as t1 where not exists (select * from rejud_ob.tb_desconciliacao_ob_paj as t2 where(t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA) )";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             
             
             
             sql = "insert into rejud_ob.tb_desconciliacao_ob_paj (NPJ,VARIACAO_NPJ,CONTA_DEPOSITARIA,AUTOR,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO,DATA_ENTRADA_BD)(select NPJ,VARIACAO_NPJ,CONTA_DEPOSITARIA,AUTOR,VALOR_DESCONCILIACAO,DATA_DESCONCILIACAO,curdate() FROM rejud_ob.tb_tmp_incremento)";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             sql = "create table if not exists rejud_ob.tb_tmp_concluidos as select * from rejud_ob.tb_desconciliacao_ob_paj as t1 where not exists (select * from rejud_ob.tb_temporaria_desconciliacao_djo_paj as t2 where(t1.NPJ = t2.NPJ and t1.CONTA_DEPOSITARIA = t2.CONTA_DEPOSITARIA) )";
             stmt = con.prepareStatement(sql);
            stmt.execute();
            
            
            
             sql = "create table rejud_ob.tb_falso_regularizado as (select t1.NPJ,t1.SITUACAO,t1.CONTA_DEPOSITARIA,t1.VALOR_DESCONCILIACAO,t1.DATA_SITUACAO,t1.DATA_ENTRADA_BD FROM rejud_ob.tb_desconciliacao_ob_paj as t1 inner join rejud_ob.tb_temporaria_desconciliacao_djo_paj as t2 on t1.NPJ = t2.NPJ AND t1.CONTA_DEPOSITARIA =t2.CONTA_DEPOSITARIA AND t1.SITUACAO = 'REGULARIZADO' AND t1.DATA_SITUACAO < subdate(curdate(),interval 1 day) )";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             sql = " update rejud_ob.tb_desconciliacao_ob_paj  as t1 set SITUACAO = NULL, NOME_TRATAMENTO = NULL,DATA_SITUACAO = NULL, OBS_LIVRE = 'Este registro estava marcado como regularizado, foi desmarcado automaticamente devido à persistencia da desconciliação' WHERE  exists (select * from rejud_ob.tb_falso_regularizado as t2 where t1.NPJ=t2.NPJ and t1.CONTA_DEPOSITARIA=t2.CONTA_DEPOSITARIA and t1.SITUACAO ='REGULARIZADO')";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             
             sql = "update rejud_ob.tb_desconciliacao_ob_paj  as t1 set SITUACAO = 'REGULARIZADO', NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA',DATA_SITUACAO = curdate(),OBS_LIVRE = 'Este registro foi anotado como regularizado automaticamente devido nao estar mais na lista de desconciliação do Portal Jurídico',FUNCIONARIO_RESPONSAVEL_SITUACAO = 'F5078775' WHERE  exists (select * from rejud_ob.tb_tmp_concluidos as t2 where t1.NPJ=t2.NPJ and t1.CONTA_DEPOSITARIA=t2.CONTA_DEPOSITARIA and (t1.SITUACAO is null or t1.SITUACAO ='EM TRATAMENTO'))";
             stmt = con.prepareStatement(sql);
             stmt.execute();
            
             
             sql = "INSERT INTO rejud_ob.tb_serie_historica_desconciliacao_dj_paj(QUANTIDADE,VALOR,DATA,VALOR_ABSOLUTO) (select count(CODIGO) AS QUANTIDADE,sum((VALOR_DESCONCILIACAO) ) AS VALOR, curdate(),SUM(ABS(VALOR_DESCONCILIACAO)) AS VLR_ABSOLUTO from rejud_ob.tb_temporaria_desconciliacao_djo_paj)";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             
             sql = "update rejud_ob.tb_desconciliacao_ob_paj as t1 set t1.AVOCADO = NULL  where AVOCADO = 'SIM' AND DATA_AVOCACAO < curdate()";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             sql = "update rejud_ob.tb_desconciliacao_ob_paj as t1 set t1.SITUACAO = NULL, DATA_SITUACAO = NULL  where SITUACAO = 'INEDITO SOLICITADO' AND DATA_SITUACAO < curdate()";
             stmt = con.prepareStatement(sql);
            stmt.execute();
             
             sql = "update rejud_ob.tb_desconciliacao_ob_paj as t1 set DIAS_DESCONCILIADO = datediff(curdate(),DATA_ENTRADA_BD) where DATA_PRIMEIRO_TRATAMENTO IS NULL AND DATA_ENTRADA_BD IS NOT NULL";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             
             sql = "UPDATE rejud_ob.tb_desconciliacao_ob_paj AS t1 set DATA_PRIMEIRO_TRATAMENTO = (t1.DATA_SITUACAO)  where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA' AND DATA_PRIMEIRO_TRATAMENTO IS NULL";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             sql = "UPDATE rejud_ob.tb_desconciliacao_ob_paj as t1 set DIAS_DESCONCILIADO = datediff(DATA_PRIMEIRO_TRATAMENTO,DATA_ENTRADA_BD) where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA'";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             sql = "UPDATE rejud_ob.tb_desconciliacao_ob_paj  as t1 set TRATADO_PRAZO = 'SIM' where (DIAS_DESCONCILIADO  <=30 and DIAS_DESCONCILIADO>=0 OR DIAS_DESCONCILIADO IS NULL)  AND (TRATADO_PRAZO IS NULL OR TRATADO_PRAZO = 'NAO') AND DATA_PRIMEIRO_TRATAMENTO IS NOT NULL AND SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO = 'ANOTAÇÃO AUTOMÁTICA'";
             stmt = con.prepareStatement(sql);
             stmt.execute();
             
             
             con.commit();
             
             
             
             
             
             
             

        } catch (Exception ex) {
            
            
            
               
                    con.rollback();
               
            

        } finally{
             con.setAutoCommit(true);
             ConnectionFactory.fecharConexao();
             
         }
        
        






    }

    @Override
    public Boolean compararValor(DesconciliacaoOB entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    public List<DesconciliacaoOB> buscarRegistroTratatoAutomatico() throws ErroSistema, Exception {
        
        
         DesconciliacaoOB desconciliacao = null;
        try {
            Connection con = ConnectionFactory.conectar("rejud_ob");
            String sql = "SELECT * FROM rejud_ob.tb_desconciliacao_ob_paj where SITUACAO = 'REGULARIZADO' AND NOME_TRATAMENTO='ANOTAÇÃO AUTOMÁTICA' AND DATA_SITUACAO  LIKE '%' '" + Utils.getDataAtualFormatoMysql() + "' '%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<DesconciliacaoOB> desconciliacoes = new ArrayList<>();

            while (rs.next()) {

                 desconciliacao = new DesconciliacaoOB();

                desconciliacao.setCodigoDesconciliacao(rs.getInt("CODIGO_DESCONCILIACAO"));
                desconciliacao.setNpj(rs.getString("NPJ"));
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
                desconciliacoes.add(desconciliacao);
                
                salvarHistoricoRegistroTratadoAutomatico(desconciliacao);


            }

            
            return desconciliacoes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar dados", ex);
        } finally {
            ConnectionFactory.fecharConexao();
        }

    }



     private void salvarHistoricoRegistroTratadoAutomatico(DesconciliacaoOB entidade) throws ErroSistema {

        try {
            String sql;
            PreparedStatement stmt = null;
            Connection con = ConnectionFactory.conectar("rejud_ob");

            sql = "INSERT INTO tb_historico_desconciliacao_djo_paj (CODIGO_DESCONCILIACAO,NPJ,VARIACAO_NPJ,CONTA_CONTROLE,CONTA_DEPOSITARIA,SALDO_CONTA_CONTROLE,SALDO_DEPOSITO,VALOR_DESCONCILIACAO,SITUACAO,DATA_SITUACAO,FUNCIONARIO_RESPONSAVEL_SITUACAO,NOME_TRATAMENTO,DATA_DESCONCILIACAO,DIAS_DESCONCILIADO,DATA_ENTRADA_BD,MES_TRATAMENTO,CODIGO_FUNCAO,UOR_LOCALIZACAO,CODIGO_TAREFA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

            stmt.setDate(10, (Date) entidade.getDataSituacao());

            stmt.setString(11, "F5078775");
            stmt.setString(12, entidade.getNomeTratamento());
            stmt.setDate(13, (Date) entidade.getDataDesconciliacao());
            stmt.setInt(14, entidade.getDiasDesconciliado());
            stmt.setDate(15,(Date) entidade.getDataEntradaBd());
            stmt.setString(16,Utils.getMesAtual());
            stmt.setInt(17,7005);
            stmt.setInt(18,286409);
            if((entidade.getValorDesconciliacao()<50000)){
                
                stmt.setInt(19,21444);
            } else{
                stmt.setInt(19,21443);
            }

            stmt.executeUpdate();

        } catch (SQLException ex) {

            throw new ErroSistema("Erro ao tentar salvar histórico", ex);

        } finally {

            ConnectionFactory.fecharConexao();

        }

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
