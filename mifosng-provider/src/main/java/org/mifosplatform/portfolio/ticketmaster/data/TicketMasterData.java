package org.mifosplatform.portfolio.ticketmaster.data;

import java.util.List;

import org.mifosplatform.infrastructure.core.data.EnumOptionData;


public class TicketMasterData {
	
	private final List<EnumOptionData> statusType,priorityType;
    private  List<ProblemsData> problemsDatas;
	public TicketMasterData(List<EnumOptionData> statusType,
			List<EnumOptionData> priorityType) {
		this.statusType=statusType;
		this.priorityType=priorityType;
		this.problemsDatas=null;
		
		
		
	}

	public TicketMasterData(TicketMasterData data, List<ProblemsData> datas) {
		this.statusType=data.getStatusType();
		this.priorityType=data.getPriorityType();
		this.problemsDatas=datas;
	}

	public List<EnumOptionData> getStatusType() {
		return statusType;
	}

	public List<EnumOptionData> getPriorityType() {
		return priorityType;
	}

	public List<ProblemsData> getProblemsDatas() {
		return problemsDatas;
	}

	

	

}
