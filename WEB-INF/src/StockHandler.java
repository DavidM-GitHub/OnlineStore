
public class StockHandler {

	private static StockHandler instance=null;
	
	private StockHandler() {
		
	}
	  
	public static StockHandler getInstance() {
		synchronized(StockHandler.class) {
			if(instance==null) {
				instance=new StockHandler();
				}			
		}
		return instance;
	}
	
	public int getStock(Product product) {
		return product.getStock();
		}
	 
	public void reduceStock(Product product) {
		PatternsDAO deldao=new PatternsDAO();
		product.setStock(product.getStock()-1);
		deldao.mergeProduct(product);
	}
}
