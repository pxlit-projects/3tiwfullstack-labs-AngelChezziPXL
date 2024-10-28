package be.pxl.services.organization.client;

import be.pxl.services.organization.domain.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient
public interface EmployeeClient {
    @GetMapping("/organization/{organizationId}")
    public List<Employee> getEmployeesByOrganizationId(@PathVariable Long organizationId);
    }
