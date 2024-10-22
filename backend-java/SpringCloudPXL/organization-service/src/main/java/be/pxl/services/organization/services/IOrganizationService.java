package be.pxl.services.organization.services;

import be.pxl.services.organization.domain.dto.OrganizationRespond;

public interface IOrganizationService {


    OrganizationRespond findById(Long id);

    OrganizationRespond findByIdWithDepartments(Long id);

    public OrganizationRespond findByIdWithEmployees(Long id);

    public OrganizationRespond findByIdWithDepartmentsAndWithEmployees(Long id);
}
