package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


@Entity
@Table(name="discount")
public abstract class Discount {
	@Id @GeneratedValue
	protected Long ID; 
	
    public abstract double getDiscount();
}