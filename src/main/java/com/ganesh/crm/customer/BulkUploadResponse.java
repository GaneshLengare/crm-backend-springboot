package com.ganesh.crm.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkUploadResponse {

    private int totalRecords;

    private int successCount;

    private int failureCount;

    private List<String> errors;
}
