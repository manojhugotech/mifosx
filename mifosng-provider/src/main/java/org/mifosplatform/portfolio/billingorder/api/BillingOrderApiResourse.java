package org.mifosplatform.portfolio.billingorder.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.adjustment.service.AdjustmentReadPlatformService;
import org.mifosplatform.portfolio.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.portfolio.billingorder.commands.InvoiceCommand;
import org.mifosplatform.portfolio.billingorder.data.BillingOrderData;
import org.mifosplatform.portfolio.billingorder.domain.BillingOrder;
import org.mifosplatform.portfolio.billingorder.domain.Invoice;
import org.mifosplatform.portfolio.billingorder.exceptions.BillingOrderNoRecordsFoundException;
import org.mifosplatform.portfolio.billingorder.service.BillingOrderReadPlatformService;
import org.mifosplatform.portfolio.billingorder.service.BillingOrderWritePlatformService;
import org.mifosplatform.portfolio.billingorder.service.GenerateBillingOrderService;
import org.mifosplatform.portfolio.billingorder.service.InvoiceClient;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiDataBillingConversionService;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiJsonBillingSerializerService;
import org.mifosplatform.portfolio.clientbalance.data.ClientBalanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/billingorder")
@Component
@Scope("singleton")
public class BillingOrderApiResourse {

	private InvoiceClient invoiceClient;
	private PortfolioApiDataBillingConversionService apiDataConversionService;
	
	@Autowired
	BillingOrderApiResourse(InvoiceClient invoiceClient,PortfolioApiDataBillingConversionService apiDataConversionService){
		this.invoiceClient = invoiceClient;
		this.apiDataConversionService = apiDataConversionService;
	}
	
	
	@POST
	@Path("{clientId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response retrieveBillingProducts(@PathParam("clientId") final Long clientId,final String jsonRequestBody) {

		 	LocalDate  processDate = this.apiDataConversionService.convertJsonToBillingProductCommand(null, jsonRequestBody);
		 	
		 	invoiceClient.invoicingSingleClient(clientId, processDate);

		return Response.ok().entity(1l).build();
	}

}
