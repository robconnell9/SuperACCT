package au.org.comsup.model;

import java.text.NumberFormat;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;



/**
 * A {@link AcctEntryTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class AcctEntryTableModel extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1547542546403627396L;

	private enum Columns {
		ACCTTYPE, TRANSDATE, TRANSAMT, TRANS_DESC, ACCT_BALANCE
	}

	private ListModel<AcctEntry> listModel = new DefaultListModel<AcctEntry>();
	private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

	public AcctEntryTableModel() {
	}

	public final void setListModel(ListModel<AcctEntry> listModel) {
		if (this.listModel != null) {
			this.listModel.removeListDataListener(listModelChangeListener);
		}
		this.listModel = listModel;
		if (listModel != null) {
			listModel.addListDataListener(listModelChangeListener);
		}
		fireTableDataChanged();
	}

	public int getRowCount() {
		return listModel.getSize();
	}

	public int getColumnCount() {
		return Columns.values().length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object columnValue = null;
		AcctEntry acctEntry = (AcctEntry) listModel.getElementAt(rowIndex);
		Columns[] columns = Columns.values();
		Columns column = columns[columnIndex];
		switch (column) {
		case ACCTTYPE:
			columnValue = acctEntry.getAcctType();
			break;
		case TRANSDATE:
			columnValue = acctEntry.getTransDateAsString();
			break;
		case TRANSAMT:
			columnValue = NumberFormat.getCurrencyInstance().format(acctEntry.getTransAmt());
			//columnValue = acctEntry.getTransAmt();
			break;
		case TRANS_DESC:
			columnValue = acctEntry.getTransDescription();
			break;
		case ACCT_BALANCE:
			columnValue = NumberFormat.getCurrencyInstance().format(acctEntry.getAcctBalance());
			break;
		default:
		//	columnValue = getAddressObject(person, column);
			break;
		}

		return columnValue;
	}

	

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public String getColumnName(int column) {
		Columns[] columns = Columns.values();
		Columns columnsObj = columns[column];
		String columnName = null;
		switch (columnsObj) {
		case ACCTTYPE:
			columnName = "Acct Type";
			break;
		case TRANSDATE:
			columnName = "Trans Date";
			break;
		case TRANSAMT:
			columnName = "Trans Amt";
			break;
		case TRANS_DESC:
			columnName = "Trans Desc";
			break;
		case ACCT_BALANCE:
			columnName = "Acct Balance";
			break;


		default:
			break;
		}
		return columnName;
	}

	private class ListModelChangeListener implements ListDataListener {

		public void intervalAdded(ListDataEvent e) {
			fireTableDataChanged();
		}

		public void intervalRemoved(ListDataEvent e) {
			fireTableDataChanged();
		}

		public void contentsChanged(ListDataEvent e) {
			fireTableDataChanged();
		}

	}

}
