package be.pxl.services.department.domain.dto;

import be.pxl.services.department.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {
    private Long id;
    private Long organizationId;
    private String name;
    private List<Employee> employees;
    private String position;
}