package au.org.comsup.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.bson.types.ObjectId;

import au.org.comsup.model.AcctEntry;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;



public class AcctService {
	public static DB db = null;
	

	

  
  public  void getDB ()  {
  	try {
  	if (AcctService.db == null) {
      MongoClientURI uri  = new MongoClientURI(""); 
      
      MongoClient client = new MongoClient(uri);
      AcctService.db = client.getDB(uri.getDatabase());
      //client.close();
  	}
  	} catch (Exception e) {
  		e.printStackTrace();
  	}
  }
  
  public AcctEntry addAcctEntry(String acctType, String transDateddmmyyyy, String transDesc, BigDecimal transAmt,BigDecimal acctBalance) {
    try {
  	getDB();
    DBCollection acctEntries = db.getCollection("acctEntry");
  	BasicDBObject acctEntry = new BasicDBObject();
  	acctEntry.put("acctType", acctType);
  	acctEntry.put("transDate", transDateddmmyyyy);
  	acctEntry.put("transDesc", transDesc);
  	acctEntry.put("transAmt", getCentsForBigDecimal(transAmt));
  	acctEntry.put("acctBalance", getCentsForBigDecimal(acctBalance));


   
  	acctEntries.insert(acctEntry);
  	return getAcctEntryFromObject(acctEntry);
    } catch (Exception e ) {
      e.printStackTrace();
    }
    return null;
  	
  }

  public AcctEntry addAcctEntry(AcctEntry acctEntryParam) {

    SimpleDateFormat outFormat = new SimpleDateFormat("ddMMyyyy");
    String transDate = outFormat.format(acctEntryParam.getTransDate());
    

    return addAcctEntry(acctEntryParam.getAcctType(), transDate, acctEntryParam.getTransDescription(), acctEntryParam.getTransAmt(),acctEntryParam.getAcctBalance());
    
  }
  
  public AcctEntry getAcctEntryFromObject(DBObject doc) {
    AcctEntry acctEntry = new AcctEntry();
    acctEntry.setAcctEntryId(((ObjectId) doc.get("_id")).toStringMongod());
    acctEntry.setAcctBalance(getDollarsCents((Integer) doc.get("acctBalance")));
    acctEntry.setAcctType((String) doc.get("acctType"));
    acctEntry.setTransAmt(getDollarsCents((Integer)  doc.get("transAmt")));
    acctEntry.setTransDate((String) doc.get("transDate"));
    acctEntry.setTransDescription((String) doc.get("transDesc"));
    

  return acctEntry;
   
 }
  
  public int getCentsForBigDecimal(BigDecimal amt) {
    amt = amt.multiply(new BigDecimal(100.0));
    return amt.intValue();
 }
  
  public BigDecimal getDollarsCents(int numberOfCents) {
    BigDecimal valueAsDollars = new BigDecimal(numberOfCents/100);
    return valueAsDollars;
 }
 
}
