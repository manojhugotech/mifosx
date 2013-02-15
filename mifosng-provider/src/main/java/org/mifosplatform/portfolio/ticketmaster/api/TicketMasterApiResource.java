package org.mifosplatform.portfolio.ticketmaster.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import org.mifosplatform.portfolio.order.data.OrderData;
import org.mifosplatform.portfolio.plan.domain.PlanRepository;
import org.mifosplatform.portfolio.pricing.service.PriceReadPlatformService;
import org.mifosplatform.portfolio.subscription.data.SubscriptionData;
import org.mifosplatform.portfolio.ticketmaster.command.TicketMasterCommand;
import org.mifosplatform.portfolio.ticketmaster.data.ProblemsData;
import org.mifosplatform.portfolio.ticketmaster.data.TicketMasterData;
import org.mifosplatform.portfolio.ticketmaster.service.TicketMasterReadPlatformService;
import org.mifosplatform.portfolio.ticketmaster.service.TicketMasterWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Path("/ticketmasters")
@Component
@Scope("singleton")
public class TicketMasterApiResource {

		@Autowired
		private PortfolioApiDataBillingConversionService apiDataConversionService;

		@Autowired
		private PortfolioApiJsonBillingSerializerService apiJsonSerializerService;

		@Autowired
		private TicketMasterWritePlatformService ticketMasterWritePlatformService;

		@Autowired
		private TicketMasterReadPlatformService ticketMasterReadPlatformService ;

		@Autowired
		private PriceReadPlatformService priceReadPlatformService;

		@Autowired
		private PlanRepository planRepository;

		@Autowired
		private PlatformSecurityContext context;

		private final String entityType = "TICKETMASTER";

		

		@POST
        @Path("{clientId}")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_JSON })
		public Response createTicketMaster(@PathParam("clientId") final Long clientId,final String jsonRequestBody) {

			TicketMasterCommand command = this.apiDataConversionService
					.convertJsonToTicketMasterCommand(null, jsonRequestBody);
			CommandProcessingResult userId = this.ticketMasterWritePlatformService.createTicketMaster(command,clientId);
			return Response.ok().entity(userId).build();
		}

		@GET
		@Path("template")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_JSON })
		public String retrieveTicketMasterTemplateData(@Context final UriInfo uriInfo) {

			// context.authenticatedUser().validateHasReadPermission("CLIENT");

			context.authenticatedUser().validateHasReadPermission(entityType);

			Set<String> typicalResponseParameters = new HashSet<String>(
					Arrays.asList("statusType"));

			Set<String> responseParameters = ApiParameterHelper.extractFieldsForResponseIfProvided(uriInfo.getQueryParameters());
			if (responseParameters.isEmpty()) {
				responseParameters.addAll(typicalResponseParameters);
			}
			boolean prettyPrint = ApiParameterHelper.prettyPrint(uriInfo.getQueryParameters());

			responseParameters.addAll(Arrays.asList("priorityType","problemsDatas"));
			TicketMasterData templateData = handleTemplateRelatedData(responseParameters);

			return this.apiJsonSerializerService.serializeTicketMasterToJson(prettyPrint, responseParameters, templateData);
		}


		private TicketMasterData handleTemplateRelatedData(final Set<String> responseParameters) {

			TicketMasterData data = this.ticketMasterReadPlatformService.retrieveTicketMasterata();
			List<ProblemsData> datas=this.ticketMasterReadPlatformService.retrieveProblemData();
			data= new TicketMasterData(data,datas);
			return  data;

	}
}

