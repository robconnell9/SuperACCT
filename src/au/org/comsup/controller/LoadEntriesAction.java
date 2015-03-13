package au.org.comsup.controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.org.comsup.model.AcctEntry;
import au.org.comsup.model.ListAdapterListModel;

/**
 * 
 *
 */
public class LoadEntriesAction extends AbstractAction {

  /**
	 *
	 */
  private static final long serialVersionUID = 2636985714796751517L;

  private ListAdapterListModel<AcctEntry> acctEntryListModel;

  private JFrame parentJFrame;

  private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();

  /*
   * private BoundedRangeModel loadPersonsSpeedModel = new
   * DefaultBoundedRangeModel();
   */
  public LoadEntriesAction(ListAdapterListModel<AcctEntry> acctEntryListModel,
      JFrame frame) {
    this.parentJFrame = frame;
    this.acctEntryListModel = acctEntryListModel;
  }

  @Override
  public Object[] getKeys() {
    return new Object[] { Action.NAME };
  }

  @Override
  public Object getValue(String key) {
    if (Action.NAME.equals(key)) {
      return "Load";
    } else if (Action.ACCELERATOR_KEY.equals(key)) {
      return KeyStroke.getKeyStroke('L');
    }
    return super.getValue(key);
  }

  public void actionPerformed(ActionEvent e) {
    File selectedFile = chooseFile();
   // int recsProcessed = extractFromFile(selectedFile);
   


    LoadEntriesWorker loadEntriesWorker = new LoadEntriesWorker(
        acctEntryListModel, selectedFile);
    // loadPersonsWorker.setLoadSpeedModel(loadPersonsSpeedModel);
    for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
      swingWorkerPropertyChangeListener
          .attachPropertyChangeListener(loadEntriesWorker);
    }
    JOptionPane.showMessageDialog(this.getParentJFrame(), getLineCount(selectedFile)
        + " Records Processed...");
    System.out.println("xxx");
    loadEntriesWorker.execute();
  }

  public File chooseFile() {
    File selectedFile = null;
    JFileChooser fileChooser = new JFileChooser("C:/development/Supercsv");
    fileChooser.setApproveButtonText("Load Transactions From File");
    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
      fileChooser.setCurrentDirectory(selectedFile.getParentFile());
      System.out.println("ÿy" + selectedFile.getParentFile());
      System.out.println(selectedFile.getName());
    }
    return selectedFile;
  }
  
  public int extractFromFile(File acctEntriesFile) {
    FileReader fr = null;
    BufferedReader br = null;
    int recordsProcessed = 0;
    try {
      fr = new FileReader(acctEntriesFile);
      br = new BufferedReader(fr);
      String s;

      while ((s = br.readLine()) != null) {
        System.out.println(s);
        String[] fieldStrings = s.split(",");

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime transDateTime = formatter.parseDateTime(fieldStrings[1]);
        Date transDate = transDateTime.toDate();
      //  String[] transAmounts = StringUtils.substringsBetween(fieldStrings[2] , "\"", "\"");
      //  String[] balanceAmounts = StringUtils.substringsBetween(fieldStrings[4] , "\"", "\"");
        AcctEntry acctEntry = new AcctEntry(getValueBetweenDoubleQuotes(fieldStrings[0]), transDate, new BigDecimal(getValueBetweenDoubleQuotes(fieldStrings[2])),
            new BigDecimal(getValueBetweenDoubleQuotes(fieldStrings[4])), getValueBetweenDoubleQuotes(fieldStrings[3]));
        acctEntryListModel.addEntry(acctEntry);
        recordsProcessed++;
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      // fr.close();
    }
    return recordsProcessed;
  }
  
  public String getValueBetweenDoubleQuotes(String doubleQuotedString) {
    String[] transAmounts = StringUtils.substringsBetween(doubleQuotedString , "\"", "\"");
    return transAmounts[0];
    
  }
  
  public int getLineCount(File chosenFile) {
	  int lines=0;
  FileReader fr = null;
  BufferedReader br = null;
  try {
    fr = new FileReader(chosenFile);
    br = new BufferedReader(fr);
    while (br.readLine() != null) lines++;
    br.close();
  } catch (Exception e) {
	  e.printStackTrace();
  }
  return lines;
  }

  /*
   * public void chooseFile() { final JFileChooser exportFileChooser = new
   * JFileChooser();
   * exportFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
   * exportFileChooser.setApproveButtonText("Export");
   * 
   * final JButton exportButton = new JButton("Export text file");
   * exportButton.addActionListener(new ActionListener() {
   * 
   * @Override public void actionPerformed(ActionEvent e) { int returnVal =
   * exportFileChooser.showSaveDialog(exportButton .getParent());
   * 
   * if (returnVal == JFileChooser.APPROVE_OPTION) { File outputFile =
   * exportFileChooser.getSelectedFile(); if (outputFileIsValid(outputFile)) {
   * System.out.println("outputFile" + outputFile.getName()); } } }
   * 
   * private boolean outputFileIsValid(File outputFile) { boolean fileIsValid =
   * false; if (outputFile.exists()) { int result =
   * JOptionPane.showConfirmDialog( exportButton.getParent(),
   * "File exists, overwrite?", "File exists",
   * JOptionPane.YES_NO_CANCEL_OPTION); switch (result) { case
   * JOptionPane.YES_OPTION: fileIsValid = true; break; default: fileIsValid =
   * false; } } else { fileIsValid = true; } return fileIsValid; } }); }
   */

  public void addSwingWorkerPropertyChangeListener(
      SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
    swingWorkerPropertyChangeListeners.add(swingWorkerPropertyChangeListener);
  }

  public void removeSwingWorkerPropertyChangeListener(
      SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
    swingWorkerPropertyChangeListeners
        .remove(swingWorkerPropertyChangeListener);
  }

  public JFrame getParentJFrame() {
    return parentJFrame;
  }

  public void setParentJFrame(JFrame parentJFrame) {
    this.parentJFrame = parentJFrame;
  }

  /*
   * public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
   * this.loadPersonsSpeedModel = loadPersonsSpeedModel; }
   */
}
