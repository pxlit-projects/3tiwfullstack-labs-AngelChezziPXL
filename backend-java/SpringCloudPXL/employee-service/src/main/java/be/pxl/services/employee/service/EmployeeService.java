package be.pxl.services.employee.service;

import be.pxl.services.employee.domain.Employee;
import be.pxl.services.employee.domain.dto.EmployeeRequest;
import be.pxl.services.employee.domain.dto.EmployeeResponse;
import be.pxl.services.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .organizationId(employeeRequest.getOrganizationId())
                .departmentId(employeeRequest.getDepartmentId())
                .position(employeeRequest.getPosition())
                .build();
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with id " + id + " not found!"));
        return mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> findByDepartmentId(Long departmentId) {
        List<Employee> employees = employeeRepository.findAllByDepartmentId(departmentId).orElse(null);
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employees with department id " + departmentId + " not found!"));
        //TODO: Check why line with elseThrow is not working
        if(employees.isEmpty() || employees == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees with department id " + departmentId + " found!");
        }
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> findByOrganizationId(Long organizationId) {
        List<Employee> employees = employeeRepository.findAllByOrganizationId(organizationId).orElse(null);
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found with organisation id " + organizationId + "!"));
        if(employees.isEmpty() || employees == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found with organisation id " + organizationId + "!");
        }
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .age(employee.getAge())
                .organizationId(employee.getOrganizationId())
                .departmentId(employee.getDepartmentId())
                .position(employee.getPosition())
                .build();
    }
}
