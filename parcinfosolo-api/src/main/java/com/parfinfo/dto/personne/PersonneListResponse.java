package com.parfinfo.dto.personne;

import lombok.Data;
import java.util.List;

@Data
public class PersonneListResponse {
    private List<PersonneResponse> personnes;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int size;
} 