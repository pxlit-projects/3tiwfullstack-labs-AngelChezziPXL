package be.pxl.services.organization.services;

import be.pxl.services.organization.domain.Organization;
import be.pxl.services.organization.domain.dto.OrganizationRespond;
import be.pxl.services.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {
    private final OrganizationRepository organizationRepository;

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
                .filter(o -> !o.getDepartments().isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization with departments found for organization id " + id));
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
                .filter(o -> !o.getEmployees().isEmpty())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization with employees found for id " + id));
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
