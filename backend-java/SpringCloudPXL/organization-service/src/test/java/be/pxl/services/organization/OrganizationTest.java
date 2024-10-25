package be.pxl.services.organization;

import be.pxl.services.organization.domain.Organization;
import be.pxl.services.organization.repository.OrganizationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.NotImplementedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: add all annotations and missing parts for this class
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class OrganizationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7");

    @DynamicPropertySource
    private static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    public void setup() {
        organizationRepository.deleteAll();
        organizationRepository.saveAll(List.of(
                Organization.builder().id(1L).name("PXL").address("ElfdeLiniestraat Hasselt").build(),
                Organization.builder().id(2L).name("UCLL").address("Diepenbeek").build(),
                Organization.builder().id(3L).name("UHasselt").address("Diepenbeek").build()
        ));
    }

    @Test
    public void testGetOrganizationById() throws Exception {
        String apiUrl = "/api/organization/";
        long existingOrganizationId = 1L;
        long nonExistingOrganizationId = -1L;
        MvcResult result;
        String responseContent;

        // test for existing ID
        Organization expectedOrganizations = organizationRepository.findById(existingOrganizationId).orElseThrow(()-> new Exception("Error finding ID in repository!"));
        result = mockMvc.perform(MockMvcRequestBuilders.get(apiUrl + existingOrganizationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        responseContent = result.getResponse().getContentAsString();
        Organization actualOrganizations = objectMapper.readValue(responseContent, new TypeReference<>() {
        });
        assertEquals(expectedOrganizations, actualOrganizations, "Should return 200 code with data.");

        // test for non existing ID
        String expectedErrorMessage = "Organization not found!";
        result = mockMvc.perform(MockMvcRequestBuilders.get(apiUrl, nonExistingOrganizationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualErrorMessage = result.getResponse().getErrorMessage();
        assertEquals(actualErrorMessage, actualErrorMessage, "Should return 404 code with error message.");
    }

    public void testGetOrganizationByIdWithDepartments() throws Exception {
        long existingOrganizationId = 1L;
        long nonExistingOrganizationId = -1L;
        MvcResult result;
        String responseContent;
        //TODO: write test for 'public OrganizationRespond getOrganizationByIdWithDepartments'
        throw new NotImplementedException();
    }

    public void testGetOrganizationByIdWithEmployees() throws Exception {
        long existingOrganizationId = 1L;
        long nonExistingOrganizationId = -1L;
        MvcResult result;
        String responseContent;
        //TODO: write test for 'public OrganizationRespond getOrganizationByIdWithEmployees'
    }

}
