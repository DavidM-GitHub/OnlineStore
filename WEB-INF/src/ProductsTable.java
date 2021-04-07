import java.util.ArrayList;

public class ProductsTable implements Table {

	@Override
	public String display(String id) {
		// TODO Auto-generated method stub
		String table="<table style=\"width: 900px;\" id=\"myTable\">";
		table+="<tr>";
		table+="<th onclick=\"sortTable(0)\">Product Number</th>";
		table+="<th onclick=\"sortTable(1)\">Image</th>"; 
		table+="<th onclick=\"sortTable(2)\">Name</th>"; 
		table+="<th onclick=\"sortTable(3)\">Price</th>"; 
		table+="<th onclick=\"sortTable(4)\">Category</th>"; 
		table+="<th onclick=\"sortTable(5)\">Manufacturer</th>"; 
		table+="<th onclick=\"sortTable(6)\">Ratings</th>"; 
		table+="<th onclick=\"sortTable(7)\">Comments</th>"; 
		table+="<th>Add to basket?</th>"; 
		table+="</tr>";	
		
		PatternsDAO deldao=new PatternsDAO();
		ArrayList<Product> products=(ArrayList<Product>) deldao.getAllProducts();

				for(int i=0;i<products.size();i++) {
					table+="<tr style=\"text-align:center;\">";
					table+="<td>"+products.get(i).getId()+"</td>";
					table+=	"<td><img src="+products.get(i).getImage()+" height=\"200\" width=\"200\"/></td>";
					table+=	"<td>"+products.get(i).getName()+"</td>";
					table+=	"<td>"+products.get(i).getPrice()+"</td>";
					table+=	"<td>"+products.get(i).getCategory()+"</td>";
					table+=	"<td>"+products.get(i).getManufacturer()+"</td>";
					table+=	"<td>"+products.get(i).getRatings()+"</td>";
					table+=	"<td>"+products.get(i).getComments()+"</td>";
					table+=	" <td><input id=\"myTextBox"+String.valueOf(i+1)+"\" type=\"checkbox\" name=\"myTextBox"+String.valueOf(i+1)+"\" value="+products.get(i).getId()+" /></td>";
					table+=	"</tr>"; 

				 }

//				
				table+="</table>";
				
				return table;
	}

}
