
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;



@Path("/sampleservice")
public class SampleService {
	
	@Context
    private HttpServletRequest request;
	private static Map<String, Customer> customers = new HashMap<String, Customer>();
	public Map<String,Object> session=new HashMap<String,Object>();
	TableFactory tableFactory=new TableFactory();
	private static Gson gson = new Gson();
	private String error="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n" + 
			"   \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
			"\r\n" + 
			"<html>\r\n" + 
			"<head>\r\n" + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
			"<title>Error</title>\r\n" + 
			"	<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
			"\r\n" + 
			"</head>\r\n" + 
			"<body >\r\n" + 
			"<div class=\"body\">\r\n" + 
			"    \r\n" + 
			"	 \r\n" + 
			"	<h2 id=\"username\">The search criteria entered did not return any results</h2>\r\n" + 
			"	\r\n" + 
			"		\r\n" + 
			"		\r\n" + 
			"		<h2>Please Try Again</h2>\r\n" + 
			"		<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/home\" method=\"GET\"> \r\n" + 
			"	<input class=\"button\" type=\"submit\" value=\"Return Home\" >\r\n" + 
			"	    </form></br>\r\n" + 
			"	\r\n" + 
			"			\r\n" + 
			"\r\n" + 
			"	\r\n" + 
			"	</div>\r\n" + 
			"</body>\r\n" + 
			"</html>";
	private String notAdminError="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n" + 
			"   \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
			"\r\n" + 
			"<html>\r\n" + 
			"<head>\r\n" + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
			"<title>Error</title>\r\n" + 
			"	<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
			"\r\n" + 
			"</head>\r\n" + 
			"<body >\r\n" + 
			"<div class=\"body\">\r\n" + 
			"    \r\n" + 
			"	 \r\n" + 
			"	<h2 id=\"username\">This page is for Admin use only</h2>\r\n" + 
			"	\r\n" + 
			"		\r\n" + 
			"		\r\n" + 
			"		<h2>Return Home</h2>\r\n" + 
			"		<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/home\" method=\"GET\"> \r\n" + 
			"	<input class=\"button\" type=\"submit\" value=\"Return Home\" >\r\n" + 
			"	    </form></br>\r\n" + 
			"	\r\n" + 
			"			\r\n" + 
			"\r\n" + 
			"	\r\n" + 
			"	</div>\r\n" + 
			"</body>\r\n" + 
			"</html>";
	
