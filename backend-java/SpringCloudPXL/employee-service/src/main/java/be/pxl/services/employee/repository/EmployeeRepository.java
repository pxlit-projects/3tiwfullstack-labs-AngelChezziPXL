package be.pxl.services.employee.repository;

import be.pxl.services.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<List<Employee>> findAllByDepartmentId(Long departmentId);
    Optional<List<Employee>> findAllByOrganizationId(Long organisationId);
}
