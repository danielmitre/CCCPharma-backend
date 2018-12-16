package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.controller.product;

import java.util.*;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.BadRequest400Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.Conflict409Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.product.PartialInformationAvailableProduct;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.product.PartialInformationProduct;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.product.PartialInformationUnavailableProduct;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.category.*;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product.Product;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.product.Status;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.repository.CategoryRepository;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.repository.ProductRepository;

public class ProductController {

	private List<Product> products;
	private HashMap<String, Category> categories;
	private ProductRepository productDAO;
	private CategoryRepository categoryDAO;

	public ProductController(ProductRepository productDAO, CategoryRepository categoryDAO) {
		this.productDAO = productDAO;
		this.categoryDAO = categoryDAO;
		try {
			this.products = loadProducts();
		} catch (RuntimeException e) {
			System.out.println("No product found:" + e.getMessage());
			System.out.println(e.getStackTrace());
			for (int i=0; i<10; i++) {
				System.out.println();
			}
		}
		
		try {
			this.categories = loadCategories();
		} catch (RuntimeException e) {
			System.out.println("No category found:" + e.getMessage());
			System.out.println(e.getStackTrace());
			for (int i=0; i<10; i++) {
				System.out.println();
			}
		}
		
	}
	
	public void addProduct(String name, String barCode, String company, String categoryType, String status)
			throws Conflict409Exception {
		
		if (null != getProductByBarcode(barCode)) {
			System.out.println("existe um produto com barcode="+getProductByBarcode(barCode).getBarcode());
			throw new Conflict409Exception("This product is already registered");	
		}

		
		Category category = null;
		try {
			category = this.categories.get(categoryType);
		} catch (RuntimeException e) {
			System.out.println("Falha ao transformar categoria");
		}		
		
		Product product = new Product(name, barCode, category, company, status);
		
		try {
			this.products.add(product);
		} catch (RuntimeException e) {
			System.out.println("Falha ao adicionar na lista");
		}
		
		try {
			this.productDAO.save(product);
		} catch (RuntimeException e) {
			System.out.println("falha ao adicionar no DAO");
		}
	}

	public void addLot(int productAmount, Date shelfLife, String barcode)
			throws BadRequest400Exception, Conflict409Exception {
		Product product = getProductByBarcode(barcode);

		if (product != null) {
			product.addLot(productAmount, shelfLife);
			product.setStatus("Available");
			this.productDAO.save(product);
		} else {
			throw new BadRequest400Exception("Product is not registered");
		}
	}

	public void changeProductPrice(double price, String barcode) throws BadRequest400Exception {
		Product product = getProductByBarcode(barcode);

		if (product != null) {
			product.setPrice(price);
			this.productDAO.save(product);
		} else
			throw new BadRequest400Exception("Product is not registered");
	}

	public void decreaseProductAmount(int amount, String barcode) throws BadRequest400Exception, Conflict409Exception {
		Product product = getProductByBarcode(barcode);

		if (product != null) {
			if (product.getStatus() == Status.Unavailable)
				throw new Conflict409Exception("There is no lot of this product in stock");
			else {
				product.ensureLotsNormality();
				this.productDAO.save(product);
				
				product.decreaseAmount(amount);
				this.productDAO.save(product);
			}
		} else {
			System.out.println("Product is not registered");
			throw new BadRequest400Exception("Product is not registered");
		}
	}

	public void changeCategoryDiscount(String categoryType, double discount) {
		Category category = getCategory(categoryType);
		category.setDiscount(discount);
		this.categoryDAO.save(category);
	}

	public List<PartialInformationProduct> getProductsInfo() {
		List<PartialInformationProduct> productsInfo = new ArrayList<>();
		
		for (int i=0; i < this.products.size(); i++) {
			Product product = products.get(i);
			if (product.getStatus() == Status.Available) {
				productsInfo.add(new PartialInformationAvailableProduct(
						product.getName(),
						product.getPrice(),
						product.getStatus()
				));
			} else {
				productsInfo.add(new PartialInformationUnavailableProduct(
						product.getName(),
						product.getStatus()
				));
			}
		}
		
		System.out.println(productsInfo);
		
		return productsInfo;
	}

	public List<Product> getInventoryReport() {
		return this.products;
	}

	private Category getCategory(String categoryType) {
		Category category = this.categories.get(categoryType);
		if (category != null)
			return category;
		else
			throw new BadRequest400Exception("There is no such category in the system");
	}

	private Product getProductByBarcode(String barcode) {
		for (Product product : this.products) {
			if (product.getBarcode().equals(barcode)) {
				return product;
			}	
		}
		return null;
	}

	private HashMap<String, Category> loadCategories() {
		final long cosmeticId = 1;
		final long foodId = 3;
		final long medicationId = 9;
		final long toiletryId = 7;
		
		if (!categoryDAO.existsById(cosmeticId)) {
			categoryDAO.save(new Cosmetic());
		}
		Category cosmetic = categoryDAO.findById(cosmeticId).get();
		
		if (!categoryDAO.existsById(foodId)) {
			categoryDAO.save(new Food());
		}
		Category food = categoryDAO.findById(foodId).get();
		
		if (!categoryDAO.existsById(medicationId)) {
			categoryDAO.save(new Medication());
		}
		Category medication = categoryDAO.findById(medicationId).get();
		
		if (!categoryDAO.existsById(toiletryId)) {
			categoryDAO.save(new Toiletry());
		}
		Category toiletry = categoryDAO.findById(toiletryId).get();

		HashMap<String, Category> categories = new HashMap<String, Category>();
		categories.put("cosmetic", cosmetic);
		categories.put("food", food);
		categories.put("medication", medication);
		categories.put("toiletry", toiletry);
		return categories;
	}

	private List<Product> loadProducts() {
		Iterable<Product> iterableProducts = this.productDAO.findAll();
		List<Product> productList = new ArrayList<Product>();
		for (Product product : iterableProducts) {
			productList.add(product);
		}
		return productList;
	}
}