package org.mifosplatform.portfolio.onetimesale.service;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.onetimesale.command.OneTimeSaleCommand;

public interface OneTimeSaleWritePlatformService {

	CommandProcessingResult createOneTimeSale(OneTimeSaleCommand command,Long clientId);

}
