package org.mifosplatform.portfolio.loanaccount.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.joda.time.LocalDate;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.organisation.office.data.OfficeLookup;
import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.mifosplatform.organisation.staff.data.BulkTransferLoanOfficerData;
import org.mifosplatform.organisation.staff.data.StaffAccountSummaryCollectionData;
import org.mifosplatform.organisation.staff.data.StaffData;
import org.mifosplatform.organisation.staff.service.StaffReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/loans/loanreassignment")
@Component
@Scope("singleton")
public class BulkLoansApiResource {

    private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("officeId", "fromLoanOfficerId",
            "assignmentDate", "officeOptions", "loanOfficerOptions", "accountSummaryCollection"));

    private final String resourceNameForPermissions = "LOAN";

    private final PlatformSecurityContext context;
    private final StaffReadPlatformService staffReadPlatformService;
    private final OfficeReadPlatformService officeReadPlatformService;
    private final DefaultToApiJsonSerializer<BulkTransferLoanOfficerData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    @Autowired
    public BulkLoansApiResource(final PlatformSecurityContext context, final StaffReadPlatformService staffReadPlatformService,
            final OfficeReadPlatformService officeReadPlatformService,
            final DefaultToApiJsonSerializer<BulkTransferLoanOfficerData> toApiJsonSerializer,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.context = context;
        this.staffReadPlatformService = staffReadPlatformService;
        this.officeReadPlatformService = officeReadPlatformService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }

    @GET
    @Path("template")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String loanReassignmentTemplate(@QueryParam("officeId") final Long officeId,
            @QueryParam("fromLoanOfficerId") final Long loanOfficerId, @Context final UriInfo uriInfo) {

        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);

        final Collection<OfficeLookup> offices = this.officeReadPlatformService.retrieveAllOfficesForLookup();

        Collection<StaffData> loanOfficers = null;
        StaffAccountSummaryCollectionData staffAccountSummaryCollectionData = null;

        if (officeId != null) {
            loanOfficers = this.staffReadPlatformService.retrieveAllLoanOfficersByOffice(officeId);
        }

        if (loanOfficerId != null) {
            staffAccountSummaryCollectionData = this.staffReadPlatformService.retrieveLoanOfficerAccountSummary(loanOfficerId);
        }

        final BulkTransferLoanOfficerData loanReassignmentData = BulkTransferLoanOfficerData.templateForBulk(officeId, loanOfficerId,
                new LocalDate(), offices, loanOfficers, staffAccountSummaryCollectionData);

        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, loanReassignmentData, RESPONSE_DATA_PARAMETERS);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String loanReassignment(final String apiRequestBodyAsJson) {

        final CommandWrapper commandRequest = new CommandWrapperBuilder().assignLoanOfficersInBulk().withJson(apiRequestBodyAsJson).build();

        final CommandProcessingResult result = this.commandsSourceWritePlatformService.logCommandSource(commandRequest);

        return this.toApiJsonSerializer.serialize(result);
    }
}