package com.mybank;

import java.util.Date;

public class TransactionBean{
    private String accountId;
    private Date fromDate;
    private Date toDate;
    private double amount;
    private long txnCount;
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(long txnCount) {
        this.txnCount = txnCount;
    }
}