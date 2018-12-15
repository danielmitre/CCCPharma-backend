package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.product;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product.Status;

public abstract class PartialInformationProduct {
	protected String name;
	protected Status status;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}	
	
}
