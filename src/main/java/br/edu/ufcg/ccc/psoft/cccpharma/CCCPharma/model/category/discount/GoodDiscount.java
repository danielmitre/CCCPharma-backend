package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class GoodDiscount extends Discount{
    public GoodDiscount(){
    }

    public double getDiscount(){
        return (float) 0.10;
    }
    
}