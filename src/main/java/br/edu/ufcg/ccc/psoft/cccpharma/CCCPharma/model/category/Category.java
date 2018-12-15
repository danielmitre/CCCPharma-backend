package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.Conflict409Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.discount.*;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product.Product;

@Entity
@Table(name="category")
public abstract class Category {

	@Id
	@GeneratedValue
	protected Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected Discount discount;

    
    public double getDiscount(){
        return discount.getDiscount();
    }

    public void setDiscount(double discount) throws Conflict409Exception{
        if (discount == 0.0)
            this.discount = new NoDiscount();
        else if (discount == 0.10)
            this.discount = new GoodDiscount();
        else if (discount == 0.25)
            this.discount = new GreatDiscount();
        else if (discount == 0.50)
            this.discount = new SuperDiscount();
        else
            throw new Conflict409Exception("discount type not defined");
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		return true;
	}

}