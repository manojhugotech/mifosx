package org.mifosplatform.portfolio.billingorder.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.portfolio.billingorder.commands.InvoiceTaxCommand;
import org.mifosplatform.portfolio.billingorder.data.BillingOrderData;
import org.mifosplatform.portfolio.billingorder.domain.InvoiceTax;
import org.mifosplatform.portfolio.taxmaster.data.TaxMappingRateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GenerateBill{

	private final BillingOrderReadPlatformService billingOrderReadPlatformService;
	private final BillingOrderWritePlatformService billingOrderWritePlatformService;


	@Autowired
	public GenerateBill(BillingOrderReadPlatformService billingOrderReadPlatformService,
			BillingOrderWritePlatformService billingOrderWritePlatformService) {
		this.billingOrderReadPlatformService = billingOrderReadPlatformService;
		this.billingOrderWritePlatformService = billingOrderWritePlatformService;

	}

	BigDecimal pricePerMonth = null;
	LocalDate startDate = null;
	LocalDate endDate = null;
	BigDecimal price = null;
	LocalDate invoiceTillDate = null;
	LocalDate nextbillDate = null;
	BillingOrderCommand billingOrderCommand = null;




	public boolean isChargeTypeNRC(BillingOrderData billingOrderData) {
		boolean chargeType = false;
		if (billingOrderData.getChargeType().equals("NRC")) {
			chargeType = true;
		}
		return chargeType;
	}

	public boolean isChargeTypeRC(BillingOrderData billingOrderData) {
		boolean chargeType = false;
		if (billingOrderData.getChargeType().equals("RC")) {
			chargeType = true;
		}
		return chargeType;
	}

	public boolean isChargeTypeUC(BillingOrderData billingOrderData) {
		boolean chargeType = false;
		if (billingOrderData.getChargeType().equals("UC")) {
			chargeType = true;
		}
		return chargeType;
	}

	public BillingOrderCommand getProrataMonthlyFirstBill(
			BillingOrderData billingOrderData) {

		startDate = new LocalDate();
		int currentDay = startDate.getDayOfMonth();
		int endOfMonth = startDate.dayOfMonth().withMaximumValue()
				.getDayOfMonth();
		int totalDays = endOfMonth - currentDay + 1;

		endDate = startDate.dayOfMonth().withMaximumValue();

		pricePerMonth = billingOrderData.getPrice();

		BigDecimal pricePerDay = pricePerMonth.divide(new BigDecimal(30), 2,
				RoundingMode.HALF_UP);

		invoiceTillDate = endDate;
		nextbillDate = invoiceTillDate.plusDays(1);

		BigDecimal price = pricePerDay.multiply(new BigDecimal(totalDays));

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;

	}

	public BillingOrderCommand getNextMonthBill(
			BillingOrderData billingOrderData) {

		startDate = new LocalDate(billingOrderData.getNextBillableDate());
		endDate = new LocalDate(billingOrderData.getInvoiceTillDate())
				.plusMonths(billingOrderData.getChargeDuration()).dayOfMonth()
				.withMaximumValue();

		invoiceTillDate = endDate;
		nextbillDate = endDate.plusDays(1);

		price = billingOrderData.getPrice();

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;

	}



	public List<InvoiceTaxCommand> generateInvoiceTax(List<TaxMappingRateData> taxMappingRateDatas,BigDecimal price,Long clientId ) {

		BigDecimal taxPercentage = null;
		String taxCode = null;
		BigDecimal taxAmount = null;
		List<InvoiceTaxCommand> invoiceTaxCommands = new ArrayList<InvoiceTaxCommand>();
		InvoiceTaxCommand invoiceTaxCommand = null;
		if (taxMappingRateDatas != null) {

			for (TaxMappingRateData taxMappingRateData : taxMappingRateDatas) {

				taxPercentage = taxMappingRateData.getRate();
				taxCode = taxMappingRateData.getTaxCode();
				taxAmount = price.multiply(
						taxPercentage.divide(new BigDecimal(100)));

				invoiceTaxCommand = new InvoiceTaxCommand(
						clientId, null, null, taxCode, null,
						taxPercentage, taxAmount);
				invoiceTaxCommands.add(invoiceTaxCommand);
			}

		}
		return invoiceTaxCommands;

	}


	public BillingOrderCommand getMonthyBill(BillingOrderData billingOrderData) {

		if (billingOrderData.getInvoiceTillDate() == null) {
			startDate = new LocalDate();

			endDate = startDate
					.plusMonths(billingOrderData.getChargeDuration())
					.minusDays(1);

		} else if (billingOrderData.getInvoiceTillDate() != null) {

			startDate = new LocalDate(billingOrderData.getNextBillableDate());
			endDate = startDate
					.plusMonths(billingOrderData.getChargeDuration()).minusDays(1);

			// invoiceTillDate = endDate;
			// nextbillDate = invoiceTillDate.plusDays(1);

		}

		invoiceTillDate = endDate;
		nextbillDate = invoiceTillDate.plusDays(1);

		price = billingOrderData.getPrice();

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;

	}

	public BillingOrderCommand getProrataWeeklyFirstBill(
			BillingOrderData billingOrderData) {

		startDate = new LocalDate();
		endDate = startDate.dayOfWeek().withMaximumValue();

		int startDateOfWeek = startDate.getDayOfMonth();

		int endDateOfWeek = startDate.dayOfWeek().withMaximumValue()
				.getDayOfMonth();

		int totalDays = 0;

		int diff = Math.abs(endDateOfWeek - startDateOfWeek);
		int numberOfdaysOfMonth = startDate.dayOfMonth().withMaximumValue()
				.getDayOfMonth();
		if (diff >= 7) {
			totalDays = numberOfdaysOfMonth - diff + 1;
		} else {
			totalDays = endDateOfWeek - startDateOfWeek + 1;
		}

		BigDecimal pricePerDay = billingOrderData.getPrice().divide(
				new BigDecimal(7), 2, RoundingMode.HALF_UP);

		price = pricePerDay.multiply(new BigDecimal(totalDays));

		invoiceTillDate = endDate;
		nextbillDate = endDate.plusDays(1);

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;

	}

	public BillingOrderCommand getNextWeeklyBill(
			BillingOrderData billingOrderData) {

		startDate = new LocalDate(billingOrderData.getNextBillableDate());

		endDate = startDate.dayOfWeek().withMaximumValue();

		price = billingOrderData.getPrice();

		invoiceTillDate = endDate;
		nextbillDate = endDate.plusDays(1);

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;

	}

	public BillingOrderCommand getWeeklyBill(BillingOrderData billingOrderData) {

		if (billingOrderData.getInvoiceTillDate() == null) {
			startDate = new LocalDate();

			endDate = startDate.plusWeeks(1).minusDays(1);

		} else if (billingOrderData.getInvoiceTillDate() != null) {

			startDate = new LocalDate(billingOrderData.getNextBillableDate());
			endDate = startDate.plusWeeks(1).minusDays(1);

		}
		price = billingOrderData.getPrice();
		invoiceTillDate = endDate;
		nextbillDate = invoiceTillDate.plusDays(1);

		price = billingOrderData.getPrice();

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;
	}

	public BillingOrderCommand getOneTimeBill(BillingOrderData billingOrderData) {

		LocalDate startDate = new LocalDate();
		LocalDate endDate = startDate;
		LocalDate invoiceTillDate = startDate;
		LocalDate nextbillDate = startDate;
		BigDecimal price = billingOrderData.getPrice();
		billingOrderData.setChargeDuration(0);

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(billingOrderData.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = generateInvoiceTax(taxMappingRateDatas, price,billingOrderData.getClientId());
		List<InvoiceTax> listOfTaxes = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);

		billingOrderCommand = new BillingOrderCommand(billingOrderData.getClientOrderId(),
				billingOrderData.getOderPriceId(),
				billingOrderData.getClientId(), startDate.toDate(),
				nextbillDate.toDate(), endDate.toDate(),
				billingOrderData.getBillingFrequency(),
				billingOrderData.getChargeCode(),
				billingOrderData.getChargeType(),
				billingOrderData.getChargeDuration(),
				billingOrderData.getDurationType(), invoiceTillDate.toDate(),
				price, billingOrderData.getBillingAlign(),
				billingOrderData.getOneTimeBill(),listOfTaxes);
		return  billingOrderCommand;
	}

}
