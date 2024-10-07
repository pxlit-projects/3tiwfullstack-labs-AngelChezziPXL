package be.pxl.services.employee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long organisationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;
}
