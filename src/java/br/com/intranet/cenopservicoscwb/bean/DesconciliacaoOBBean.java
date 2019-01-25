
package br.com.intranet.cenopservicoscwb.bean;

import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoOBDAO;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class DesconciliacaoOBBean  extends CrudBean<DesconciliacaoOB, DesconciliacaoOBDAO>{
    private DesconciliacaoOBDAO desconciliacaoOBDAO;
    
    
   @PostConstruct
   public void init(){
       
       buscar();
       
       
   }
    
  
    
    @Override
    public DesconciliacaoOBDAO getDao() {
       if(desconciliacaoOBDAO == null){
            desconciliacaoOBDAO = new DesconciliacaoOBDAO();
        }
        return desconciliacaoOBDAO;
    }

    @Override
    public DesconciliacaoOB criarNovaEntidade() {
      return new DesconciliacaoOB();
    }
    
    
   
    
}
