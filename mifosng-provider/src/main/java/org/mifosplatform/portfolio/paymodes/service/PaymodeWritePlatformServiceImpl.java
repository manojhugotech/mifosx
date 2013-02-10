package org.mifosplatform.portfolio.paymodes.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.paymodes.commands.PaymodeCommand;
import org.mifosplatform.portfolio.paymodes.domain.Paymode;
import org.mifosplatform.portfolio.paymodes.domain.PaymodeCommandValidator;
import org.mifosplatform.portfolio.paymodes.domain.PaymodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymodeWritePlatformServiceImpl implements PaymodeWritePlatformService{


	private PlatformSecurityContext context;

	private PaymodeRepository paymodeRepository;




	@Autowired
	public PaymodeWritePlatformServiceImpl(final PlatformSecurityContext context, final PaymodeRepository paymodeRepository){
		this.context=context;
		this.paymodeRepository=paymodeRepository;
	}
	@Transactional
	@Override
	public CommandProcessingResult createPaymode(PaymodeCommand command) {

			try
			{

				this.context.authenticatedUser();

				PaymodeCommandValidator validator=new PaymodeCommandValidator(command);
				validator.validateForCreate();

				Paymode data=new Paymode(command.getPaymode(),command.getDescription(),command.getCategory());
				this.paymodeRepository.save(data);

				return new CommandProcessingResult(data.getId());

			} catch (DataIntegrityViolationException dve) {
				 handleDataIntegrityIssues(command, dve);
				 return new CommandProcessingResult(Long.valueOf(-1));
			}
	}
	private void handleDataIntegrityIssues(PaymodeCommand command,
			DataIntegrityViolationException dve) {
		// TODO Auto-generated method stub

	}

}
