package org.mifosplatform.portfolio.pricing.commands;

import java.math.BigDecimal;
import java.util.Set;

public class PricingCommand {

	private final  String planCode;
	private final String serviceCode;
	private final String chargeCode;
	private final  String chargingVariant;

	private final  BigDecimal price;
	private final Long discountId;


	private final Set<String> modifiedParameters;

	public PricingCommand(Set<String> modifiedParameters,
		 String planCode, String serviceCode,
			String chargeCode, String chargingVariant, BigDecimal price,
			Long discountId) {
		this.chargeCode=chargeCode;
		this.serviceCode=serviceCode;
		this.planCode=planCode;
		this.chargingVariant=chargingVariant;
		this.price=price;
		this.discountId=discountId;
		this.modifiedParameters=modifiedParameters;


	}

	public String getPlanCode() {
		return planCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public String getChargingVariant() {
		return chargingVariant;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getdiscountId() {
		return discountId;
	}

	public Set<String> getModifiedParameters() {
		return modifiedParameters;
	}

	public boolean isServiceCodeChanged() {
		return this.modifiedParameters.contains("serviceCode");
	}

	public boolean isChargeCodeChanged() {
		return this.modifiedParameters.contains("chargeCode");
	}
	public boolean isChargeVariantChanged() {
		return this.modifiedParameters.contains("chargevariant");
	}
	public boolean isPriceChanged() {
		return this.modifiedParameters.contains("price");
	}

	public boolean isDiscountChanged() {
		return this.modifiedParameters.contains("discountId");
	}



}
