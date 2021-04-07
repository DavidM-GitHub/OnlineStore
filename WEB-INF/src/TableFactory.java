
public class TableFactory {

	public Table getTable(String tableType) {
		if(tableType == null) {
			return null;
		}
		if(tableType.equalsIgnoreCase("Product")) {
			return new ProductsTable();
		}else if(tableType.equalsIgnoreCase("Order")) {
			return new OrderTable();
		}else if(tableType.equalsIgnoreCase("Customer")) {
			return new CustomerTable();
		}else if(tableType.equalsIgnoreCase("Cart")) {
			return new CartTable();
		}
		return null;
	}
}
