package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.product;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product.Status;

public class PartialInformationUnavailableProduct extends PartialInformationProduct{

	public PartialInformationUnavailableProduct(String name, Status status) {
		this.name = name;
		this.status = status;
	}
	
}
