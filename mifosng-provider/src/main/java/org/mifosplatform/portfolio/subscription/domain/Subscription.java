package org.mifosplatform.portfolio.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.portfolio.subscription.commands.SubscriptionCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "contract_period")
public class Subscription extends AbstractPersistable<Long> {

	@Column(name = "contract_period", nullable = false)
	private String subscription_period;

	@Column(name = "is_deleted", nullable = false)
	private boolean deleted=false;

	@Column(name = "contract_type", length = 100)
	private String subscription_type;

	@Column(name = "contract_duration")
	private Long units;

		public Subscription() {
	}

	public Subscription(final String subscriptionPeriod, final Long units,
			final String subscriptionType, final Long subscriptionTypeId) {

       this.subscription_period=subscriptionPeriod;
		this.subscription_type = subscriptionType;
		this.units = units;



	}



	public String getSubscription_period() {
		return subscription_period;
	}



	public String getSubscription_type() {
		return subscription_type;
	}



	public Long getUnits() {
		return units;
	}



	public void update(SubscriptionCommand command, String type) {

		if (command.issubscriptionPeriodChanged()) {
			this.subscription_period = command.getSubscription_period();
		}

		if (command.isUnitsChanged()) {
			this.units = command.getUnits();
		}


		if (command.isSubscriptionTypeIdChanged()) {


			this.subscription_type = type;
		}
		if (command.isDayNameChanged()) {

		}

	}

	public void delete() {
		if (deleted) {

		} else {
			this.deleted = true;

		}
	}

}
