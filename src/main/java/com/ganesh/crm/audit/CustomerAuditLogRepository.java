package com.ganesh.crm.audit;

import com.ganesh.crm.audit.CustomerAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAuditLogRepository
        extends JpaRepository<CustomerAudit, Long> {

    List<CustomerAudit> findByCustomerPhoneOrderByUpdatedAtDesc(
            String customerPhone);
}
