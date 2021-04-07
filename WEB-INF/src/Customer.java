 

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
	@NamedQuery(name="Customer.findAll", query="select o from Customer o"), 
	@NamedQuery(name = "Customer.findById", query = "select o from Customer o where o.id=:id"),
	@NamedQuery(name = "Customer.findByEmail", query = "select o from Customer o where o.email=:email")
})

@Entity
@XmlRootElement(name = "customer")
public class Customer extends User{
	 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;	
	private String address;
	private String paymentMethod;
	@OneToMany(fetch = FetchType.EAGER)
	private List<Product> products=new ArrayList<Product>();
	private String admin="no";
	
	public Customer() {
		
	}
	
 
	 

	public Customer(String name, String email, String password, String address, String paymentMethod) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.paymentMethod = paymentMethod;
	}




	public String getAdmin() {
		return admin;
	}


	public void setAdmin(String admin) {
		this.admin = admin;
	}


	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPaymentMethod() {
		return paymentMethod;
	}



	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}



	public List<Product> getProducts() {
		return products;
	}



	public void setProducts(List<Product> products) {
		this.products = products;
	}



	@XmlElement
	public int getId() {
		return id;
	}
	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@XmlElement
	public List<Product> getProduct() {
		return products;
	}

	public void buyProduct(Product product) {
		this.products.add(product);
	}



	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", address="
				+address + ", products=" + products + "]";
	}

	
	public boolean isNull() 
    {
        return false;
    }
	
}
