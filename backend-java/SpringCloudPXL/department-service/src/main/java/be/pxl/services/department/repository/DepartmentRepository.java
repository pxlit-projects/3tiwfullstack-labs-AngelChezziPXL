package be.pxl.services.department.repository;

import be.pxl.services.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<List<Department>> findAllByOrganizationId(Long organizationId);

    @Query("SELECT d FROM Department d WHERE d.organization.id = :organizationId AND SIZE(d.employees) > 0")
    Optional<List<Department>> findAllWithEmployeesByOrganizationId(Long organizationId);
}
