package org.mifosplatform.portfolio.billingorder.data;

import java.util.ArrayList;
import java.util.List;

import org.mifosplatform.portfolio.financialtransaction.data.FinancialTransactionsData;

public class DataBeanMaker {
public ArrayList<BillingData> getDataBeanList(BillingData billDetails, List<FinancialTransactionsData> financialTransactionsDatas) {
ArrayList<BillingData> dataBeanList = new ArrayList<BillingData>();

dataBeanList.add(new BillingData(billDetails.getBillNo(),billDetails.getClientId(),billDetails.getAddrNo(),billDetails.getClientName(),
		billDetails.getBillPeriod(),billDetails.getStreet(),billDetails.getZip(),billDetails.getCity(),
		billDetails.getState(),billDetails.getCountry(),billDetails.getPreviousBalance(),
		billDetails.getAdjustmentAmount(),billDetails.getChargeAmount(),billDetails.getTaxAmount(),
		billDetails.getPayments(),billDetails.getDueAmount(),billDetails.getBillDate(),billDetails.getDueDate(),billDetails.getMessage()));
for(FinancialTransactionsData data: financialTransactionsDatas){
	dataBeanList.add(new BillingData(data.getTransactionId(),data.getTransactionType(),data.getTransDate(),data.getAmount()));
}


return dataBeanList;
}



}