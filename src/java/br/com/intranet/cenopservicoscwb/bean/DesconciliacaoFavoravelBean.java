
package br.com.intranet.cenopservicoscwb.bean;

import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoFavoravelDAO;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class DesconciliacaoFavoravelBean  extends CrudBean<DesconciliacaoOB, DesconciliacaoFavoravelDAO>{
    private DesconciliacaoFavoravelDAO desconciliacaoDAO;
    
    
   @PostConstruct
   public void init(){
       
       buscar();
       
       
   }
    
  
    
    @Override
    public DesconciliacaoFavoravelDAO getDao() {
       if(desconciliacaoDAO == null){
            desconciliacaoDAO = new DesconciliacaoFavoravelDAO();
        }
        return desconciliacaoDAO;
    }

    @Override
    public DesconciliacaoOB criarNovaEntidade() {
      return new DesconciliacaoOB();
    }
    
    
   
    
}
