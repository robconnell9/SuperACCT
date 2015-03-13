package au.org.comsup.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.org.comsup.model.AcctEntry;
import au.org.comsup.model.ListAdapterListModel;
import au.org.comsup.service.AcctService;



public class LoadEntriesWorker extends SwingWorker<List<AcctEntry>, AcctEntry> {

	private volatile int maxProgress;
	private int progressedItems;
	private ListAdapterListModel<AcctEntry> acctEntryListModel;
	private File chosenFile;
	//private BoundedRangeModel loadAcctEntrysSpeedModel = new DefaultBoundedRangeModel();

	public LoadEntriesWorker(ListAdapterListModel<AcctEntry> AcctEntryListModel, File chosenFile) {
		this.acctEntryListModel = AcctEntryListModel;
		this.chosenFile = chosenFile;
		
	}

	@Override
	protected List<AcctEntry> doInBackground() throws Exception {
	  List<AcctEntry> acctEntrysFromFile = new ArrayList<AcctEntry>();
	  extractFromFile(chosenFile, acctEntrysFromFile);
	  acctEntryListModel.clear();
	 
	  AcctService service = new AcctService();
	  maxProgress = acctEntrysFromFile.size();
	  List<AcctEntry> acctEntrysAfterStore = new ArrayList<AcctEntry>();
	  for (AcctEntry acctEntry:acctEntrysFromFile) {
	    AcctEntry acctEntryStored = service.addAcctEntry(acctEntry);
	    acctEntrysAfterStore.add(acctEntryStored);
	    publish(acctEntryStored);
	    
	  }
	 
	 return acctEntrysAfterStore;
	}

	@Override
	protected void process(List<AcctEntry> chunks) {
		acctEntryListModel.addAll(chunks);
		progressedItems = progressedItems + chunks.size();
		setProgress(calcProgress(progressedItems));
	}

	private int calcProgress(int progressedItems) {
		int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
		return progress;
	}

	private void sleepAWhile() {
		try {
			Thread.yield();
			long timeToSleep = 0;
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
		}
	}
/*
	private long getTimeToSleep() {
		return loadAcctEntrysSpeedModel.getValue();
	}

	public void setLoadSpeedModel(BoundedRangeModel loadAcctEntrysSpeedModel) {
		this.loadAcctEntrysSpeedModel = loadAcctEntrysSpeedModel;

	}
	*/
	  
	  public void extractFromFile(File acctEntriesFile, List<AcctEntry> acctEntriesFromFile) {
	    FileReader fr = null;
	    BufferedReader br = null;
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
	        acctEntriesFromFile.add(acctEntry);
	      }

	    } catch (Exception ex) {
	      ex.printStackTrace();
	    } finally {
	      // fr.close();
	    }
	  }
	  
	  public String getValueBetweenDoubleQuotes(String doubleQuotedString) {
	    String[] transAmounts = StringUtils.substringsBetween(doubleQuotedString , "\"", "\"");
	    return transAmounts[0];
	    
	  }
}
