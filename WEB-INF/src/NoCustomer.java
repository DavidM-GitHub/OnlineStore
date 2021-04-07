import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class NoCustomer extends User{

	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Unavailable";
	}
	

	public String getAdmin() {
		return "Unavailable";
	}



	public String getAddress() {
		return "Unavailable";
	}


	public String getPaymentMethod() {
		return "Unavailable";
	}

	@XmlElement
	public int getId() {
		return 0;
	}
	@XmlElement
	public String getEmail() {
		return "Unavailable";
	}

	@XmlElement
	public String getPassword() {
		return "Unavailable";
	}


	@XmlElement
	public List<Product> getProduct() {
		return null;
	}



}
