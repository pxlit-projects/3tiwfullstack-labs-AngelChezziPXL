package be.pxl.services.organization.domain.dto;

import be.pxl.services.organization.domain.Department;
import be.pxl.services.organization.domain.Employee;
import jakarta.persistence.Transient;
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
public class OrganizationRequest {
    private Long id;
    private String name;
    private String address;
    private List<Employee> employees = Collections.emptyList();
    private List<Department> departments = Collections.emptyList();
}
