package com.ganesh.crm.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerPhone;

    private String fieldName;

    @Column(columnDefinition = "TEXT")
    private String oldValue;

    @Column(columnDefinition = "TEXT")
    private String newValue;

    private String updatedBy;      // this will we find by using phoneNumber

    private LocalDateTime updatedAt;
}
