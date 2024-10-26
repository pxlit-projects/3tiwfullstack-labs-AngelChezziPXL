package be.pxl.services.employee.service;

import be.pxl.services.employee.domain.dto.EmployeeRequest;
import be.pxl.services.employee.domain.dto.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    List<EmployeeResponse> getAllEmployees();

    void addEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse findEmployeeById(Long id);

    List<EmployeeResponse> findByDepartmentId(Long departmentId);

    List<EmployeeResponse> findByOrganizationId(Long organizationId);
}
