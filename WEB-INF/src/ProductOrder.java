
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
	@NamedQuery(name="ProductOrder.findAll", query="select o from ProductOrder o"), 
	@NamedQuery(name = "ProductOrder.findById", query = "select o from ProductOrder o where o.id=:id"),
})

@Entity
@XmlRootElement(name = "productorder")
public class ProductOrder {
	 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@OneToOne(fetch = FetchType.EAGER)
	private Customer customer;
	@OneToOne(fetch = FetchType.EAGER)
	private Product product;
	private String address;
	private double total;
	private String orderstatus;



	public ProductOrder() {
		super();
		// TODO Auto-generated constructor stub
	}


	


	public ProductOrder(Customer customer, Product product, String address, double total,
			String orderstatus) {
		super();
		this.customer = customer;
		this.product = product;
		this.address=address;
		this.total = product.getPrice();
		this.orderstatus = orderstatus;
	}




	@XmlElement
	public String getOrderstatus() {
		return orderstatus;
	}





	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}





	@XmlElement
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@XmlElement
	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	@XmlElement
	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public double getTotal() {
		return total;
	}





	public void setTotal(double total) {
		this.total = total;
	}



	

}