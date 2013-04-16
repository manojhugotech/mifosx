package org.mifosplatform.portfolio.onetimesale.api;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.infrastructure.core.api.ApiParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiDataBillingConversionService;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiJsonBillingSerializerService;
import org.mifosplatform.portfolio.charge.data.ChargesData;
import org.mifosplatform.portfolio.onetimesale.command.OneTimeSaleCommand;
import org.mifosplatform.portfolio.onetimesale.data.ItemData;
import org.mifosplatform.portfolio.onetimesale.data.OneTimeSaleData;
import org.mifosplatform.portfolio.onetimesale.domain.OneTimeSale;
import org.mifosplatform.portfolio.onetimesale.service.InvoiceOneTimeSale;
import org.mifosplatform.portfolio.onetimesale.service.OneTimeSaleReadPlatformService;
import org.mifosplatform.portfolio.onetimesale.service.OneTimeSaleWritePlatformService;
import org.mifosplatform.portfolio.plan.data.PlanData;
import org.mifosplatform.portfolio.pricing.service.PriceReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Path("/onetimesales")
@Component
@Scope("singleton")
public class OneTimeSalesApiResource {

	@Autowired
	private PortfolioApiDataBillingConversionService apiDataConversionService;

	@Autowired
	private PortfolioApiJsonBillingSerializerService apiJsonSerializerService;

	@Autowired
	private  OneTimeSaleWritePlatformService oneTimeSaleWritePlatformService;

	@Autowired
	private  OneTimeSaleReadPlatformService oneTimeSaleReadPlatformService;
	
	@Autowired
	private  PriceReadPlatformService priceReadPlatformService;
	
	@Autowired
	private InvoiceOneTimeSale invoiceOneTimeSale;
	

	@POST
	@Path("{clientId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createNewSale(@PathParam("clientId") final Long clientId,final String jsonRequestBody) {

		// OneTimeSaleCommand command = this.apiDataConversionService.convertJsonToSalesCommand(null, jsonRequestBody);

		// CommandProcessingResult userId = this.oneTimeSaleWritePlatformService.createOneTimeSale(command,clientId);
		
		invoiceOneTimeSale.invoiceOneTimeSale(clientId);
		return Response.ok().entity(1l).build();
	}

	@GET
	@Path("template")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String retrieveItemTemplateData(@Context final UriInfo uriInfo) {

		// context.authenticatedUser().validateHasReadPermission("CLIENT");

		final Set<String> responseParameters = ApiParameterHelper
				.extractFieldsForResponseIfProvided(uriInfo
						.getQueryParameters());
		final boolean prettyPrint = ApiParameterHelper.prettyPrint(uriInfo
				.getQueryParameters());

		OneTimeSaleData data = handleTemplateRelatedData(responseParameters);

		

		return this.apiJsonSerializerService.serializeOneTimeSaleDataToJson(
				prettyPrint, responseParameters,data);
	}

	private OneTimeSaleData handleTemplateRelatedData(
			Set<String> responseParameters) {
		List<ChargesData> chargeDatas = this.priceReadPlatformService
				.retrieveChargeCode();
		List<ItemData> itemData = this.oneTimeSaleReadPlatformService.retrieveItemData();
		return new OneTimeSaleData(chargeDatas,itemData);
	}
	
	@GET
	@Path("{clientId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String retrieveClientAccount(
			@QueryParam("clientId") final Long clientId,
			@Context final UriInfo uriInfo) {

		// context.authenticatedUser().validateHasReadPermission("CLIENT");

		final Set<String> responseParameters = ApiParameterHelper
				.extractFieldsForResponseIfProvided(uriInfo
						.getQueryParameters());
		final boolean prettyPrint = ApiParameterHelper.prettyPrint(uriInfo
				.getQueryParameters());
		List<OneTimeSaleData> salesData = this.oneTimeSaleReadPlatformService.retrieveClientOneTimeSalesData(clientId);
				
		return this.apiJsonSerializerService.serializeOneTimeSaleDataToJson(
				prettyPrint, responseParameters,salesData);
	}

	
		
	}


