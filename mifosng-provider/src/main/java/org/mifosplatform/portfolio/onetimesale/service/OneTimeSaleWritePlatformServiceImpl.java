package org.mifosplatform.portfolio.onetimesale.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.onetimesale.command.OneTimeSaleCommand;
import org.mifosplatform.portfolio.onetimesale.domain.OneTimeSale;
import org.mifosplatform.portfolio.onetimesale.domain.OneTimeSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OneTimeSaleWritePlatformServiceImpl implements OneTimeSaleWritePlatformService{
	
	private PlatformSecurityContext context;
	private OneTimeSaleRepository  oneTimeSaleRepository;
	@Autowired
	public OneTimeSaleWritePlatformServiceImpl(final PlatformSecurityContext context,final OneTimeSaleRepository oneTimeSaleRepository)
	{
		this.context=context;
		this.oneTimeSaleRepository=oneTimeSaleRepository;
	}

	 @Transactional
	@Override
	public CommandProcessingResult createOneTimeSale(
			OneTimeSaleCommand command, Long clientId) {
		try{
			
			this.context.authenticatedUser();
			OneTimeSale oneTimeSale=new OneTimeSale(clientId,command.getItemId(),command.getQuantity(),command.getUnits(),command.getChargeCode(),
					command.getUnitPrice(),command.getTotalPrice());
			
			this.oneTimeSaleRepository.save(oneTimeSale);
			return new CommandProcessingResult(Long.valueOf(oneTimeSale.getId()));
		} catch (DataIntegrityViolationException dve) {
			handleDataIntegrityIssues(command, dve);
			return new CommandProcessingResult(Long.valueOf(-1));
		}
	}

	private void handleDataIntegrityIssues(OneTimeSaleCommand command,
			DataIntegrityViolationException dve) {
		
		
	}
	 

}
