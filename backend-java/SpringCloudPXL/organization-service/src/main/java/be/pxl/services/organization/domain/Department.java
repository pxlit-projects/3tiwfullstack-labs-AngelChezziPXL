package be.pxl.services.organization.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long id;
    private Long organizationId;
    private String name;
    private List<Employee> employees = Collections.emptyList();
    private String position;
}
