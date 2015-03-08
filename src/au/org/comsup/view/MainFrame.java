package au.org.comsup.view;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;

import au.org.comsup.controller.LoadEntriesAction;
import au.org.comsup.controller.SwingWorkerProgressModel;
import au.org.comsup.model.AcctEntry;
import au.org.comsup.model.ListAdapterListModel;

public class MainFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 4353611743416911021L;

	
	private ListAdapterListModel<AcctEntry> acctEntryListModel = new ListAdapterListModel<AcctEntry>();
	private SwingWorkerProgressModel swingWorkerProgressModel = new SwingWorkerProgressModel();
    private JProgressBar progressBar = new JProgressBar(swingWorkerProgressModel);
	private SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility = new SwingWorkerBasedComponentVisibility(progressBar);
	/*
	


;

	private OverviewPanel overviewPanel = new OverviewPanel();
	private LoadSpeedSimulationPanel loadSpeedSimulationPanel = new LoadSpeedSimulationPanel();

	
	*/
	private Component currentContent;

	public MainFrame() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		//progressBar.setStringPainted(true);
		//overviewPanel.setPersonList(personListModel);

		//setContent(overviewPanel);
		//getContentPane().add(loadSpeedSimulationPanel, BorderLayout.NORTH);
		//getContentPane().add(progressBar, BorderLayout.SOUTH);

		JMenuBar jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);
		initMenu(jMenuBar);

		setSize(605, 660);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Swing MVC Implementation Example");
		setLocationRelativeTo(null);
	}

	private void initMenu(JMenuBar jMenuBar) {
		initFileMenu(jMenuBar);
	}

	private void initFileMenu(JMenuBar jMenuBar) {
		JMenu fileMenu = new JMenu("Actions");
		jMenuBar.add(fileMenu);

		LoadEntriesAction loadPersonsAction = new LoadEntriesAction(
		    acctEntryListModel, this);
		//loadPersonsAction
		//		.addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
		loadPersonsAction
				.addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
	//	loadPersonsAction.setLoadSpeedModel(loadSpeedSimulationPanel
	//			.getPersonsLoadSpeedModel());

		JMenuItem loadMenuItem = new JMenuItem(loadPersonsAction);
		fileMenu.add(loadMenuItem);
		
		JMenuItem chooseMenuItem = new JMenuItem(loadPersonsAction);
        fileMenu.add(loadMenuItem);
		/*

		ClearListModelAction clearPersonsModelAction = new ClearListModelAction(
				personListModel);
		JMenuItem clearPersonsMenuItem = new JMenuItem(clearPersonsModelAction);
		fileMenu.add(clearPersonsMenuItem);
		*/
	}
/*	
    public File chooseFilea() {
      File selectedFile = null;
      JFileChooser fileChooser = new JFileChooser();
         int returnValue = fileChooser.showOpenDialog(null);
         if (returnValue == JFileChooser.APPROVE_OPTION) {
           selectedFile = fileChooser.getSelectedFile();
           System.out.println(selectedFile.getName());
         }
         return selectedFile;
    }
    */

	public void setContent(Component component) {
		Container contentPane = getContentPane();
		if (currentContent != null) {
			contentPane.remove(currentContent);
		}
		contentPane.add(component, BorderLayout.CENTER);
		currentContent = component;
		contentPane.doLayout();
		repaint();
	}

}
