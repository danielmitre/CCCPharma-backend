package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.product;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product.Status;

public class PartialInformationAvailableProduct extends PartialInformationProduct {
	private double price;
	
	public PartialInformationAvailableProduct(String name, double price, Status status) {
		this.name = name;
		this.status = status;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
