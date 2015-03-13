package au.org.comsup.view;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;

import au.org.comsup.model.AcctEntry;
import au.org.comsup.model.AcctEntryTableModel;
import au.org.comsup.model.ListAdapterListModel;
import au.org.comsup.model.ListModelSelection;

/**
 * The main panel that holds a {@link JList}, {@link JTable}, {@link JTree} and
 * {@link JComboBox} that display the loaded {@link Person} objects. All the
 * components are synchronized by models so that they will all show the same
 * data and reflect the same selection. This panel is an example of how to
 * implement a MVC (model-view-controller) pattern with swing.
 *
 * @author René Link <a
 *         href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 *         intersystems.com]</a>
 *
 */
public class OverviewPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4471606875093169644L;

	private AcctEntryTableModel acctEntryTableModel = new AcctEntryTableModel();
	private ListAdapterListModel<AcctEntry> acctEntryListModel = new ListAdapterListModel<AcctEntry>();

	private ListModelSelection<AcctEntry> listModelSelection = new ListModelSelection<AcctEntry>();
	private ListSelectionModel selectionModel = new DefaultListSelectionModel();

	private JTable acctEntryTable = new JTable(acctEntryTableModel);

	


	public void setAcctEntryList(ListAdapterListModel<AcctEntry> acctEntryListModel) {

		acctEntryTableModel.setListModel(acctEntryListModel);

		listModelSelection.setListModels(acctEntryListModel, selectionModel);
	}

	public OverviewPanel() {
		setLayout(null);
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		acctEntryTable.setSelectionModel(selectionModel);
		


		acctEntryTable.setSelectionModel(selectionModel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 580, 130);
		add(scrollPane);

		scrollPane.setViewportView(acctEntryTable);



		

}
}
