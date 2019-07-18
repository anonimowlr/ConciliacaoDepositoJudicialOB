
package br.com.intranet.cenopservicoscwb.bean;

import br.com.intranet.cenopservicoscwb.dao.DesconciliacaoOBDesfavoravelDAO;
import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class DesconciliacaoOBDesfavoravelBean  extends CrudBean<DesconciliacaoOB, DesconciliacaoOBDesfavoravelDAO>{
    private DesconciliacaoOBDesfavoravelDAO desconciliacaoOBDAO;
    
    
   @PostConstruct
   public void init(){
       
       buscar();
       
       
   }
    
  
    
    @Override
    public DesconciliacaoOBDesfavoravelDAO getDao() {
       if(desconciliacaoOBDAO == null){
            desconciliacaoOBDAO = new DesconciliacaoOBDesfavoravelDAO();
        }
        return desconciliacaoOBDAO;
    }

    @Override
    public DesconciliacaoOB criarNovaEntidade() {
      return new DesconciliacaoOB();
    }
    
    
   
    
}
