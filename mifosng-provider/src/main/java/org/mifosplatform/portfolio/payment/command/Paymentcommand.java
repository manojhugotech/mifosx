package org.mifosplatform.portfolio.payment.command;

import java.math.BigDecimal;

import org.joda.time.LocalDate;


public class Paymentcommand {

	private final Long clientId;
	private final Long payment_id;
	private final Long externalId;
	private final Long statement_id;
	private final String payment_code;
	private final String remarks;
	private final BigDecimal amount_paid;
	private final LocalDate payment_date;


public Paymentcommand(final Long clientId,final Long payment_id,final Long externalId,final Long statement_id,final String payment_code,
		final String remarks, final BigDecimal amount_paid,final LocalDate payment_date )

{
	this.clientId=clientId;
	this.payment_id=payment_id;
	this.externalId=externalId;
	this.statement_id=statement_id;
	this.payment_code=payment_code;
	this.remarks=remarks;
	this.amount_paid=amount_paid;
	this.payment_date=payment_date;
}


public Long getClientId() {
	return clientId;
}


public Long getPayment_id() {
	return payment_id;
}


public Long getExternalId() {
	return externalId;
}


public Long getStatement_id() {
	return statement_id;
}


public String getPayment_code() {
	return payment_code;
}




public String getRemarks() {
	return remarks;
}


public BigDecimal getAmount_paid() {
	return amount_paid;
}




public LocalDate getPayment_date() {
	return payment_date;
}


}
