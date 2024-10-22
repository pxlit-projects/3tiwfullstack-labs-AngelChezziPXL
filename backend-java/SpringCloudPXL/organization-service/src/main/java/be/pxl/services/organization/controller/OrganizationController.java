package be.pxl.services.organization.controller;

import be.pxl.services.organization.domain.dto.OrganizationRespond;
import be.pxl.services.organization.services.IOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrganizationController {
    private final IOrganizationService organizationService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationRespond getOrganizationById(@PathVariable("id") Long id) {
        return organizationService.findById(id);
    }

    @GetMapping("/{id}/with-departments")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationRespond getOrganizationByIdWithDepartments(@PathVariable("id") Long id) {
        return organizationService.findByIdWithDepartments(id);
    }

    @GetMapping("/{id}/with-employees")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationRespond getOrganizationByIdWithEmployees(@PathVariable("id") Long id) {
        return organizationService.findByIdWithEmployees(id);
    }

}
