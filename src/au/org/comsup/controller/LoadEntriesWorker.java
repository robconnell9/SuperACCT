package au.org.comsup.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import au.org.comsup.model.AcctEntry;
import au.org.comsup.model.ListAdapterListModel;
import au.org.comsup.service.AcctService;



public class LoadEntriesWorker extends SwingWorker<List<AcctEntry>, AcctEntry> {

	private volatile int maxProgress;
	private int progressedItems;
	private ListAdapterListModel<AcctEntry> acctEntryListModel;
	//private BoundedRangeModel loadAcctEntrysSpeedModel = new DefaultBoundedRangeModel();

	public LoadEntriesWorker(ListAdapterListModel<AcctEntry> AcctEntryListModel) {
		this.acctEntryListModel = AcctEntryListModel;
	}

	@Override
	protected List<AcctEntry> doInBackground() throws Exception {
	  List<AcctEntry> AcctEntrys = new ArrayList<AcctEntry>();
	  AcctService service = new AcctService();
	  for (AcctEntry acctEntry:acctEntryListModel.getList()) {
	    AcctEntry acctEntryStored = service.addAcctEntry(acctEntry);
	    AcctEntrys.add(acctEntryStored);
	    
	  }
	 
	 return AcctEntrys;
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
}