	private String loggedout="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n" + 
			"   \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
			"\r\n" + 
			"<html>\r\n" + 
			"<head>\r\n" + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
			"<title>Error</title>\r\n" + 
			"	<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
			"\r\n" + 
			"</head>\r\n" + 
			"<body >\r\n" + 
			"<div class=\"body\">\r\n" + 
			"    \r\n" + 
			"	 \r\n" + 
			"	<h2 id=\"username\">Not Logged In</h2>\r\n" + 
			"	\r\n" + 
			"		\r\n" + 
			"		\r\n" + 
			"		<h2>Return to Login</h2>\r\n" + 
			"		<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/index\" method=\"GET\"> \r\n" + 
			"	<input class=\"button\" type=\"submit\" value=\"Return Home\" >\r\n" + 
			"	    </form></br>\r\n" + 
			"	\r\n" + 
			"			\r\n" + 
			"\r\n" + 
			"	\r\n" + 
			"	</div>\r\n" + 
			"</body>\r\n" + 
			"</html>";
String filterScript="<script>\r\n" + 
		"function sortTable(n) {\r\n" + 
		"  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;\r\n" + 
		"  table = document.getElementById(\"myTable\");\r\n" + 
		"  switching = true;\r\n" + 
		"  // Set the sorting direction to ascending:\r\n" + 
		"  dir = \"asc\";\r\n" + 
		"  /* Make a loop that will continue until\r\n" + 
		"  no switching has been done: */\r\n" + 
		"  while (switching) {\r\n" + 
		"    // Start by saying: no switching is done:\r\n" + 
		"    switching = false;\r\n" + 
		"    rows = table.rows;\r\n" + 
		"    /* Loop through all table rows (except the\r\n" + 
		"    first, which contains table headers): */\r\n" + 
		"    for (i = 1; i < (rows.length - 1); i++) {\r\n" + 
		"      // Start by saying there should be no switching:\r\n" + 
		"      shouldSwitch = false;\r\n" + 
		"      /* Get the two elements you want to compare,\r\n" + 
		"      one from current row and one from the next: */\r\n" + 
		"      x = rows[i].getElementsByTagName(\"TD\")[n];\r\n" + 
		"      y = rows[i + 1].getElementsByTagName(\"TD\")[n];\r\n" + 
		"      /* Check if the two rows should switch place,\r\n" + 
		"      based on the direction, asc or desc: */\r\n" + 
		"      if (dir == \"asc\") {\r\n" + 
		"        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {\r\n" + 
		"          // If so, mark as a switch and break the loop:\r\n" + 
		"          shouldSwitch = true;\r\n" + 
		"          break;\r\n" + 
		"        }\r\n" + 
		"      } else if (dir == \"desc\") {\r\n" + 
		"        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {\r\n" + 
		"          // If so, mark as a switch and break the loop:\r\n" + 
		"          shouldSwitch = true;\r\n" + 
		"          break;\r\n" + 
		"        }\r\n" + 
		"      }\r\n" + 
		"    }\r\n" + 
		"    if (shouldSwitch) {\r\n" + 
		"      /* If a switch has been marked, make the switch\r\n" + 
		"      and mark that a switch has been done: */\r\n" + 
		"      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);\r\n" + 
		"      switching = true;\r\n" + 
		"      // Each time a switch is done, increase this count by 1:\r\n" + 
		"      switchcount ++;\r\n" + 
		"    } else {\r\n" + 
		"      /* If no switching has been done AND the direction is \"asc\",\r\n" + 
		"      set the direction to \"desc\" and run the while loop again. */\r\n" + 
		"      if (switchcount == 0 && dir == \"asc\") {\r\n" + 
		"        dir = \"desc\";\r\n" + 
		"        switching = true;\r\n" + 
		"      }\r\n" + 
		"    }\r\n" + 
		"  }\r\n" + 
		"}\r\n" + 
		"</script>";
	
	static {
		
        
    }

	@GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello(){
        return "Hello World";    
    }
	
	
	
	@GET
    @Path("/echo/{message}")
    @Produces("text/plain")
    public String echo(@PathParam("message")String message){
        return message;  
    }

	
	@GET
    @Path("/customers")
    @Produces("application/xml")
    public List<Customer> listCustomers(){
//        return new ArrayList<Employee>(employees.values());
		PatternsDAO deldao=new PatternsDAO();
		return deldao.getAllCustomers();
    }
	
	@GET
    @Path("/customers/json")
    @Produces("application/json")
    public List<Customer> listCustomersJSON(){
//        return new ArrayList<Employee>(employees.values());
		PatternsDAO deldao=new PatternsDAO();
		return deldao.getAllCustomers();
    }
	
	@GET
    @Path("/customer/{customerid}")
    @Produces("application/xml")
    public Customer getCustomer(@PathParam("customerid")String customerid){
		PatternsDAO deldao=new PatternsDAO();
		return deldao.getCustomerById(Integer.parseInt(customerid));			
    }
	
