package org.mifosplatform.portfolio.ticketmaster.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.savingsdepositproduct.data.TicketMasterRepository;
import org.mifosplatform.portfolio.ticketmaster.command.TicketMasterCommand;
import org.mifosplatform.portfolio.ticketmaster.domain.TicketMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TicketMasterWritePlatformServiceImpl implements TicketMasterWritePlatformService{
	
	private PlatformSecurityContext context;
	private TicketMasterRepository repository;


	@Autowired
	public TicketMasterWritePlatformServiceImpl(final PlatformSecurityContext context,
			final TicketMasterRepository repository) {
		this.context = context;
		this.repository = repository;

	}

    @Transactional
	@Override
	public CommandProcessingResult createTicketMaster(
			TicketMasterCommand command,Long clieniId) {
		
    	try {


			this.context.authenticatedUser();

			TicketMasterCommandValidator validator=new TicketMasterCommandValidator(command);
			validator.validateForCreate();
			
			TicketMaster ticketMaster=new TicketMaster(clieniId,command.getPriority(),command.getTicketDate(),
					command.getProblemCode(),command.getDescription(),command.getStatus(),command.getResolutionDescription(),
					command.getAssignedTo());

                   this.repository.save(ticketMaster);
			return new CommandProcessingResult(Long.valueOf(ticketMaster.getId()));

		} catch (DataIntegrityViolationException dve) {
			handleDataIntegrityIssues(command, dve);
			return new CommandProcessingResult(Long.valueOf(-1));
		}
    	
	}

	private void handleDataIntegrityIssues(TicketMasterCommand command,
			DataIntegrityViolationException dve) {
		// TODO Auto-generated method stub
		
	}

}
