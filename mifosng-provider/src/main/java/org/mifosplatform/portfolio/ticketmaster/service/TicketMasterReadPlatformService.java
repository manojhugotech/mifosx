package org.mifosplatform.portfolio.ticketmaster.service;

import java.util.List;

import org.mifosplatform.portfolio.ticketmaster.data.ProblemsData;
import org.mifosplatform.portfolio.ticketmaster.data.TicketMasterData;

public interface TicketMasterReadPlatformService {

	TicketMasterData retrieveTicketMasterata();

	List<ProblemsData> retrieveProblemData();

	
	
}
