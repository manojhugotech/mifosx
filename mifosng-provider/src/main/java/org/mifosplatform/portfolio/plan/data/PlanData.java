package org.mifosplatform.portfolio.plan.data;


import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.portfolio.billingorder.data.BillRuleData;
import org.mifosplatform.portfolio.subscription.data.SubscriptionData;


public class PlanData {

		private final Long id;
		private final Long billRule;

		private final String planCode;
		private final String planDescription;
		private  LocalDate startDate;
		private final LocalDate endDate;
		private final Long status;
		private  EnumOptionData planstatus;
		private final String serviceDescription;
		private  final List<ServiceData> services;
		private final List<ServiceData>selectedServices;
		private List<String> contractPeriod;
		private List<SubscriptionData> subscriptiondata;
		private List<BillRuleData> statusType;
		private List<EnumOptionData> planStatus;
		private final String Period;

		private PlanData datas;
		private long statusname;



		public  PlanData(final Long id,final String planCode)
		{

			this.id=id;
			this.planCode=planCode;
			this.planDescription=null;
			this.endDate=null;
			this.startDate=null;
			this.subscriptiondata=null;
			this.status=null;
			this.statusType=null;
			this.services=null;
			this.serviceDescription=null;
			this.billRule=null;
			this.Period=null;
			this.datas=null;
			this.selectedServices=null;

		}



		public PlanData(Long id, String planCode,
				String serviceDescription, LocalDate start_date,
				Long plan_status, LocalDate end_date,EnumOptionData status) {
			this.id=id;
			this.planCode=planCode;
			this.serviceDescription=serviceDescription;
			this.startDate=start_date;
			this.status=plan_status;
			this.endDate=end_date;
			this.billRule=null;
			this.planDescription=null;
			this.services=null;
			this.statusType=null;
			this.Period=null;
			this.datas=null;
			this.selectedServices=null;
			this.planstatus=status;
		}


		public PlanData(Long id, String planCode, Long charge_code,
				String contract_period, LocalDate startDate, LocalDate endDate) {
			this.id=id;
			this.planCode=planCode;
			this.serviceDescription=null;
			this.startDate=startDate;
			this.status=charge_code;
			this.billRule=null;
			this.endDate=endDate;
			this.planDescription=contract_period;
			this.services=null;
			this.statusType=null;
			this.Period=null;
			this.datas=null;
			this.selectedServices=null;
		}

		public PlanData(List<ServiceData> data, List<BillRuleData> billData,
				List<SubscriptionData> contractPeriod,
			List<EnumOptionData> status,PlanData datas,List<ServiceData> selectedservice) {
			this.id=datas.getId();
		this.planCode=datas.getplanCode();
			this.subscriptiondata=contractPeriod;
			this.startDate=datas.getStartDate();
			this.status=datas.getStatus();
		this.billRule=datas.getBillRule();
		this.endDate=datas.getEndDate();
			this.planDescription=datas.getplanDescription();
		this.services=data;

		this.selectedServices=selectedservice;
		this.statusType=billData;
		this.planStatus=status;
			this.serviceDescription=null;
			this.Period=null;
			this.datas=datas;
			this.datas=null;

		}




		public PlanData(List<ServiceData> data, List<BillRuleData> billData,
				List<SubscriptionData> contractPeriod,
				List<EnumOptionData> status) {

			this.id=null;
			this.planCode=null;
			this.subscriptiondata=contractPeriod;
			this.startDate=null;
			this.status=null;
			this.billRule=null;
			this.endDate=null;
			this.planDescription=null;
			this.services=data;
			this.statusType=billData;
			this.planStatus=status;
			this.serviceDescription=null;
			this.Period=null;
			this.datas=null;
			this.selectedServices=null;
			this.startDate=new LocalDate();
		}



		public PlanData(Long id, String planCode, LocalDate startDate,
				LocalDate endDate, Long bill_rule, String contractPeriod,
				 long status, String planDescription,
				long status1) {

			this.id=id;
			this.planCode=planCode;
		this.serviceDescription=null;
			this.startDate=startDate;
			this.status=status;
		this.billRule=bill_rule;
			this.endDate=endDate;
			this.planDescription=planDescription;
		this.services=null;
			this.statusType=null;
		this.Period=contractPeriod;

			this.selectedServices=null;
			this.statusname=status1;
		}



		public EnumOptionData getPlanstatus() {
			return planstatus;
		}



		public PlanData getDatas() {
			return datas;
		}

		public List<ServiceData> getSelectedServices() {
			return selectedServices;
		}


		public long getStatusname() {
			return statusname;
		}







		public List<EnumOptionData> getPlanStatus() {
			return planStatus;
		}



		public String getPeriod() {
			return Period;
		}

		public void setContractPeriod(List<String> contractPeriod) {
			this.contractPeriod = contractPeriod;
		}

		public List<BillRuleData> getStatusType() {
			return statusType;
		}




		public Long getId() {
			return id;
		}

		public String getplanCode() {
			return planCode;
		}

		public String getplanDescription() {
			return planDescription;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public Long getStatus() {
			return status;
		}

		public List<ServiceData> getServicedata() {
			return services;
		}



		public Long getBillRule() {
			return billRule;
		}



		public List<String> getContractPeriod() {
			return contractPeriod;
		}





		public String getserviceDescription() {
			return serviceDescription;
		}



		public List<SubscriptionData> getSubscriptiondata() {
			return subscriptiondata;
		}




	}
