package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class NoDiscount extends Discount {
	
    public NoDiscount(){
    }

    public double getDiscount(){
        return (float) 0.0;
    }
}