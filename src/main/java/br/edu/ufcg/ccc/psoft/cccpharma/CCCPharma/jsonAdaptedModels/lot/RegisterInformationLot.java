package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.lot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterInformationLot {

	private String productBarcode;
	private Date shelfLife;
	private int amount;

	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public RegisterInformationLot(String productBarcode, String shelfLife, int amount) throws ParseException {
		super();
		this.productBarcode = productBarcode;
		this.shelfLife = dateFormat.parse(shelfLife);
		this.amount = amount;
	}

	public String getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}

	public Date getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) throws ParseException {
		this.shelfLife = dateFormat.parse(shelfLife);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
