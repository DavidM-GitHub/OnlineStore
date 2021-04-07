 

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PatternsDAO {

	protected static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("patternsPU"); 	
	
	public PatternsDAO(){
		
	}
	   
	public void persistCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
		em.close();
	}
	
	public void removeCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(customer));
		em.getTransaction().commit();
		em.close();
	}
	
	public Customer mergeCustomer(Customer customer) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Customer updatedCustomer = em.merge(customer);
		em.getTransaction().commit();
		em.close();
		return updatedCustomer;
	}
	
	
	public List<Customer> getAllCustomers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Customer> customersFromDB = new ArrayList<Customer>();
		customersFromDB = em.createNamedQuery("Customer.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return customersFromDB;
	}
	
	public User getCustomerByEmail(String email){
		EntityManager em = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Customer> customers = (List<Customer>) 
				em.createNamedQuery("Customer.findByEmail").
				setParameter("email", email).getResultList();
		em.close();
		//Do whatever you want with the employee(s) with that username
		//Here we just return the first one
		Customer customer = new Customer();
		for(Customer c: customers) {
			customer = c;
			return customer;
		}
		return new NoCustomer();
	}
	
	public Customer getCustomerById(int id){
		EntityManager em = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Customer> customers = (List<Customer>) 
				em.createNamedQuery("Customer.findById").
				setParameter("id", id).getResultList();
		em.close();
		//Do whatever you want with the employee(s) with that username
		//Here we just return the first one
		Customer customer = new Customer();
		for(Customer c: customers) {
			customer = c;
		}
		return customer;
	}
	
	public void persistProductOrder(ProductOrder productOrder) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(productOrder);
		em.getTransaction().commit();
		em.close();
	}
	
	public void removeProductOrder(ProductOrder productOrder) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(productOrder));
		em.getTransaction().commit();
		em.close();
	}
	
	public ProductOrder mergeProductOrder(ProductOrder productOrder) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		ProductOrder updatedproductOrder = em.merge(productOrder);
		em.getTransaction().commit();
		em.close();
		return updatedproductOrder;
	}
	
	
	public List<ProductOrder> getAllProductOrders() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<ProductOrder> productOrderFromDB = new ArrayList<ProductOrder>();
		productOrderFromDB = em.createNamedQuery("ProductOrder.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return productOrderFromDB;
	}
	
	
	public ProductOrder getProductOrderById(int id){
		EntityManager em = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<ProductOrder> productOrders = (List<ProductOrder>) 
				em.createNamedQuery("ProductOrder.findById").
				setParameter("id", id).getResultList();
		em.close();
		//Do whatever you want with the employee(s) with that username
		//Here we just return the first one
		ProductOrder productOrder = new ProductOrder();
		for(ProductOrder p: productOrders) {
			productOrder = p;
		}
		return productOrder;
	}


	public void removeProduct(Product product) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(product));
		em.getTransaction().commit();
		em.close();
	}
	
	public Product mergeProduct(Product product) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Product updatedproduct = em.merge(product);
		em.getTransaction().commit();
		em.close();
		return updatedproduct;
	}
	
	
	public List<Product> getAllProducts() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Product> productFromDB = new ArrayList<Product>();
		productFromDB = em.createNamedQuery("Product.findAll").getResultList();
		em.getTransaction().commit();
		em.close();
		return productFromDB;
	}
	
	
	public Product getProductById(int id){
		EntityManager em = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>) 
				em.createNamedQuery("Product.findById").
				setParameter("id", id).getResultList();
		em.close();
		//Do whatever you want with the employee(s) with that username
		//Here we just return the first one
		Product product = new Product();
		for(Product p: products) {
			product = p;
		}
		return product;
	}
	
	
	
	public void persistProduct(Product product) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
		em.close();
	}
	
	
}
