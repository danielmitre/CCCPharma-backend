package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class GreatDiscount extends Discount{
	
    public GreatDiscount(){
    }

    public double getDiscount(){
        return (float) 0.25;
    }
}