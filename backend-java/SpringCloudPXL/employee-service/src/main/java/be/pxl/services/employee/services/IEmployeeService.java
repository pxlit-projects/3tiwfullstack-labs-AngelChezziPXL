package be.pxl.services.employee.services;

import be.pxl.services.employee.domain.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees();
}
