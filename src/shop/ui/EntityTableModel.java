package shop.ui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import shop.core.Customer;

abstract public class EntityTableModel extends AbstractTableModel {
	String[] columnNames;
	protected List<Object> entities;
	
	public EntityTableModel(List<Object> theEntities) {
		entities = theEntities;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override 
	public int getRowCount() {
		return entities.size();
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	abstract public Object getValueAt(int row, int col);
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
