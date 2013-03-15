package org.mifosplatform.portfolio.plan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plan_detail")
public class ServiceDetails {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@ManyToOne
    @JoinColumn(name="plan_id")
    private Plan plan;

	@Column(name ="service_code", length=50)
    private String serviceCode;


	@Column(name = "is_deleted", nullable = false)
	private char isDeleted = 'n';


	public ServiceDetails()
	{}
	public ServiceDetails(final String serviceCode)
	{

		this.serviceCode=serviceCode;
		//this.is_deleted=null;
		this.plan=null;

	}


	public String getServiceCode() {
		return serviceCode;
	}


	public char isIsDeleted() {
		return isDeleted;
	}



	public Plan getPlan() {
		return plan;
	}

	public void update(Plan plan1)
	{
		this.plan=plan1;
	}
	public void delete() {
		this.isDeleted='y';

	}



}