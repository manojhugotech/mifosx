package org.mifosplatform.portfolio.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_line")
public class OrderLine {

@Id
@GeneratedValue
@Column(name="id")
private Long id;


@ManyToOne
@JoinColumn(name="order_id")
	private Order orders;

	@Column(name = "service_id")
	private Long service_id;

	@Column(name = "service_type")
	private String service_type;

	@Column(name = "service_status")
	private Long service_status;

	@Column(name = "is_deleted")
	private boolean is_deleted;

	public OrderLine()
	{}

	public OrderLine(final Long service_id,final String service_type,final Long service_status,final boolean isdeleted )
	{
		this.orders=null;
		this.service_id=service_id;
		this.service_status=service_id;
		this.is_deleted=isdeleted;
		this.service_type=service_type;

	}
public OrderLine(final String service_code)
{
	this.service_type=service_code;
	}

	public Order getOrder_id() {
		return orders;
	}


	public Long getService_id() {
		return service_id;
	}


	public String getService_type() {
		return service_type;
	}


	public Long getService_status() {
		return service_status;
	}


	public boolean isIs_deleted() {
		return is_deleted;
	}
	public  void update(Order order2)
	{
		this.orders=order2;

	}

	public void delete() {

		this.is_deleted=true;


	}



}
