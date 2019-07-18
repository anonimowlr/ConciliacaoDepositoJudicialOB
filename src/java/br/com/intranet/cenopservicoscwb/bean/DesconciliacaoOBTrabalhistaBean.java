
package br.com.intranet.cenopservicoscwb.bean;

import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoOBDAO;
import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoOBTrabalhistaDAO;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class DesconciliacaoOBTrabalhistaBean  extends CrudBean<DesconciliacaoOB, DesconciliacaoOBTrabalhistaDAO>{
    private DesconciliacaoOBTrabalhistaDAO desconciliacaoOBDAO;
    
    
   @PostConstruct
   public void init(){
       
       buscar();
       
       
   }
    
  
    
    @Override
    public DesconciliacaoOBTrabalhistaDAO getDao() {
       if(desconciliacaoOBDAO == null){
            desconciliacaoOBDAO = new DesconciliacaoOBTrabalhistaDAO();
        }
        return desconciliacaoOBDAO;
    }

    @Override
    public DesconciliacaoOB criarNovaEntidade() {
      return new DesconciliacaoOB();
    }
    
    
   
    
}
