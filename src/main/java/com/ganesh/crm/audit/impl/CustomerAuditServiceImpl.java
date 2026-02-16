package com.ganesh.crm.audit.impl;

import com.ganesh.crm.audit.CustomerAuditService;
import com.ganesh.crm.audit.CustomerAudit;
import com.ganesh.crm.audit.CustomerAuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAuditServiceImpl
        implements CustomerAuditService {

    private final CustomerAuditLogRepository customerAuditLogRepository;

    public CustomerAuditServiceImpl(
            CustomerAuditLogRepository auditRepo) {
        this.customerAuditLogRepository = auditRepo;
    }

    @Override
    public List<CustomerAudit> getCustomerHistory(
            String phoneNumber) {

        return customerAuditLogRepository
                .findByCustomerPhoneOrderByUpdatedAtDesc(phoneNumber);
    }
}
