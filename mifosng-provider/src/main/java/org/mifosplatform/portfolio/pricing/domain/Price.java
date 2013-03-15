package org.mifosplatform.portfolio.pricing.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.portfolio.plan.commands.PlansCommand;
import org.mifosplatform.portfolio.pricing.commands.PricingCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "plan_pricing")
public class Price extends AbstractPersistable<Long> {

	@Column(name = "plan_code")
	private Long planCode;

	@Column(name = "service_code")
	private String serviceCode;

	@Column(name = "charge_code")
	private String chargeCode;

	@Column(name = "charging_variant")
	private String chargingVariant;

	@Column(name = "price", scale = 6, precision = 19, nullable = false)
	private BigDecimal price;

	@Column(name = "discount_id", nullable = false)
	private Long discountId;

	@Column(name = "is_deleted")
	private String isDeleted="n";

	public Price() {
	}

	public Price(final Long planCode, final String chargeCode,
			final String serviceCode, final String chargingVariant,
			final BigDecimal price, final Long discountId)

	{

		this.planCode = planCode;
		this.serviceCode = serviceCode;
		this.chargeCode = chargeCode;

		this.chargingVariant = chargingVariant;
		this.price = price;
		this.discountId = discountId;
	}

	public String getChargingVariant() {
		return chargingVariant;
	}

	public void setCharging_variant(String chargingVariant) {
		this.chargingVariant = chargingVariant;
	}

	public Long getPlanCode() {
		return planCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void update(PricingCommand command) {
		if (command.isServiceCodeChanged()) {
			this.serviceCode = command.getServiceCode();
		}
		if (command.isChargeCodeChanged()) {
			this.chargeCode = command.getChargeCode();
		}
		if (command.isChargeVariantChanged()) {
			this.chargingVariant = command.getChargingVariant();
			if (command.isDiscountChanged()) {
				this.discountId = command.getdiscountId();
			}
			if (command.isPriceChanged()) {
				this.price = command.getPrice();
			}
		}

	}

	public void delete() {



			this.isDeleted = "y";


	}
}
