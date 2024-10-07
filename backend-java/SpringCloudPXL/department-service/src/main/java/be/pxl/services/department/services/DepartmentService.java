package be.pxl.services.department.services;

import be.pxl.services.department.domain.Department;
import be.pxl.services.department.domain.dto.DepartmentRequest;
import be.pxl.services.department.domain.dto.DepartmentResponse;
import be.pxl.services.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResponse> findAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartment).toList();
    }

    @Override
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        return DepartmentResponse.builder()
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .employees(department.getEmployees())
                .position(department.getPosition())
                .build();
    }

    @Override
    public List<DepartmentResponse> findAllByOrganizationId(Long organizationId) {
        return List.of();
    }

    @Override
    public List<DepartmentResponse> findAllByOrganizationWithEmployees(Long organizationId) {
        return List.of();
    }

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {

    }

    private DepartmentResponse mapToDepartment(Department department) {
        return DepartmentResponse.builder()
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .employees(department.getEmployees())
                .position(department.getPosition())
                .build();
    }
}
