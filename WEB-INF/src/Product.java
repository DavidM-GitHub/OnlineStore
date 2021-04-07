

import javax.persistence.Entity; 

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
	@NamedQuery(name="Product.findAll", query="select o from Product o"), 
	@NamedQuery(name = "Product.findById", query = "select o from Product o where o.id=:id"),
})

@Entity
@XmlRootElement(name = "product")
public class Product {
  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String image;
	private int price;
	private String category;
	private String manufacturer;
	private int stock;
	private String comments;
	private String ratings;
	
	public Product() {
		
	}

	public Product(String name, String image, int price, String category,String manufacturer, int stock) {
		super();
		this.name = name;
		this.image = image;
		this.price = price;
		this.category = category;
this.manufacturer=manufacturer;
		this.stock = stock;
	}


	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@XmlElement
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@XmlElement
	public int getPrice() {
		return price;
	}
 
	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	
}
