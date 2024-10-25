package be.pxl.services.employee;

import be.pxl.services.employee.domain.Employee;
import be.pxl.services.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class will test all the endpoints of the EmployeeController class by use of MockMvc and a TestContainer
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7");

    @DynamicPropertySource
    static void registerDynamicMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setup(TestInfo testInfo) {
        employeeRepository.deleteAll();
        if(!testInfo.getTags().contains("SkipSeedingData"))
        {
            employeeRepository.saveAll(List.of(
                Employee.builder().id(1L).age(22).organizationId(1L).departmentId(1L).name("John").position("student").build(),
                Employee.builder().id(2L).age(35).organizationId(1L).departmentId(1L).name("Piet").position("teacher").build(),
                Employee.builder().id(3L).age(32).organizationId(1L).departmentId(2L).name("Luc").position("teacher").build(),
                Employee.builder().id(4L).age(21).organizationId(2L).departmentId(1L).name("Jef").position("student").build(),
                Employee.builder().id(5L).age(20).name("Lisa").position("student").build(),
                Employee.builder().id(6L).organizationId(2L).departmentId(3L).build()
            ));
        }
    }

    @Test
    @Tag("SkipSeedingData")
    public  void testCreateEmployee() throws Exception {

        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();
        String employeeString = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1, employeeRepository.findAll().size());

        //TODO: add test for wrong entry
    }

    @Test
    public  void testGetEmployees() throws Exception {
        List<Employee> expectedEmployees = employeeRepository.findAll();

        //TODO: QUESTION: Moet de service gemocked worden of is het beter om de Testcontainer DB te gebruiken met de repository?
        //when(employeeService.getAllEmployees()).thenReturn(employeeResponses);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Employee> actualEmployees = objectMapper.readValue(responseContent, new TypeReference<>() {
        });

        assertEquals(expectedEmployees, actualEmployees);
    }

    //Todo: QUESTION: Waarom is de test groen als ik deze apart run en rood als ik ze allemaal via de class run?
    // Om één of reden kent het systeem tijden de setup voor de ids andere waardes toe. Ik heb niet kunnen vinden waar het aan ligt.
    @Test
    public  void testGetEmployeeById() throws Exception {
        Long existingEmployeeId = 3L;
        List<Employee> employees = employeeRepository.findAll();
        Employee expectedEmployee = (Employee) employees.get(2);

        // test for id that exists
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + existingEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Employee actualEmployee = objectMapper.readValue(responseContent, new TypeReference<>() {});

        assertEquals(expectedEmployee, actualEmployee);

        // test for non existing id
        Long nonExistingEmployeeId = 99L;
        String expectedErrorMessage = "Employee with id " + nonExistingEmployeeId + " not found!";
        MvcResult resultNotExistingId = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + nonExistingEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualErrorMessage = resultNotExistingId.getResponse().getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);

    }

    @Test
    public void testGetEmployeesByDepartmentId() throws Exception {
        List<Employee> employees = employeeRepository.findAll();

        //test for departments with employees
        Long departmentId = 1L;
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(employees.get(0));
        expectedEmployees.add(employees.get(1));
        expectedEmployees.add(employees.get(3));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + departmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<Employee> actualEmployees = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(expectedEmployees, actualEmployees, "Should return only the employees in the department.");

        //Test for non existing departmentId
        Long nonExistingDepartmentId = 99L;
        String expectedErrorMessage = "No employees with department id " + nonExistingDepartmentId + " found!";
        MvcResult resultNonExistingDepartmentId = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + nonExistingDepartmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualErrorMessage = resultNonExistingDepartmentId.getResponse().getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage, "A wrong deparmentId should return a Not Found status code!");

    }

    @Test
    public void testGetEmployeesByOrganizationId() throws Exception {
        //Test for existing organizationId
        List<Employee> employees = employeeRepository.findAll();
        Long organizationId = 1L;
        List<Employee> expectedEmployees = Arrays.asList(
                employees.get(0),
                employees.get(1),
                employees.get(2)
        );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + organizationId)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Employee> actualEmployees = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(expectedEmployees, actualEmployees);

        //Test for non existing organizationId
        Long nonExistingOrganizationId = 99L;
        String expectedErrorMessage = "No employees found with organisation id " + nonExistingOrganizationId + "!";
        result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + nonExistingOrganizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualErrorMessage = result.getResponse().getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}
