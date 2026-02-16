package com.ganesh.crm.audit;

import com.ganesh.crm.audit.CustomerAudit;

import java.util.List;

public interface CustomerAuditService {

    List<CustomerAudit> getCustomerHistory(String phoneNumber);

}
