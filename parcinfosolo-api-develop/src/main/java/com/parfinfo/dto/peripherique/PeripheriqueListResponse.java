package com.parfinfo.dto.peripherique;

import lombok.Data;
import java.util.List;

@Data
public class PeripheriqueListResponse {
    private List<PeripheriqueResponse> peripheriques;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int size;
} 