	@GET
	@Path("/registercustomer")
	public InputStream registerCustomer(@QueryParam("name") String name,
			@QueryParam("email") String email,
			@QueryParam("password") String password,
			@QueryParam("address") String address,@QueryParam("payment") String payment) {
		PatternsDAO deldao=new PatternsDAO();
		File f;
		User customer=deldao.getCustomerByEmail(email);
	
		if(!customer.isNull()) {	
			System.out.println("error");
			f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\error1.jsp");
			
		}else {
			Customer customer1=new Customer(name,email,password,address,payment);
			deldao.persistCustomer(customer1);
			request.getSession(true);
			HttpSession session = request.getSession();
			session.setAttribute("user",customer1.getEmail());
			f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\home.jsp");
		}
		
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		else {
//			return "Login details incorrect... Customer not found";
//		}
		return null;
	}
	
//	@GET
//	@Path("/changeLocation")
//	public InputStream changeLocation() {
//		PatternsDAO deldao=new PatternsDAO();
//	
//		File f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\gps.jsp");
//		try {
//			return new FileInputStream(f);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	@GET
	@Path("/error2")
    @Produces("application/json")
	public InputStream error() {
		PatternsDAO deldao=new PatternsDAO();
	
		File f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\error2.jsp");
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@GET
	@Path("/home")
	public InputStream home() {
		PatternsDAO deldao=new PatternsDAO();
	
		File f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\home.jsp");
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@GET
	@Path("/products")
	public InputStream products() {
		PatternsDAO deldao=new PatternsDAO();
	
		File f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\products.jsp");
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	@GET
	@Path("/registration")
	public InputStream registration() {
		PatternsDAO deldao=new PatternsDAO();
	
		File f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\register.jsp");
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

	
	
	@GET
	@Path("/index")
	public InputStream index() {
		PatternsDAO deldao=new PatternsDAO();
	
		File f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\index.jsp");
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@GET
	@Path("/browseProducts")
	public String browse() throws SQLException {
		PatternsDAO deldao=new PatternsDAO();
		
		

		Table table1=tableFactory.getTable("Product");
		
				String html = "<html>\r\n" + 
						"<head>\r\n" + 
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
						"<title>REST Example</title>\r\n" + 
						"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
						"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
						filterScript+
						"</head>\r\n" + 
						"<body >\r\n" + 
						"<div class=\"body\">\r\n" + 
						"    \r\n" + 
						"	 \r\n" + 
						"	<ul>\r\n" + 
						"  <li><a href=\"home\">Home</a></li>\r\n" + 
						"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
						"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
						"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
						"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
						"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
						"</ul></br></br>" + 
						"	\r\n" + 
						"	\r\n" + 
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchName\" method=\"GET\"> \r\n" + 
						"		<p>Name: <input style=\"margin-left:5em\"id=\"name\" type=\"text\" name=\"name\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Name\"/>\r\n" + 
						"		</form></br></br>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchCategory\" method=\"GET\"> \r\n" + 
						"		<p>Category: <input style=\"margin-left:5em\"id=\"category\" type=\"text\" name=\"category\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Category\"/>\r\n" + 
						"		</form></br></br>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchManufacturer\" method=\"GET\"> \r\n" + 
						"		<p>Manufacturer: <input style=\"margin-left:5em\"id=\"manufacturer\" type=\"text\" name=\"manufacturer\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Manufacturer\"/>\r\n" + 
						"		</form></br></br>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/buyProducts\" method=\"GET\"> \r\n" + 
						table1.display("0")+"</br></br>"+
						"\r\n" + 
						"        </br>\r\n" + 
						"<input class=\"button\" type=\"submit\" value=\"Make Purchase\" />\r\n" + 
						"</form>"+
						"<h1>Leave Review</h1></br>\r\n" + 
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/review\" method=\"GET\"> \r\n" + 
						"        <p>Product Id: <input style=\"margin-left:7.1em\" id=\"id\" type=\"text\" name=\"id\" /> </p>\r\n" + 
						"        <p>Rating (1-5): <input style=\"margin-left:7.1em\" id=\"rating\" type=\"text\" name=\"rating\" /> </p>\r\n" + 
						"		<p>Comment: <input style=\"margin-left:7.1em\" id=\"comment\" type=\"text\" name=\"comment\" /> </p>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Submit Review\" />\r\n" + 
						"		</form></br></br>"+
						"	\r\n" +
						"	</div>\r\n" + 
						"</body>\r\n" + 
						"</html>";
				return  html;
	}
	
	
	@GET
	@Path("/viewCustomers")
	public String viewCustomers() throws SQLException {
		PatternsDAO deldao=new PatternsDAO();
		request.getSession(true);
		HttpSession session = request.getSession();
		String email=session.getAttribute("user").toString();
		User customer=(Customer)deldao.getCustomerByEmail(email);
		if(customer.isNull()) {
			return loggedout;
		}
		if( customer.getAdmin().equals("no")) {
			return notAdminError;
		}
		

				
				Table table1=tableFactory.getTable("Customer");
				String table=table1.display("");
//				
//		
				
				String html = "<html>\r\n" + 
						"<head>\r\n" + 
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
						"<title>REST Example</title>\r\n" + 
						"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
						"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
						filterScript+
						"</head>\r\n" + 
						"<body >\r\n" + 
						"<div class=\"body\">\r\n" + 
						"    \r\n" + 
						"	 \r\n" + 
						"	<ul>\r\n" + 
						"  <li><a href=\"home\">Home</a></li>\r\n" + 
						"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
						"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
						"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
						"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
						"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
						"</ul></br></br>" + 
						"	\r\n" + 
						"	\r\n" + 
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchOrders\" method=\"GET\"> \r\n" + 
						"		<p>Customer ID: <input style=\"margin-left:5em\"id=\"id\" type=\"text\" name=\"id\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Order History\"/>\r\n" + 
						"		</form></br></br>"+
						table+"</br></br>"+
						"	</div>\r\n" + 
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/checkout/1\" method=\"GET\"> \r\n" + 
						"<input class=\"button\" type=\"submit\" value=\"Simulate Melatonin Purchase\" />\r\n" + 
						"</form>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/checkout/2\" method=\"GET\"> \r\n" + 
						"<input class=\"button\" type=\"submit\" value=\"Simulate Aspirin Purchase\" />\r\n" + 
						"</form>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/checkout/3\" method=\"GET\"> \r\n" + 
						"<input class=\"button\" type=\"submit\" value=\"Simulate Inhalor Purchase\" />\r\n" + 
						"</form>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/checkout/4\" method=\"GET\"> \r\n" + 
						"<input class=\"button\" type=\"submit\" value=\"Simulate Solpadeine Purchase\" />\r\n" + 
						"</form>"+
						"</body>\r\n" + 
						"</html>";
				return  html;
	}
	
	
	@GET
	@Path("/viewCart")
	public String viewCart() throws SQLException {
		PatternsDAO deldao=new PatternsDAO();
		
		request.getSession(true);
		HttpSession session = request.getSession();
		String email=session.getAttribute("user").toString();
		User customer=deldao.getCustomerByEmail(email);
		String ids="";
		if(customer.isNull()) {
			return loggedout;
		}

		
		//get product orders
		ArrayList<ProductOrder> orders=(ArrayList<ProductOrder>) deldao.getAllProductOrders();
		//take out one for this customer and are in cart
		ArrayList<ProductOrder> myorders=new ArrayList<ProductOrder>();
		for(ProductOrder order:orders) {
			if(order.getCustomer().getId()==customer.getId()&&order.getOrderstatus().equals("inCart")) {
				myorders.add(order);
			}
		}
		if(myorders.isEmpty()) {
			return error;
		}
		//get product from id
		ArrayList<Product> products=new ArrayList<Product>();
		for(ProductOrder order:myorders) {
			products.add(order.getProduct());
			if(ids.equals("")) {
				ids+=String.valueOf(order.getProduct().getId());
			}else {
				ids+=","+String.valueOf(order.getProduct().getId());
			}
		}
		
	
		
				Table table1=tableFactory.getTable("Cart");
				String table=table1.display(String.valueOf( customer.getId()));		
				
				String html = "<html>\r\n" + 
						"<head>\r\n" + 
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
						"<title>REST Example</title>\r\n" + 
						"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
						"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
						filterScript+
						"</head>\r\n" + 
						"<body >\r\n" + 
						"<div class=\"body\">\r\n" + 
						"    \r\n" + 
						"	 \r\n" + 
						"	<ul>\r\n" + 
						"  <li><a href=\"home\">Home</a></li>\r\n" + 
						"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
						"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
						"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
						"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
						"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
						"</ul></br></br>" + 
						"	\r\n" + 
						"	\r\n" + 
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchName\" method=\"GET\"> \r\n" + 
						"		<p>Name: <input style=\"margin-left:5em\"id=\"name\" type=\"text\" name=\"name\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Name\"/>\r\n" + 
						"		</form></br></br>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchCategory\" method=\"GET\"> \r\n" + 
						"		<p>Category: <input style=\"margin-left:5em\"id=\"category\" type=\"text\" name=\"category\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Category\"/>\r\n" + 
						"		</form></br></br>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/searchManufacturer\" method=\"GET\"> \r\n" + 
						"		<p>Manufacturer: <input style=\"margin-left:5em\"id=\"manufacturer\" type=\"text\" name=\"manufacturer\" /> </p></br>\r\n" + 
						"        <input class=\"button\" type=\"submit\" value=\"Search Manufacturer\"/>\r\n" + 
						"		</form></br></br>"+"<p>Buy 3 Products and get 10% off your order!</p>"+
						"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/checkout/"+ids+"\" method=\"GET\"> \r\n" + 
						table+"</br></br>"+
						"\r\n" + 
						"        </br>\r\n" + 
						"<input class=\"button\" type=\"submit\" value=\"Checkout\" />\r\n" + 
						"</form>"+
						"	\r\n" +
						"	</div>\r\n" + 
						"</body>\r\n" + 
						"</html>";
				return  html;
	}
	 
	@GET
	@Path("/checkout/{productids}")
	public InputStream checkout(@PathParam(value = "productids") String productids) {
		PatternsDAO deldao=new PatternsDAO();
		StockHandler handler=StockHandler.getInstance();
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();
		String email=session.getAttribute("user").toString();
		//String productid=session.getAttribute("productid").toString();
		User customer=deldao.getCustomerByEmail(email);
		String[] ids=productids.split(",");
		for(String id:ids) {
			Product product=deldao.getProductById(Integer.parseInt(id));	
			handler.reduceStock(product);
			}
	//	Product product=deldao.getProductById(Integer.parseInt(productid));
		ArrayList<ProductOrder> orders=(ArrayList<ProductOrder>) deldao.getAllProductOrders();
		//take out one for this customer and are in cart
		ArrayList<ProductOrder> myorders=new ArrayList<ProductOrder>();
		for(ProductOrder order:orders) {
			if(order.getCustomer().getId()== customer.getId()&&order.getOrderstatus().equals("inCart")) {
				myorders.add(order);
			}
		}
		boolean discountEligible=false;
		if(myorders.size()>=3) {
			discountEligible=true;
		}
		
		for(ProductOrder order:myorders) {
			order.setOrderstatus("fulfilled");
			if(discountEligible) {
				order.setTotal((order.getTotal()/100)*90);
			}
			deldao.mergeProductOrder(order);
		}

			
		f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\checkoutsuccess.jsp");
		
		
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	
	@GET
	@Path("/ordersuccess")
	public InputStream success() {
		PatternsDAO deldao=new PatternsDAO();
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();
		String email=session.getAttribute("user").toString();
		String productid=session.getAttribute("productid").toString();
		User customer=deldao.getCustomerByEmail(email);
		Product product=deldao.getProductById(Integer.parseInt(productid));

		ProductOrder order=new ProductOrder(((Customer)customer),product, customer.getAddress(),product.getPrice(),"ordered");
		deldao.persistProductOrder(order);
		
		f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\ordersuccess.jsp");
			
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	
	@GET
	@Path("/searchOrders")
	public String searchOrders(@QueryParam("id") String id) {
		PatternsDAO deldao=new PatternsDAO();
		String productid=null;
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();		
		String email=session.getAttribute("user").toString();
		

		Table table1=tableFactory.getTable("Order");
		String table=table1.display(id);
		if(table.equals("error")) {
			return error;
		}
							
//							
//					
							
							String html = "<html>\r\n" + 
									"<head>\r\n" + 
									"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
									"<title>REST Example</title>\r\n" + 
									"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
									"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
									filterScript+
									"</head>\r\n" + 
				"<body >\r\n" + 
				"<div class=\"body\">\r\n" + 
				"    \r\n" + 
				"	 \r\n" + 
				"	<ul>\r\n" + 
				"  <li><a href=\"home\">Home</a></li>\r\n" + 
				"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
				"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
				"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
				"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
				"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
				"</ul></br></br>" + 
				"	\r\n" + 
				"	\r\n" + 
				table+"</br></br>"+
				"\r\n" + 
				"        </br>\r\n" + 
				"	</div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		return  html;
	}
	 
	@GET
	@Path("/searchCategory")
	public String searchCategory(@QueryParam("category") String category) {
		PatternsDAO deldao=new PatternsDAO();
		String productid=null;
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();		
		String email=session.getAttribute("user").toString();
		
			SearchContext context=new SearchContext(new SearchCategory());
			String table=context.executeStrategy(category);
			if(table.equals("error")) {
				return error;
			}
							
							
//							
//					
							
							String html = "<html>\r\n" + 
									"<head>\r\n" + 
									"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
									"<title>REST Example</title>\r\n" + 
									"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
									"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
									filterScript+
									"</head>\r\n" + 
				"<body >\r\n" + 
				"<div class=\"body\">\r\n" + 
				"    \r\n" + 
				"	 \r\n" + 
				"	<ul>\r\n" + 
				"  <li><a href=\"home\">Home</a></li>\r\n" + 
				"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
				"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
				"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
				"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
				"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
				"</ul></br></br>" + 
				"	\r\n" + 
				"	\r\n" + 
				"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/buyProducts\" method=\"GET\"> \r\n" + 
				table+"</br></br>"+
				"\r\n" + 
				"        </br>\r\n" + 
				"<input class=\"button\" type=\"submit\" value=\"Make Purchase\" />\r\n" + 
				"</form>"+
				"	\r\n" +
				"	</div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		return  html;
	}
	
	@GET
	@Path("/searchName")
	public String searchName(@QueryParam("name") String name) {
		PatternsDAO deldao=new PatternsDAO();
		String productid=null;
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();		
		String email=session.getAttribute("user").toString();
		
		SearchContext context=new SearchContext(new SearchName());
		String table=context.executeStrategy(name);
		if(table.equals("error")) {
			return error;
		}
		
//		
							
							String html = "<html>\r\n" + 
									"<head>\r\n" + 
									"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
									"<title>REST Example</title>\r\n" + 
									"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
									"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
									filterScript+
									"</head>\r\n" + 
				"<body >\r\n" + 
				"<div class=\"body\">\r\n" + 
				"    \r\n" + 
				"	 \r\n" + 
				"	<ul>\r\n" + 
				"  <li><a href=\"home\">Home</a></li>\r\n" + 
				"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
				"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
				"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
				"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
				"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
				"</ul></br></br>" + 
				"	\r\n" + 
				"	\r\n" + 
				"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/buyProducts\" method=\"GET\"> \r\n" + 
				table+"</br></br>"+
				"\r\n" + 
				"        </br>\r\n" + 
				"<input class=\"button\" type=\"submit\" value=\"Make Purchase\" />\r\n" + 
				"</form>"+
				"	\r\n" +
				"	</div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		return  html;
	}
	
	@GET
	@Path("/searchManufacturer")
	public String searchManufacturer(@QueryParam("manufacturer") String manufacturer) {
		PatternsDAO deldao=new PatternsDAO();
		String productid=null;
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();		
		String email=session.getAttribute("user").toString();
		
		SearchContext context=new SearchContext(new SearchManufacturer());
		String table=context.executeStrategy(manufacturer);
		if(table.equals("error")) {
			return error;
		}
		
							
							String html = "<html>\r\n" + 
									"<head>\r\n" + 
									"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n" + 
									"<title>REST Example</title>\r\n" + 
									"\r\n" + "    <script src=\"https://js.stripe.com/v3/\"></script>\r\n" + 
									"		<link href=\"http://localhost:8080/spca4/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n" + 
									filterScript+
									"</head>\r\n" + 
				"<body >\r\n" + 
				"<div class=\"body\">\r\n" + 
				"    \r\n" + 
				"	 \r\n" + 
				"	<ul>\r\n" + 
				"  <li><a href=\"home\">Home</a></li>\r\n" + 
				"  <li><a href=\"browseProducts\">Browse Products</a></li>\r\n" + 
				"  <li><a href=\"viewCustomers\" >View Customers</a></li>\r\n" + 
				"  <li><a href=\"viewCart\">Cart</a></li>\r\n" + 
				"    <li><a href=\"index\">Log Out</a></li>\r\n" + 
				"	<li style=\"float:right;\"><a style=\"color:#FF4D00;\">spca4</a></li>\r\n" + 
				"</ul></br></br>" + 
				"	\r\n" + 
				"	\r\n" + 
				"<form action= \"http://localhost:8080/spca4/restful-services/sampleservice/buyProducts\" method=\"GET\"> \r\n" + 
				table+"</br></br>"+
				"\r\n" + 
				"        </br>\r\n" + 
				"<input class=\"button\" type=\"submit\" value=\"Make Purchase\" />\r\n" + 
				"</form>"+
				"	\r\n" +
				"	</div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		return  html;
	}
	
	@GET
	@Path("/buyProducts")
	public InputStream buy(
			@QueryParam("myTextBox1") String myTextBox1,
			@QueryParam("myTextBox2") String myTextBox2,
			@QueryParam("myTextBox3") String myTextBox3,
			@QueryParam("myTextBox4") String myTextBox4) {
		PatternsDAO deldao=new PatternsDAO();
		StockHandler handler=StockHandler.getInstance();
		//String productid=null;
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();		
		String email=session.getAttribute("user").toString();
		System.out.println(myTextBox1);
		System.out.println(myTextBox2);

		System.out.println(myTextBox3);

		System.out.println(myTextBox4);

		
		String[] products= {myTextBox1,myTextBox2,myTextBox3,myTextBox4};
		List<String> productids=new ArrayList<String>();
		for (int i=0;i<products.length;i++) {
			if(products[i]!=null) {
				productids.add(products[i]);
			}
		}
		
		List<Product> productsOrdered=new ArrayList<Product>();
		User customer=deldao.getCustomerByEmail(email);
		for(String productid:productids) {
		Product product=deldao.getProductById(Integer.parseInt(productid));
		productsOrdered.add(product);
		}
		
		System.out.println("Size"+productsOrdered.size());
		
		boolean enoughStock=true;
		if(!productids.isEmpty())
		for (Product product:productsOrdered) {
			if(handler.getStock(product)<1){
				enoughStock=false;
			}
		}
		 
		
		if(!customer.isNull() &&!productids.isEmpty()&&enoughStock) {	
		
			for(Product product:productsOrdered) {
			ProductOrder order=new ProductOrder(((Customer) customer),product,customer.getAddress(),product.getPrice(),"inCart");
			deldao.persistProductOrder(order);
			}
			
			f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\ordersuccess.jsp");
			}else {
				f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\error2.jsp");
			}

		
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@GET
	@Path("/review")
	public InputStream review(
			@QueryParam("id") String id,
			@QueryParam("rating") String rating,
			@QueryParam("comment") String comment) {
		PatternsDAO deldao=new PatternsDAO();
		String productid=null;
		File f;
		request.getSession(true);
		HttpSession session = request.getSession();		
		String email=session.getAttribute("user").toString();
		

//		
//		String[] products= {myTextBox1,myTextBox2,myTextBox3,myTextBox4};
//		for (int i=0;i<products.length;i++) {
//			if(products[i]!=null) {
//				productid=products[i];
//			}
//		}
		
		Product product=deldao.getProductById(Integer.parseInt(id));

		User customer=deldao.getCustomerByEmail(email);
		int rate=Integer.parseInt(rating);
		if((rate>=1&&rate<=5)&&!comment.equals("") &&product.getName()!=null) {	
		
			product.setComments(product.getComments()+"\n"+customer.getName()+": "+comment);
			product.setRatings(product.getRatings()+"\n"+customer.getName()+": "+rating);
			deldao.mergeProduct(product);
			
			f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\reviewsuccess.jsp");
			}else {
				f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\error2.jsp");
			}

		
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/logincustomer")
	@Produces("text/html")
	public InputStream loginCustomer(@QueryParam("email") String email,
			@QueryParam("password") String password)  {
		PatternsDAO deldao=new PatternsDAO();
		File f;
		User customer=deldao.getCustomerByEmail(email);
		if (!customer.isNull() && customer.getPassword().equals(password)) {
			request.getSession(true);
			HttpSession session = request.getSession();
			session.setAttribute("user",customer.getEmail());
			System.out.println("User: "+session.getAttribute("user"));
			f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\home.jsp");
		}else {
			f=new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\spca4\\error.jsp");
		}
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	

	@PUT
	@Path("/updatecustomer/xml")
	@Consumes("application/xml")
	public String updateEmployee(Customer customer) {
		PatternsDAO deldao=new PatternsDAO();
		//Employee emp=getEmployeeById(employee.getemployeeid()
		//		empdao.merge(employee);
		deldao.mergeCustomer(customer);
		return "Customer name changed to "+customer.getName();
	}
	//functions strangely
	@DELETE
	@Path("deletecustomer/xml")
	@Consumes("application/xml")
	public String deleteCustomer(Customer customer) {
		PatternsDAO deldao=new PatternsDAO();
		deldao.removeCustomer(customer);
		return "Customer "+customer.getName()+" has been deleted";
	}
	

}




