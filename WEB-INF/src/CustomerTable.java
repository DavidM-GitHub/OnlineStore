import java.util.ArrayList;

public class CustomerTable implements Table{

	@Override
	public String display(String id) {
		// TODO Auto-generated method stub
		PatternsDAO deldao=new PatternsDAO();

		ArrayList<Customer> customers=(ArrayList<Customer>) deldao.getAllCustomers();
	//	
				 
			String table="<table style=\"width: 900px;\" id=\"myTable\">";
			table+="<tr>";
			table+="<th onclick=\"sortTable(0)\">Customer ID</th>";
			table+="<th onclick=\"sortTable(1)\">Name</th>"; 
			table+="<th onclick=\"sortTable(2)\">Address</th>"; 
			table+="<th onclick=\"sortTable(3)\">Email</th>"; 
			table+="<th onclick=\"sortTable(4)\">Admin</th>"; 
			table+="<th onclick=\"sortTable(5)\">Payment Method</th>"; 
			table+="</tr>";	
		

			
					for(int i=0;i<customers.size();i++) {
						table+="<tr style=\"text-align:center;\">";
						table+="<td>"+customers.get(i).getId()+"</td>";
						table+=	"<td>"+customers.get(i).getName()+"</td>";
						table+=	"<td>"+customers.get(i).getAddress()+"</td>";
						table+=	"<td>"+customers.get(i).getEmail()+"</td>";
						table+=	"<td>"+customers.get(i).getAdmin()+"</td>";
						table+=	"<td>"+customers.get(i).getPaymentMethod()+"</td>";
						table+=	"</tr>"; 

					 }

//					
					table+="</table>";
		return table;
	}

}
