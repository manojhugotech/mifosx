package org.mifosplatform.portfolio.billingorder.data;

import org.joda.time.LocalDate;


public class BillDetailsData  {
private Long id;
private Long clientId;
private String addrNo;
private String clientName;
private String billPeriod;
private String street;
private String zipcode;
private String city;
private String state;
private String country;
private Double previousBalance;
private Double chargeAmount;
private Double adjustmentAmount;
private Double taxAmount;
private Double paidAmount;
private Double dueAmount;
private LocalDate billDate;
private LocalDate dueDate;
private String promotionalMessage;
private String billNo;
private String date;
private String transaction;
private String amount;
private String payments;
	public BillDetailsData(Long id, Long clientId, String addrNo, String clientName,
			String billPeriod, String street, String zipcode, String city,
			String state, String country, Double previousBal,
			Double chargeAmount, Double adjustmentAmount, Double taxAmount,
			Double paidAmount, Double dueAmount,LocalDate billDate,LocalDate duDate,String message) {

		this.id=id;
		this.addrNo=addrNo;
		this.adjustmentAmount=adjustmentAmount;
		this.billPeriod=billPeriod;
		this.chargeAmount=chargeAmount;
		this.city=city;
		this.clientId=clientId;
		this.clientName=clientName;
		this.country=country;
		this.dueAmount=dueAmount;
		this.paidAmount=paidAmount;
		this.previousBalance=previousBal;
		this.state=state;
		this.street=street;
		this.taxAmount=taxAmount;
		this.zipcode=zipcode;
		this.billDate=billDate;
		this.dueDate=duDate;
		this.promotionalMessage=message;

	}

	public Long getId() {
		return id;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getAddrNo() {
		if(addrNo!=null){
		return addrNo+",";}
		else{
			return "";}
	}

	public String getClientName() {
		if(clientName!=null){
		return clientName;}
		else{
			return "";}
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public String getStreet() {
		if(street!=null){
		return street;}
		else{
			return "";}
	}

	public String getZip() {
		if(zipcode!=null){
		return zipcode;}
		else{
			return "";}
	}

	public String getCity() {
		if(city!=null){
		return city+",";}
		else{
			return "";}
	}

	public String getState() {
		if(state!=null){
		return state+",";}
		else{
			return "";}
	}

	public String getCountry() {
		if(country!=null){
		return country+",";}
		else{
			return "";}
	}

	public Double getPreviousBalance() {
		return previousBalance;
	}

	public String getZipcode() {
		if(zipcode!=null){
		return zipcode;}
		else{
			return "";}
	}

	

	public String getPromotionalMessage() {
		return promotionalMessage;
	}

	public String getBillNo() {
		return billNo;
	}

	public String getDate() {
		return date;
	}

	public String getTransaction() {
		return transaction;
	}

	public String getAmount() {
		return amount;
	}

	public String getPayments() {
		return payments;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public Double getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public Double getDueAmount() {
		return dueAmount;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public String getMessage() {
		return promotionalMessage;
	}


}
