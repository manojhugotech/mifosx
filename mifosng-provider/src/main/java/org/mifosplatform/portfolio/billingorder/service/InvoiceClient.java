package org.mifosplatform.portfolio.billingorder.service;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.adjustment.service.AdjustmentReadPlatformService;
import org.mifosplatform.portfolio.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.portfolio.billingorder.commands.InvoiceCommand;
import org.mifosplatform.portfolio.billingorder.data.BillingOrderData;
import org.mifosplatform.portfolio.billingorder.domain.BillingOrder;
import org.mifosplatform.portfolio.billingorder.domain.Invoice;
import org.mifosplatform.portfolio.billingorder.exceptions.BillingOrderNoRecordsFoundException;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiDataBillingConversionService;
import org.mifosplatform.portfolio.billingproduct.PortfolioApiJsonBillingSerializerService;
import org.mifosplatform.portfolio.clientbalance.data.ClientBalanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceClient {

	private BillingOrderReadPlatformService billingOrderReadPlatformService;

	private GenerateBillingOrderService generateBillingOrderService;

	private PortfolioApiJsonBillingSerializerService apiJsonSerializerService;

	private BillingOrderWritePlatformService billingOrderWritePlatformService;

	private PortfolioApiDataBillingConversionService apiDataConversionService;

	private AdjustmentReadPlatformService adjustmentReadPlatformService;
	
	
	@Autowired
	InvoiceClient(
			BillingOrderReadPlatformService billingOrderReadPlatformService,
			GenerateBillingOrderService generateBillingOrderService,
			PortfolioApiJsonBillingSerializerService apiJsonSerializerService,
			BillingOrderWritePlatformService billingOrderWritePlatformService,
			PortfolioApiDataBillingConversionService apiDataConversionService,
			AdjustmentReadPlatformService adjustmentReadPlatformService) {
		

		this.billingOrderReadPlatformService = billingOrderReadPlatformService;
		this.generateBillingOrderService = generateBillingOrderService;
		this.apiJsonSerializerService = apiJsonSerializerService;
		this.billingOrderWritePlatformService = billingOrderWritePlatformService;
		this.apiDataConversionService = apiDataConversionService;
		this.adjustmentReadPlatformService = adjustmentReadPlatformService;
	}

	public void invoicingSingleClient(Long clientId, LocalDate processDate) {

		List<Long> orderIds = billingOrderReadPlatformService.retrieveOrderIds(clientId, processDate);
		if (orderIds.size() == 0) {
			throw new BillingOrderNoRecordsFoundException();
		}
		for (Long orderId : orderIds) {

			// Charges
			List<BillingOrderData> products = this.billingOrderReadPlatformService
					.retrieveBillingOrderData(clientId, processDate, orderId);

			List<BillingOrderCommand> billingOrderCommands = this.generateBillingOrderService
					.generatebillingOrder(products);
			List<BillingOrder> listOfBillingOrders = billingOrderWritePlatformService
					.createBillingProduct(billingOrderCommands);

			// Invoice
			InvoiceCommand invoiceCommand = this.generateBillingOrderService.generateInvoice(billingOrderCommands);
			List<ClientBalanceData> clientBalancesDatas = adjustmentReadPlatformService.retrieveAllAdjustments(clientId);
			Invoice invoice = billingOrderWritePlatformService.createInvoice(invoiceCommand, clientBalancesDatas);

			// Update invoice-tax
			billingOrderWritePlatformService.updateInvoiceTax(invoice, billingOrderCommands, listOfBillingOrders);

			// Update charge
			billingOrderWritePlatformService.updateInvoiceCharge(invoice,
					listOfBillingOrders);

			// Update orders
			billingOrderWritePlatformService
					.updateBillingOrder(billingOrderCommands);

			// Update order-price
			CommandProcessingResult entityIdentifier = billingOrderWritePlatformService
					.updateOrderPrice(billingOrderCommands);
			// TODO Auto-generated method stub
			
			

		}
	}

}
