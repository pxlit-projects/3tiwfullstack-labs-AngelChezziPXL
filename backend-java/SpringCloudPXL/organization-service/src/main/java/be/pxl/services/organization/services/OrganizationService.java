package be.pxl.services.organization.services;

import be.pxl.services.organization.client.DepartmentClient;
import be.pxl.services.organization.client.EmployeeClient;
import be.pxl.services.organization.domain.Employee;
import be.pxl.services.organization.domain.Department;
import be.pxl.services.organization.domain.Organization;
import be.pxl.services.organization.domain.dto.OrganizationRespond;
import be.pxl.services.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {
    private final OrganizationRepository organizationRepository;
    private final EmployeeClient employeeClient;
    private final DepartmentClient departmentClient;

    @Override
    public OrganizationRespond findById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization with id " + id + " not found."));
        return convertOrganizationToOrganizationRespond(organization);
    }

    // TODO: QUESTION: Is het ok om te fileren of kun je beter een query voorzien in de repository interface?
    @Override
    public OrganizationRespond findByIdWithDepartments(Long id) {
        Organization organization = organizationRepository.findById(id)
//                .filter(o -> !o.getDepartments().isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization with departments found for organization id " + id));

        List<Department> departments = departmentClient.getDepartmentByOrganizationId(id);
        organization.setDepartments(departments);
        return convertOrganizationToOrganizationRespond(organization);
    }

    // TODO: QUESTION: Is het ok om te fileren of kun je beter een query voorzien in de repository interface?
    @Override
    public OrganizationRespond findByIdWithDepartmentsAndWithEmployees(Long id) {
        Organization organization = organizationRepository.findById(id)
                .filter(o -> !o.getEmployees().isEmpty() && !o.getDepartments().isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization with employees and with departments found for id " + id));
        return convertOrganizationToOrganizationRespond(organization);
    }

    // TODO: QUESTION: Is het ok om te fileren of kun je beter een query voorzien in de repository interface?
    @Override
    public OrganizationRespond findByIdWithEmployees(Long id) {
        Organization organization = organizationRepository.findById(id)
//                .filter(o -> !o.getEmployees().isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization with employees found for id " + id));

        List<Employee> employees = employeeClient.getEmployeesByOrganizationId(id);
        organization.setEmployees(employees);
        return convertOrganizationToOrganizationRespond(organization);
    }

    private OrganizationRespond convertOrganizationToOrganizationRespond(Organization organization) {
        return OrganizationRespond.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .departments(organization.getDepartments())
                .build();
    }
}
