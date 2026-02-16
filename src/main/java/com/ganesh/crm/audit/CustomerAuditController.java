package com.ganesh.crm.audit;

import com.ganesh.crm.audit.CustomerAudit;
import com.ganesh.crm.audit.CustomerAuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class CustomerAuditController {

    private final CustomerAuditService auditService;

    public CustomerAuditController(
            CustomerAuditService auditService) {
        this.auditService = auditService;
    }

    // pendigs : add the permission to see only for admin and manager
    @GetMapping("/customers/{phoneNumber}")
    public List<CustomerAudit> getCustomerHistory(
            @PathVariable String phoneNumber) {

        return auditService.getCustomerHistory(phoneNumber);
    }
}
