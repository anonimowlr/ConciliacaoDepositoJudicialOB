
package br.com.intranet.cenopservicoscwb.bean;

import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoConexaoDAO;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class DesconciliacaoConexaoBean  extends CrudBean<DesconciliacaoOB, DesconciliacaoConexaoDAO>{
    private DesconciliacaoConexaoDAO desconciliacaoDAO;
    
    
   @PostConstruct
   public void init(){
       
       buscar();
       
       
   }    
  
    
    @Override
    public DesconciliacaoConexaoDAO getDao() {
       if(desconciliacaoDAO == null){
            desconciliacaoDAO = new DesconciliacaoConexaoDAO();
        }
        return desconciliacaoDAO;
    }

    @Override
    public DesconciliacaoOB criarNovaEntidade() {
      return new DesconciliacaoOB();
    }
    
    
   
    
}
