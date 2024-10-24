package be.pxl.services.department;

import be.pxl.services.department.domain.Department;
import be.pxl.services.department.repository.DepartmentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
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

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Container
    private static MySQLContainer mysqlContainer = new MySQLContainer("mysql:5.7");

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp(TestInfo testInfo){
        departmentRepository.deleteAll();
        departmentRepository.saveAll(List.of(
                Department.builder().id(1L).name("Math").organizationId(1L).position("UNK").build(),
                Department.builder().id(2L).name("Geography").organizationId(1L).position("UNK").build(),
                Department.builder().id(3L).name("History").organizationId(1L).position("UNK").build(),
                Department.builder().id(4L).name("Dutch").organizationId(2L).position("UNK").build(),
                Department.builder().id(5L).name("French").organizationId(2L).position("UNK").build(),
                Department.builder().id(6L).name("Phisics").organizationId(3L).position("UNK").build()
        ));
    }

    @Test
    public void testGetAllDepartMents() throws Exception {
        List<Department> departments = departmentRepository.findAll();
        String expectedDepartments = objectMapper.writeValueAsString(departments);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/department").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();

        assertEquals(expectedDepartments, resultContent, "Should return all departments from the database as a string.");
    }

   @Test
   public void testGetDepartmentById() throws Exception {
        // existing Id
       Long existingDepartmentId = 1L;
       Department expectedDepartments = departmentRepository.findById(existingDepartmentId).orElseThrow();
       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + existingDepartmentId).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andReturn();

       String resultContent = result.getResponse().getContentAsString();
       Department actualDepartment = objectMapper.readValue(resultContent, Department.class);
       assertEquals(expectedDepartments, actualDepartment, "Should return all departments from the database and a 200 code.");



       // Non existing Id
       Long nonExistingDepartmentId = -5L;
       String expectedErrorMessage = "Department not found";
       result = mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + nonExistingDepartmentId).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andReturn();

       String actualErrorMessage = result.getResponse().getErrorMessage();
       assertEquals(expectedErrorMessage, actualErrorMessage, "Should return a 404 code with an error message.");
   }

    @Test
    public void testGetDepartmentByOrganizationId() throws Exception {
        // Test for existing organizationId
        Long existingOrganizationId = 1L;
        List<Department> expectedDepartments = departmentRepository.findAllByOrganizationId(existingOrganizationId).orElseThrow();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + existingOrganizationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<Department> actualDepartments = objectMapper.readValue(resultContent, new TypeReference<List<Department>>() {});
        assertEquals(expectedDepartments, actualDepartments, "Should return 200 code and data.");

        // Test for non existing organizationId
        Long nonExistingOrganizationId = -5L;
        String expectedErrorMessage = "Department not found";
        result = mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + nonExistingOrganizationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualErrorMessage = result.getResponse().getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage, "Should return a 404 code with an error message.");
    }

    @Test
    public void testGetDepartmentByOrganizationIdWithEmployees() throws Exception {
        //TODO: write test for 'public List<DepartmentResponse> getDepartmentByOrganizationIdWithEmployees'
        throw new NotImplementedException();
    }


}
