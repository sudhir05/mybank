package com.mybank;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import java.text.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.net.URL;

public class ProcessCSV
{
    public void parseCSV(TransactionBean transactionBean)
    {
        try{
            String txnRow = null;
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("config/txn.csv").getFile());
            BufferedReader csvReader = new BufferedReader(new FileReader(file));
            int iteration = 0;
            String [] header = {"transactionId","fromAccountId","toAccountId","createdAt","amount","transactionType","relatedTransaction"};
            while((txnRow = csvReader.readLine()) !=null)
            {
                if(iteration == 0)
                {
                    iteration++;
                    continue;
                }
                //map the value
                System.out.println("txnrow"+txnRow);
                String [] txnDetails = txnRow.split(",");
                HashMap<String,String> txnMapItem = new HashMap<String,String>();
                for(int txnCount = 0; txnCount < txnDetails.length; txnCount++)
                {
                    txnMapItem.put(header[txnCount],txnDetails[txnCount]);
                }              
                processTransaction(txnMapItem, transactionBean);               
            }
            csvReader.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            
        }
    }
    public static void main (String [] args)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        if(args.length < 3)
        {
            System.out.println("Input should be in the format of accountId:<xxxx> from:<date> to:<date>");
        }
        else
        {
            try{
               
                String accountId = args[0];
                Date fromDate = format.parse(args[1]);
                Date toDate = format.parse(args[2]);
                TransactionBean transactionBean = new TransactionBean();
                transactionBean.setAccountId(accountId);
                transactionBean.setFromDate(fromDate);
                transactionBean.setToDate(toDate);
                ProcessCSV processCSV = new ProcessCSV();
                processCSV.parseCSV(transactionBean);
                System.out.println("Relative balance for the period is:$"+transactionBean.getAmount());
                System.out.println("Number of transactions included is:"+transactionBean.getTxnCount());
            }
            catch(ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException)
            {
                arrayIndexOutOfBoundsException.printStackTrace();
            }
            catch (ParseException parseException)
            {
                parseException.printStackTrace();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
    //process transaction based on timeframe and accountId
    private void processTransaction(HashMap<String,String> txnMapItem, TransactionBean transactionBean)
    { 
        try{
            if(txnMapItem.get("fromAccountId").equals(transactionBean.getAccountId())){
                if(checkTransactionTimeAndType(txnMapItem, transactionBean))
                {
                    transactionBean.setAmount(transactionBean.getAmount() - Double.parseDouble(txnMapItem.get("amount")));
                    transactionBean.setTxnCount(transactionBean.getTxnCount() + 1);
                    System.out.println("from:"+transactionBean.getTxnCount());
                }
                else if(txnMapItem.get("relatedTransaction") != null)
                {
                    System.out.println("related transaction"+txnMapItem.get("relatedTransaction"));
                    transactionBean.setAmount(transactionBean.getAmount() + Double.parseDouble(txnMapItem.get("amount")));
                    transactionBean.setTxnCount(transactionBean.getTxnCount() - 1);
                    System.out.println("reversal:"+transactionBean.getTxnCount());
                }
            }
            else if(txnMapItem.get("toAccountId").toString().equals(transactionBean.getAccountId())){
                if(checkTransactionTimeAndType(txnMapItem, transactionBean)){
                    transactionBean.setAmount(transactionBean.getAmount() + Double.parseDouble(txnMapItem.get("amount")));
                    transactionBean.setTxnCount(transactionBean.getTxnCount() + 1);
                    System.out.println("to:"+transactionBean.getTxnCount());
                }   
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //if it is payment and come under timeframe
    private boolean checkTransactionTimeAndType(HashMap<String,String> txnMapItem, TransactionBean transactionBean)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        boolean transactionStatus = false;
        try{
            Date txnCreatedDateTime = format.parse(txnMapItem.get("createdAt"));
            if (txnMapItem.get("transactionType").equals("PAYMENT")) {
                if (transactionBean.getFromDate().compareTo(txnCreatedDateTime) <= 0 && 
                        transactionBean.getToDate().compareTo(txnCreatedDateTime) >= 0){
                    System.out.println("date matched");
                    transactionStatus = true;
                }
            }
            else if(txnMapItem.get("transactionType").equals("REVERSAL")){
                transactionStatus =  false;
            }
        }
        catch(ParseException parseException){
            parseException.printStackTrace();
        }  
        return transactionStatus;
    }  
    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
}

