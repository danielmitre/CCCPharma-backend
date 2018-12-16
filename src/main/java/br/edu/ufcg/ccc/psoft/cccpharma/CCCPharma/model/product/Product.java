package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Proxy;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.BadRequest400Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.Conflict409Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.*;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.lot.Lot;

@Entity
@Table(name = "product")
@Proxy(lazy = false)
public class Product {
	
	@Id
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name = "name", nullable = false)
	@Size(max = 30)
    private String name;
	
	@Column(name = "company", nullable = false)
	@Size(max = 40)
    private String company;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Status status;
    
    @Column(name = "price", nullable = false)
    private double price;
    
	@OneToMany(mappedBy="_lot_",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy("shelf_life")
    private List<Lot> lots;

	public List<Lot> getLots() {
		return lots;
	}

	public void setLots(List<Lot> lots) {
		this.lots = lots;
	}

	public Product() {
		super();
	}
	
    public Product(String name, String barcode, Category category, String company, String status) throws Conflict409Exception{
        this.name = name;
        this.barcode = barcode;
        this.company = company;
        this.price = 0.0;
        this.lots = new ArrayList<Lot>();
        setStatus(status);
        setCategory(category);
    };

    public double getPrice() {
        return (this.price - this.price * this.category.getDiscount());
    }

    public void setPrice(double price) throws BadRequest400Exception {
        if (price > 0)
        	this.price = price;
        else
        	throw new BadRequest400Exception("Cannot assign a price of zero to a product");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStatus(String status) throws Conflict409Exception{
        if (status.toLowerCase().equals("available")) {
            this.status = Status.Available;
        } else if (status.toLowerCase().equals("unavailable")) {
            this.status = Status.Unavailable;
        } else {
            throw new Conflict409Exception("Status value not defined");	
        }
    }

    public Status getStatus() {
    	return this.status;
    }
    
    public void setCategory(Category category){
        this.category = category;
    }

    public Category getCategory(){
        return this.category;
    }
    
    public void addLot(int amount, Date shelfLife) throws BadRequest400Exception {
    	Lot lot = new Lot(amount, shelfLife, this);
    	this.lots.add(lot);
    }
    
    public void addLot(Lot lot) {
    	this.lots.add(lot);
    }
    
    public void removeLot(Lot lot) {
    	this.lots.remove(lot);
    }
  
    
    public void setStatus(Status status) {
		this.status = status;
	}

	public int getAmount() {
    	int amount = 0;
    	int i = 0;
    	while (i < this.lots.size()) {
    		amount += this.lots.get(i).getAmount();
    		i++;
    	}
    	return amount;
    }
    
    public void decreaseAmount(int amount) throws BadRequest400Exception {
    	ensureLotsNormality();
    	
    	int totalAmount = getAmount();
    	if (totalAmount - amount > 0) {
    		int i = 0;
    		while (amount > 0 && i < this.lots.size()) {
    			Lot closestToOutOfDate = closestToShelfLife();
    			if(closestToOutOfDate.getAmount() <= amount) {
    				amount -= closestToOutOfDate.getAmount();
    				this.lots.remove(closestToOutOfDate);
    				i++;
    			} else {
    				int auxliaryAmount = amount;
    				amount = 0;
    				closestToOutOfDate.decreaseAmount(auxliaryAmount);
    			}
    		}
    	} else if (totalAmount - amount == 0) {
    		this.lots = new ArrayList<Lot>();
    	} else
    		throw new BadRequest400Exception("Desired amount is greater than amount of products in stock");
    }
    
    private Lot closestToShelfLife() {
    	if (this.lots.size() > 0) {    		
    		Lot closestShelfLifeLot = this.lots.get(0);
    		int i = 0;
    		while (i < this.lots.size()) {
    			Lot lot = this.lots.get(i);
    			if (lot.getShelfLife().before(closestShelfLifeLot.getShelfLife())) {
    				closestShelfLifeLot = lot;
    			}
    			i++;
    		}
    		return closestShelfLifeLot;
    	} else
    		return null;
    }
    
    private void ensureLotsNormality() {
    	for (Lot lot : this.lots) {
    		if (lot.isOutOfDate() || lot.isOutOfStock())
    			this.lots.remove(lot);
    	}
    }
    
    private String lotsToString() {
    	String description = "[";
    	for (int i = 0; i < lots.size() - 1; i++) {
    		description += this.lots.get(i).toString() + ", ";
    	}
    	description += this.lots.get(this.lots.size() - 1).toString() + "]";
    	return description;
    }

    public String partialInfo() {
    	String description = "PRODUTO: " + this.name;
    	if (this.status.equals(Status.Available)) {
    		description += "; PREÇO: " + price;
    		description += "; SITUAÇÃO: Disponível";
    	} else
    		description += "; SITUAÇÃO: Indisponível";
    	return description;
    }
    
    public String completeInfo(){
        String description = "{PRODUTO: " + this.name
                         + "; FABRICANTE: " + this.company
                         + "; CÓDIGO DE BARRAS: " + this.barcode
                         + "; CATEGORIA: " + this.category.toString();

        if (this.status.equals(Status.Available)) {
            description += "; PREÇO: " + price;
            description += "; SITUAÇÃO: Disponível";
        } else
            description += "; SITUAÇÃO: Indisponível";
        
        description += "Nº DE LOTES: " + getAmount();
        description += "LOTES: " + lotsToString();
        
        return description;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
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
		Product other = (Product) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		return true;
	}

}