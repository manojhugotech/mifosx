package org.mifosplatform.portfolio.savingplan.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceDescriptionRepository extends
JpaRepository<ServiceDescription, Long>,
JpaSpecificationExecutor<ServiceDescription>{

}
