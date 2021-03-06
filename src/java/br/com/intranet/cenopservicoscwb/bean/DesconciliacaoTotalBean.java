
package br.com.intranet.cenopservicoscwb.bean;

import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoTotalDAO;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class DesconciliacaoTotalBean  extends CrudBean<DesconciliacaoOB, DesconciliacaoTotalDAO>{
    private DesconciliacaoTotalDAO desconciliacaoDAO;
    
    
   @PostConstruct
   public void init(){
       
       buscar();
   }
    
  
    
    @Override
    public DesconciliacaoTotalDAO getDao() {
       if(desconciliacaoDAO == null){
            desconciliacaoDAO = new DesconciliacaoTotalDAO();
        }
        return desconciliacaoDAO;
    }

    @Override
    public DesconciliacaoOB criarNovaEntidade() {
      return new DesconciliacaoOB();
    }
    
    
   
    
}
