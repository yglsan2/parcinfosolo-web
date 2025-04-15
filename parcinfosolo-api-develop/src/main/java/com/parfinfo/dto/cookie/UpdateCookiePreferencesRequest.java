package com.parfinfo.dto.cookie;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCookiePreferencesRequest {
    @NotNull(message = "L'état des cookies analytiques est obligatoire")
    private boolean analyticsEnabled;

    @NotNull(message = "L'état des cookies marketing est obligatoire")
    private boolean marketingEnabled;

    @NotNull(message = "L'état des cookies nécessaires est obligatoire")
    private boolean necessaryEnabled;
} 