package com.mybank;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import com.mybank.ProcessCSV;
import com.mybank.TransactionBean;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@DisplayName("Test cases for calculating number of transaction")
public class TransactionTest{
	
	@Test
	@DisplayName("Validate number of transaction for a period of given account")
	public void test_validateNoOfTransaction() {
		try {
			TransactionBean transactionBean = new TransactionBean();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String accountId = "ACC998877";
			Date fromDate = format.parse("20/10/2018 12:00:00");
			Date toDate = format.parse("20/10/2018 19:00:00");
			transactionBean.setAccountId(accountId);
			transactionBean.setFromDate(fromDate);
			transactionBean.setToDate(toDate);
			ProcessCSV processCSV = new ProcessCSV();
			processCSV.parseCSV(transactionBean);
			assertEquals(5.5, transactionBean.getAmount());
			assertEquals(2, transactionBean.getTxnCount());
		}
		catch (ParseException parseException)
		{
			parseException.printStackTrace();
		}
	}
}

