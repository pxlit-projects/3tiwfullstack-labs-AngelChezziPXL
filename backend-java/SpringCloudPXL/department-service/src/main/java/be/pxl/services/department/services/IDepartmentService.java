package be.pxl.services.department.services;

import be.pxl.services.department.domain.dto.DepartmentRequest;
import be.pxl.services.department.domain.dto.DepartmentResponse;

import java.util.List;

public interface IDepartmentService {
    List<DepartmentResponse> findAll();
    DepartmentResponse findById(Long id);
    List<DepartmentResponse> findAllByOrganizationId(Long organizationId);

    void addDepartment(DepartmentRequest departmentRequest);


}
