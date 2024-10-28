package be.pxl.services.department.client;

import be.pxl.services.department.domain.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {
    @GetMapping("/api/employee/department/{departmentId}")
    public List<Employee> getEmployeesByDepartmentId(@PathVariable Long departmentId);

    @GetMapping("/api/employee/organization/{organizationId}")
    public List<Employee> getEmployeesByOrganizationId(@PathVariable Long organizationId);
}
