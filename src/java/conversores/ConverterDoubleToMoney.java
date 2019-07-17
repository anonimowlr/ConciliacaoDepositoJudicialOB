/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversores;


import br.com.intranet.cenopservicoscwb.bean.util.Utils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author PC_LENOVO
 */
@FacesConverter(value = "converterDoubleToMoney")
public class ConverterDoubleToMoney implements  Serializable,Converter{

   
            
    
    
    
    
    
    
    @Override // da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
   
            try{
                Double retorno =  Double.parseDouble(string);
               
                return  retorno;
            } catch (Exception ex) {
                
                   return  null;
            
            
            }
        
    
    }
 
    @Override   // do objeto para a tela;
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {

             if(o== null || o.equals(" ")){
                 return null;
             }
                
             
             Double obj = (Double) o;
             
             String retorno =Utils.converterToMoney(o.toString());
             
             
             return  retorno;
    }
    
    
    
}
