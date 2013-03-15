package org.mifosplatform.portfolio.subscription.commands;

import java.util.Set;

public class SubscriptionCommand {

	private final String subscriptionPeriod;

	private final Long subscriptionTypeId;
	private final Long units;
	private String subscriptionType;
	private final Long id;
	private final Set<String> modifiedParameters;

	public SubscriptionCommand(final Set<String> modifiedParameters,
			final Long subScriptionId, final Long id,
			final String subscriptionPeriod, final Long units,
			String subscriptionType, final Long subscriptionTypeId) {
		this.subscriptionPeriod = subscriptionPeriod;

		this.units = units;
		this.id = id;
		this.subscriptionType = subscriptionType;
		this.subscriptionTypeId = subscriptionTypeId;
		this.modifiedParameters = modifiedParameters;
	}

	public Long getSubscriptionTypeId() {
		return subscriptionTypeId;
	}

	public String getSubscriptionPeriod() {
		return subscriptionPeriod;
	}

	public Long getUnits() {
		return units;
	}

	public String getsubscriptionType() {

		return subscriptionType;

	}

	public Long getId() {
		return id;
	}

	public boolean issubscriptionPeriodChanged() {
		return this.modifiedParameters.contains("subscriptionPeriod");
	}

	public boolean issubscriptionTypeChanged() {
		return this.modifiedParameters.contains("subscriptionType");
	}

	public boolean isUnitsChanged() {
		return this.modifiedParameters.contains("units");
	}

	public boolean isSubscriptionTypeIdChanged() {
		return this.modifiedParameters.contains("subscriptionTypeId");
	}

}
