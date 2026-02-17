package com.ganesh.crm.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerBulkController {

    private final CustomerService customerService;

    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping("/bulk-upload")
    public ResponseEntity<BulkUploadResponse> bulkUpload(@RequestParam("file") MultipartFile file) {

        BulkUploadResponse response = customerService.bulkCreateCustomers(file);

        return ResponseEntity.ok(response);
    }
}
