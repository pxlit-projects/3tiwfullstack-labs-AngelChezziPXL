package be.pxl.services.department.services;

import be.pxl.services.department.domain.Department;
import be.pxl.services.department.domain.dto.DepartmentRequest;
import be.pxl.services.department.domain.dto.DepartmentResponse;
import be.pxl.services.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResponse> findAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        return mapToDepartmentResponse(department);
    }

    @Override
    public List<DepartmentResponse> findAllByOrganizationId(Long organizationId) {
        List<Department> departments = departmentRepository.findAllByOrganizationId(organizationId).orElse(null);
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        if(departments == null || departments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        return departments.stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public List<DepartmentResponse> findAllByOrganizationWithEmployees(Long organizationId) {
        List<Department> departments = departmentRepository.findAllWithEmployeesByOrganizationId(organizationId).orElse(null);
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        if(departments == null || departments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        return  departments.stream().map((this::mapToDepartmentResponse)).toList();
    }

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
        Department department = mapToDepartment(departmentRequest);
        departmentRepository.save(department);
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .employees(department.getEmployees())
                .position(department.getPosition())
                .build();
    }

    private Department mapToDepartment(DepartmentRequest departmentRequest) {
        return Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
                .employees(departmentRequest.getEmployees())
                .position(departmentRequest.getPosition())
                .build();
    }
}
