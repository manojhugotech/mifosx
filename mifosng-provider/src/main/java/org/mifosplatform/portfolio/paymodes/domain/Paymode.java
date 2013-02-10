package org.mifosplatform.portfolio.paymodes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "paymodes")
public class Paymode extends AbstractPersistable<Long> {

	@Column(name = "paymode_code", nullable = false)
	private String paymode_code;

	@Column(name = "paymode_description", length = 100)
	private String description;

	public Paymode(final String paymode_code, final String description,
			final String category) {

		this.paymode_code = paymode_code;
		this.description = description;

	}

	public String getPaymode_code() {
		return paymode_code;
	}

	public void setPaymode_code(String paymode_code) {
		this.paymode_code = paymode_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
