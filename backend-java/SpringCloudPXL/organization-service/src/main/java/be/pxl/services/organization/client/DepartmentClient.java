package be.pxl.services.organization.client;

import be.pxl.services.organization.domain.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient
public interface DepartmentClient {
    @GetMapping("/organization/{organizationId}")
    public List<Department> getDepartmentByOrganizationId(@PathVariable Long organizationId);
}
