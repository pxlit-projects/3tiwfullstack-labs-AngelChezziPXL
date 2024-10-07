package be.pxl.services.employee.services;

import be.pxl.services.employee.Exceptions.NotFoundException;
import be.pxl.services.employee.domain.Employee;
import be.pxl.services.employee.domain.dto.EmployeeRequest;
import be.pxl.services.employee.domain.dto.EmployeeResponse;
import be.pxl.services.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;


    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .age(employee.getAge())
                .name(employee.getName())
                .organisationId(employee.getOrganisationId())
                .departmentId(employee.getDepartmentId())
                .position(employee.getPosition())
                .build();
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .organisationId(employeeRequest.getOrganisationId())
                .departmentId(employeeRequest.getDepartmentId())
                .position(employeeRequest.getPosition())
                .build();
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));
        return mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> findByDepartmentId(Long departmentId) {
        List<Employee> employees = employeeRepository.findAllByDepartmentId(departmentId).orElseThrow(() -> new NotFoundException("Employee with department id " + departmentId + " not found!"));
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findByOrganisationId(Long organizationId) {
        List<Employee> employees = employeeRepository.findAllByOrganisationId(organizationId).orElseThrow(() -> new NotFoundException("Employee with organisation id " + organizationId + " not found!"));
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }


}
