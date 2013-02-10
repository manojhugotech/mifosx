package org.mifosplatform.portfolio.paymodes.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.infrastructure.core.api.ApiParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiDataBillingConversionService;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiJsonBillingSerializerService;
import org.mifosplatform.portfolio.paymodes.commands.PaymodeCommand;
import org.mifosplatform.portfolio.paymodes.data.PaymodeData;
import org.mifosplatform.portfolio.paymodes.data.PaymodeTypesData;
import org.mifosplatform.portfolio.paymodes.service.PaymodeReadPlatformService;
import org.mifosplatform.portfolio.paymodes.service.PaymodeWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Path("/paymodes")
@Component
@Scope("singleton")
public class PaymodeApiResource {

	@Autowired
	private  PaymodeReadPlatformService paymodeReadPlatformService ;

	@Autowired
	private PortfolioApiDataBillingConversionService apiDataConversionService;

	@Autowired
	private PortfolioApiJsonBillingSerializerService apiJsonSerializerService;

	@Autowired
	private PaymodeWritePlatformService paymodeWritePlatformService;

	@Autowired
	private PlatformSecurityContext context;

	private final String entityType = "PAYMODE";

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response createSubscription(final String jsonRequestBody) {

		PaymodeCommand command = this.apiDataConversionService.convertJsonToPaymodeCommand(null, jsonRequestBody);

		CommandProcessingResult userId = this.paymodeWritePlatformService.createPaymode(command);
		return Response.ok().entity(userId).build();
	}

	@GET
	@Path("template")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String paymodeTypeDetails(@Context final UriInfo uriInfo) {

	context.authenticatedUser().validateHasReadPermission(entityType);

		Set<String> typicalResponseParameters = new HashSet<String>(
				Arrays.asList("categorytypes"));

		Set<String> responseParameters = ApiParameterHelper.extractFieldsForResponseIfProvided(uriInfo.getQueryParameters());
		if (responseParameters.isEmpty()) {
			responseParameters.addAll(typicalResponseParameters);
		}
		boolean prettyPrint = ApiParameterHelper.prettyPrint(uriInfo.getQueryParameters());

		List<PaymodeTypesData> categorytypes = this.paymodeReadPlatformService.retrieveNewPaymode();

		PaymodeData data= new PaymodeData(categorytypes);

		return this.apiJsonSerializerService.serializePaymodeToJson(prettyPrint, responseParameters, data);
	}



}
