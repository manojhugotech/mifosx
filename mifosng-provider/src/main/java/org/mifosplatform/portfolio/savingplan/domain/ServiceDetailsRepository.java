package org.mifosplatform.portfolio.savingplan.domain;

import org.mifosplatform.portfolio.savingplan.domain.ServiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceDetailsRepository  extends
JpaRepository<ServiceDetails, Long>,
JpaSpecificationExecutor<ServiceDetails>{

}
