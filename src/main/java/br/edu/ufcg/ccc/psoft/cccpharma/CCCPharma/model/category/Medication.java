package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount.NoDiscount;

@Entity
public class Medication extends Category {

    public Medication(){
        this.discount = new NoDiscount();
    }

    @Override
    public String toString(){
        return "Medicamento";
    }

}