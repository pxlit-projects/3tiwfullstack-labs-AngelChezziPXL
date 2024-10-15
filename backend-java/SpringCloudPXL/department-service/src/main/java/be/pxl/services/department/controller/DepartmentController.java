package be.pxl.services.department.controller;
import be.pxl.services.department.domain.dto.DepartmentRequest;
import be.pxl.services.department.domain.dto.DepartmentResponse;
import be.pxl.services.department.services.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addDepartment(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.addDepartment(departmentRequest);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse getDepartmentById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @GetMapping("/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentResponse> getDepartmentByOrganizationId(@PathVariable Long organizationId) {
        return departmentService.findAllByOrganizationId(organizationId);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentResponse> getDepartmentByOrganizationIdWithEmployees(@PathVariable Long organizationId) {
        return departmentService.findAllByOrganizationWithEmployees(organizationId);
    }
}
