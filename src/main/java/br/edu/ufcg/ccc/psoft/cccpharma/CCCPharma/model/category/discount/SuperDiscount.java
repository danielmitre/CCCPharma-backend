package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;

@Entity
public class SuperDiscount extends Discount{
	
    public SuperDiscount(){
    }

    public double getDiscount(){
        return (float) 0.5;
    }
}