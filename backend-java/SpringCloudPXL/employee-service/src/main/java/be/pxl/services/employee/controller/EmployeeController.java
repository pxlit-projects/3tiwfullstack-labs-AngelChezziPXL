package be.pxl.services.employee.controller;

import be.pxl.services.employee.client.NotificationClient;
import be.pxl.services.employee.domain.dto.EmployeeRequest;
import be.pxl.services.employee.domain.dto.EmployeeResponse;
import be.pxl.services.employee.domain.dto.NotificationRequest;
import be.pxl.services.employee.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;
    private final NotificationClient notificationClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Employee Created")
                .to("Angel")
                .build();
        notificationClient.sendNotification(notificationRequest);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity(employeeService.findByDepartmentId(departmentId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity getEmployeesByOrganizationId(@PathVariable Long organizationId) {
        return new ResponseEntity(employeeService.findByOrganizationId(organizationId), HttpStatus.OK);
    }



}
