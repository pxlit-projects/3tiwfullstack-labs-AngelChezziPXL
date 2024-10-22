package be.pxl.services.organization.repository;

import be.pxl.services.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    //TODO: QUESTION: Is het ok om te fileren of kun je beter een query voorzien in de repository interface?
    // with employees, with departments, with departments en with employees
}
