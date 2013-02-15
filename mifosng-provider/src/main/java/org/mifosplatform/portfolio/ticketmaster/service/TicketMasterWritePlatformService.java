package org.mifosplatform.portfolio.ticketmaster.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.plan.commands.PlansCommand;
import org.mifosplatform.portfolio.ticketmaster.command.TicketMasterCommand;

public interface TicketMasterWritePlatformService {


	CommandProcessingResult createTicketMaster(TicketMasterCommand command,Long clieniId);
}
