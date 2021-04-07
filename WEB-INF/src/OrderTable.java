import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderTable implements Table{
    private HttpServletRequest request;

	@Override
	public String display(String id) {
		// TODO Auto-generated method stub
		PatternsDAO deldao=new PatternsDAO();
			
		
		ArrayList<ProductOrder> allOrders=(ArrayList<ProductOrder>) deldao.getAllProductOrders();
		ArrayList<ProductOrder> orders=new ArrayList<ProductOrder>();
				 for(ProductOrder order:allOrders) {
					 if(order.getCustomer().getId()==Integer.parseInt(id)&&order.getOrderstatus().equals("fulfilled")) {
						 orders.add(order);
					 }
				 }
				 if(orders.isEmpty()) {
					 return "error";
				 }
				 String table="<table style=\"width: 900px;\" id=\"myTable\">";
					table+="<tr>";
					table+="<th onclick=\"sortTable(0)\">Order ID</th>";
					table+="<th onclick=\"sortTable(1)\">Address</th>"; 
					table+="<th onclick=\"sortTable(2)\">Status</th>"; 
					table+="<th onclick=\"sortTable(3)\">Total</th>"; 
					table+="<th onclick=\"sortTable(4)\">Product Id</th>"; 
					table+="</tr>";	
				

					
							for(int i=0;i<orders.size();i++) {
								table+="<tr style=\"text-align:center;\">";
								table+="<td>"+orders.get(i).getId()+"</td>";
								table+=	"<td>"+orders.get(i).getAddress()+"</td>";
								table+=	"<td>"+orders.get(i).getOrderstatus()+"</td>";
								table+=	"<td>"+orders.get(i).getTotal()+"</td>";
								table+=	"<td>"+orders.get(i).getProduct().getId()+"</td>";
								table+=	"</tr>"; 

							 }
							table+="</table>";
		return table;
		}

}
