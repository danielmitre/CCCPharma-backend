package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.lot;

import java.text.ParseException;

public class RegisterInformationLot {

	private String productBarcode;
	private String shelfLife;
	private int amount;

	
	
	public RegisterInformationLot(String productBarcode, String shelfLife, int amount) throws ParseException {
		super();
		this.productBarcode = productBarcode;
		this.shelfLife = shelfLife;
		this.amount = amount;
	}

	public String getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
