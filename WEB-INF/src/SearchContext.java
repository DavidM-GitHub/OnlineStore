
public class SearchContext {
	   private SearchStrategy strategy;

	   public SearchContext(SearchStrategy strategy) {
		   this.strategy=strategy;
	   }
	   
	   public String executeStrategy(String criteria) {
		   return strategy.search(criteria);
	   }
}
