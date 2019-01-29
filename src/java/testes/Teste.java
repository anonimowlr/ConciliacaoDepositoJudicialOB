/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import br.com.intranet.cenopservicoscwb.entity.DesconciliacaoOB;
import br.com.intranet.cenopservicoscwb.jpa.EntityManagerUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author f5078775
 */
public class Teste {
    
    
    
    public static void main(String[] args) {
         EntityManager em = EntityManagerUtil.getEntityManager();
        
        List<DesconciliacaoOB>  listaCadastro = new ArrayList<>();
        
        try{
          listaCadastro = em.createQuery("FROM DesconciliacaoOB cadastro").getResultList();
          
    }catch(Exception e ){
        
    }
    
    }






}
