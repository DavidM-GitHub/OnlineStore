import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public abstract class User {
	protected String name;
    public abstract boolean isNull();
    public abstract String getName();

	public abstract String getAdmin();

	public abstract String getAddress();


	public abstract String getPaymentMethod() ;

	public abstract int getId() ;

	public abstract String getEmail() ;

	public abstract String getPassword() ;


	public abstract List<Product> getProduct() ;
}
