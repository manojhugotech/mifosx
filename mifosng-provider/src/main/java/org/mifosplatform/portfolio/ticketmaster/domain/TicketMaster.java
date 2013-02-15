package org.mifosplatform.portfolio.ticketmaster.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "ticket_master")
public class TicketMaster {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "client_id", length = 65536)
	private Long clientId;

	@Column(name = "priority")
	private String priority;

	@Column(name = "problem_code")
	private String problemCode;
	
	@Column(name = "description")
	private String description;

	@Column(name = "ticket_date")
	private Date ticketDate;

	@Column(name = "status")
	private String status;

	@Column(name = "resolution_description")
	private String resolutionDescription;
	
	@Column(name = "assigned_to")
	private String assignedTo;

	@Column(name = "is_deleted", nullable = false)
	private char deleted = 'n';

	
	public TicketMaster() {
		// TODO Auto-generated constructor stub
	}



	public TicketMaster(Long clientId, String priority,
			LocalDate ticketDate, String problemCode, String description,
			String status, String resolutionDescription, String assignedTo) {
		
		this.clientId=clientId;
		this.priority=priority;
		this.ticketDate=ticketDate.toDate();
		this.problemCode=problemCode;
		this.description=description;
		this.status="";
		this.resolutionDescription="";
		this.assignedTo="";
		
	
	}



	public Long getId() {
		return id;
	}



	public Long getClientId() {
		return clientId;
	}



	public String getPriority() {
		return priority;
	}



	public String getProblemCode() {
		return problemCode;
	}



	public String getDescription() {
		return description;
	}



	public Date getTicketDate() {
		return ticketDate;
	}



	public String getStatus() {
		return status;
	}



	public String getResolutionDescription() {
		return resolutionDescription;
	}



	public String getAssignedTo() {
		return assignedTo;
	}



	public char getDeleted() {
		return deleted;
	}

	 
}
