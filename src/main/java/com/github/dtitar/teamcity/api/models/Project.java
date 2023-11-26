package com.github.dtitar.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String id;
    private String name;
    private String parentProjectId;
    private String locator;
}
