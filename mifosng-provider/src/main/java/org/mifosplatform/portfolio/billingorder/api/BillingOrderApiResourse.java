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
import org.mifosplatform.portfolio.billingorder.service.BillingOrderReadPlatformService;
import org.mifosplatform.portfolio.billingorder.service.BillingOrderWritePlatformService;
import org.mifosplatform.portfolio.billingorder.service.GenerateBillingOrderService;
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


	@Autowired
	private BillingOrderReadPlatformService billingOrderReadPlatformService;

	@Autowired
	private GenerateBillingOrderService generateBillingOrderService;

	@Autowired
	private PortfolioApiJsonBillingSerializerService apiJsonSerializerService;

	@Autowired
	private BillingOrderWritePlatformService billingOrderWritePlatformService;

    @Autowired
    private PortfolioApiDataBillingConversionService apiDataConversionService;

	@Autowired
	private AdjustmentReadPlatformService adjustmentReadPlatformService;

	 @POST
	 @Path("{clientId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response retrieveBillingProducts(@PathParam("clientId") final Long clientId,final String jsonRequestBody) {

			LocalDate  localDate = this.apiDataConversionService.convertJsonToBillingProductCommand(null, jsonRequestBody);
			// Get List of Plans
			List<Long> orderIds = billingOrderReadPlatformService.retrieveOrderIds(clientId);

			// for each plan
			for(Long orderId : orderIds){
			// call below code with client_id , date and plan_id

			// Charges
			List<BillingOrderData> products = this.billingOrderReadPlatformService.retrieveBillingOrderData(clientId,localDate,orderId);

			List<BillingOrderCommand> billingOrderCommands = this.generateBillingOrderService.generatebillingOrder(products);
			List<BillingOrder> listOfBillingOrders = billingOrderWritePlatformService.createBillingProduct(billingOrderCommands);

			// Invoice
			InvoiceCommand invoiceCommand = this.generateBillingOrderService.generateInvoice(billingOrderCommands);
			List<ClientBalanceData> clientBalancesDatas =  adjustmentReadPlatformService.retrieveAllAdjustments(clientId);
			Invoice invoice = billingOrderWritePlatformService.createInvoice(invoiceCommand,clientBalancesDatas);

			// Update invoice-tax
			billingOrderWritePlatformService.updateInvoiceTax(invoice,billingOrderCommands,listOfBillingOrders);

			// Update charge
			 billingOrderWritePlatformService.updateInvoiceCharge(invoice, listOfBillingOrders);

			// Update orders
			billingOrderWritePlatformService.updateBillingOrder(billingOrderCommands);

			// Update order-price
			CommandProcessingResult entityIdentifier = billingOrderWritePlatformService.updateOrderPrice(billingOrderCommands);


/*
		// Tax
		List<List<InvoiceTax>> listOfListOfTax = new ArrayList<List<InvoiceTax>>();
		List<InvoiceTax> tax = new ArrayList<InvoiceTax>();
		for(BillingOrder command : listOfBillingOrders){

			List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(command.getChargeCode());
			List<InvoiceTaxCommand> invoiceTaxCommand = this.generateBillingOrderService.generateInvoiceTax(taxMappingRateDatas, command);
			tax = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);
			listOfListOfTax.add(tax);
		}

		List<TaxMappingRateData> taxMappingRateDatas = billingOrderReadPlatformService.retrieveTaxMappingDate(command.getChargeCode());
		List<InvoiceTaxCommand> invoiceTaxCommand = this.generateBillingOrderService.generateInvoiceTax(taxMappingRateDatas, command);
		List<InvoiceTax> tax = billingOrderWritePlatformService.createInvoiceTax(invoiceTaxCommand);
*/

//		// Invoice
//		InvoiceCommand invoiceCommand = this.generateBillingOrderService.generateInvoice(billingOrderCommands);
//		List<ClientBalanceData> clientBalancesDatas =  adjustmentReadPlatformService.retrieveAllAdjustments(clientId);
//		Invoice invoice = billingOrderWritePlatformService.createInvoice(invoiceCommand,localDate,clientBalancesDatas);
//
//		// Update invoice-tax
//		 billingOrderWritePlatformService.updateInvoiceTax(invoice,billingOrderCommands,listOfBillingOrders);
//
//		// Update charge
//		 billingOrderWritePlatformService.updateInvoiceCharge(invoice, listOfBillingOrders);
//
//		// Update orders
//		 billingOrderWritePlatformService.updateBillingOrder(billingOrderCommands);
//
//		// Update order-price
//		CommandProcessingResult entityIdentifier = billingOrderWritePlatformService.updateOrderPrice(billingOrderCommands);

		}
		return Response.ok().entity(1l).build();
	}

}
