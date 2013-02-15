package org.mifosplatform.portfolio.ticketmaster.command;

import org.joda.time.LocalDate;

public class TicketMasterCommand {

	private final Long id;
	private final Long clientId;
	private final String priority;
	private final LocalDate ticketDate;
	private final String problemCode;
	private final String description;
	private final String status;
	private final String resolutionDescription;
	private final String assignedTo;
	public TicketMasterCommand(final Long clientId,final String priority,
	final String description,final String problemCode,final String status,
	final String resolutionDescription, final String assignedTo,final LocalDate ticketDate){		
		
		this.id=null;
		this.clientId=clientId;
		this.priority=priority;
		this.ticketDate=ticketDate;
		this.problemCode=problemCode;
		this.description=description;
		this.status=status;
		this.resolutionDescription=resolutionDescription;
		this.assignedTo=assignedTo;
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
	public LocalDate getTicketDate() {
		return ticketDate;
	}
	public String getProblemCode() {
		return problemCode;
	}
	public String getDescription() {
		return description;
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
	
}
