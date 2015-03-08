package au.org.comsup.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;




public class AcctEntry implements Serializable {
  private static final long serialVersionUID = 1L;
  private String acctEntryId;
  private String acctType;
  private Date transDate;
  private BigDecimal transAmt; 
  private BigDecimal acctBalance;
  private String transDescription;

  public AcctEntry(String acctType, Date transDate, BigDecimal transAmt,
      BigDecimal acctBalance, String transDescription) {
    super();
    this.acctType = acctType;
    this.transDate = transDate;
    this.transAmt = transAmt;
    this.acctBalance = acctBalance;
    this.transDescription = transDescription;
  }
  

  
  public AcctEntry(){
    
  }

  public String getAcctType() {
    return acctType;
  }
  public void setAcctType(String acctType) {
    this.acctType = acctType;
  }

  public BigDecimal getTransAmt() {
    return transAmt;
  }
  public void setTransAmt(BigDecimal transAmt) {
    this.transAmt = transAmt;
  }
  public BigDecimal getAcctBalance() {
    return acctBalance;
  }
  public void setAcctBalance(BigDecimal acctBalance) {
    this.acctBalance = acctBalance;
  }
  public String getTransDescription() {
    return transDescription;
  }
  public void setTransDescription(String transDescription) {
    this.transDescription = transDescription;
  }

  public String getAcctEntryId() {
    return acctEntryId;
  }
  public void setAcctEntryId(String acctEntryId) {
    this.acctEntryId = acctEntryId;
  }

  public Date getTransDate() {
    return transDate;
  }
  
  public String getTransDateAsString() {
 
    SimpleDateFormat outFormat = new SimpleDateFormat("ddMMyyyy");
    String transDate = outFormat.format(this.transDate);
    return transDate;

  }

  public void setTransDate(Date transDate) {
    this.transDate = transDate;
  }
  
  public void setTransDate(String transDateddMMYYYY) {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyy");
    DateTime transDateTime = formatter.parseDateTime(transDateddMMYYYY);
    Date transDate = transDateTime.toDate();
    this.setTransDate(transDate);
  }

 

}
