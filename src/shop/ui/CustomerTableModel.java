package shop.ui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import shop.core.Customer;

public class CustomerTableModel extends EntityTableModel {

	private static final int NUM_COL = 0;
	private static final int FNAME_COL = 1;
	private static final int LNAME_COL = 2;
	private static final int CONTACT_COL = 3;
	
	columnNames = new String[]{"Num", "First Name", "Last Name", "Contact"};
	private List<Customer> customers;
	
	public CustomerTableModel(List<Object> theEntities) {
		super(theEntities);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Customer temp = customers.get(row);
		
		switch(col) {
		case NUM_COL:
			return temp.getNum();
		case FNAME_COL:
			return temp.getFName();
		case LNAME_COL:
			return temp.getLName();
		case CONTACT_COL:
			return temp.getContact();
		default:
			return temp.getNum();
		}
	}
}