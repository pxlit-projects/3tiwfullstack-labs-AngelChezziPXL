package be.pxl.services.employee;

import be.pxl.services.employee.domain.Employee;
import be.pxl.services.employee.domain.dto.EmployeeResponse;
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
import org.testcontainers.shaded.org.apache.commons.lang3.NotImplementedException;

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
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;
//    @MockBean
//    private IEmployeeService employeeService;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:8.3");

    @DynamicPropertySource
    static void registerDeynamicMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setup(TestInfo testInfo) {
        employeeRepository.deleteAll();
        if(!testInfo.getTags().contains("SkipSeedingData"))
        {
            List<Employee> seedEmployees = new ArrayList<>();
            seedEmployees.add(Employee.builder().id(1L).age(22).organizationId(1L).departmentId(1L).name("John").position("student").build());
            seedEmployees.add(Employee.builder().id(2L).age(35).organizationId(1L).departmentId(1L).name("Piet").position("teacher").build());
            seedEmployees.add(Employee.builder().id(3L).age(32).organizationId(1L).departmentId(2L).name("Luc").position("teacher").build());
            seedEmployees.add(Employee.builder().id(4L).age(21).organizationId(2L).departmentId(1L).name("Jef").position("student").build());
            seedEmployees.add(Employee.builder().id(5L).age(20).name("Lisa").position("student").build());
            seedEmployees.add(Employee.builder().id(6L).organizationId(2L).departmentId(3L).build());

            employeeRepository.saveAll(seedEmployees);
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


    //Todo: QUESTION: Waarom is de test groen als ik deze apart run en rood als ik ze allemaal via de class run?
    @Test
    public  void testGetEmployees() throws Exception {
        List<Employee> seedEmployees = employeeRepository.findAll();

        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee : seedEmployees) {
            employeeResponses.add(EmployeeResponse.builder()
                    .id(employee.getId())
                    .age(employee.getAge())
                    .name(employee.getName())
                    .position(employee.getPosition())
                    .departmentId(employee.getDepartmentId())
                    .organizationId(employee.getOrganizationId())
                    .build()
            );
        }
        //TODO: QUESTION: Mag de service gemocked worden of moet de Testcontainer DB gebruikt worden met de repository?
        //when(employeeService.getAllEmployees()).thenReturn(employeeResponses);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<EmployeeResponse> responseEmployees = objectMapper.readValue(responseContent, new TypeReference<List<EmployeeResponse>>() {
        });

        assertEquals(employeeResponses, responseEmployees);
    }

    @Test
    public  void testGetEmployeeById() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        Long employeeId = 3L;
        Employee employee = employees.stream().filter(e -> e.getId().equals(employeeId)).findFirst().get();

        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .id(employee.getId())
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();

        // test for id that exists
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        EmployeeResponse responseEmployee = objectMapper.readValue(responseContent, new TypeReference<EmployeeResponse>() {
        });

        assertEquals(employeeResponse, responseEmployee);

        // test for non existing id
        Long nonExistingEmployeeId = 99L;
        MvcResult resultNotExistId = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + nonExistingEmployeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseNotExistIdContent = resultNotExistId.getResponse().getErrorMessage();
        assertEquals("Employee with id " + nonExistingEmployeeId + " not found!", responseNotExistIdContent);

    }

    @Test
    public void testGetEmployeesByDepartmentId() throws Exception {
        List<Employee> employees = employeeRepository.findAll();

        //test for departments with employees
        Long departmentId = 1L;
        List<EmployeeResponse> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(mapEmployeeToEmployeeResponse(employees.get(0)));
        expectedEmployees.add(mapEmployeeToEmployeeResponse(employees.get(1)));
        expectedEmployees.add(mapEmployeeToEmployeeResponse(employees.get(3)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + departmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        List<EmployeeResponse> employeesResponse = objectMapper.readValue(responseContent, new TypeReference<List<EmployeeResponse>>() {});
        assertEquals(expectedEmployees, employeesResponse, "Should return only the employees in the department.");

        //ToDo: Add test for non existing departmentId
        Long nonExistingDepartmentId = 99L;
        MvcResult resultNonExistingDepartmentId = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + nonExistingDepartmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseResultNonExistingDepartmentIdContent = resultNonExistingDepartmentId.getResponse().getErrorMessage();
        assertEquals("Employees with department id " + nonExistingDepartmentId + " not found!", responseResultNonExistingDepartmentIdContent, "A wrong deparmentId should return a Not Found status code!");

    }

    @Test
    public void testGetEmployeesByOrganizationId() throws Exception {
        //ToDo: Add test for existing organizationId
        List<Employee> employees = employeeRepository.findAll();
        Long organizationId = 1L;
        List<EmployeeResponse> employeesResponseExpected = Arrays.asList(
                mapEmployeeToEmployeeResponse(employees.get(0)),
                mapEmployeeToEmployeeResponse(employees.get(1)),
                mapEmployeeToEmployeeResponse(employees.get(2)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + organizationId)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<EmployeeResponse> employeeResponse = objectMapper.readValue(responseContent, new TypeReference<List<EmployeeResponse>>() {});
        assertEquals(employeesResponseExpected, employeeResponse);
        //ToDo: Add test for non existing organizationId

        Long nonExistingOrganizationId = 99L;
        result = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + nonExistingOrganizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String errorMessageNotExistingOrganizationId = result.getResponse().getErrorMessage();
        assertEquals("No employees found with organisation id " + nonExistingOrganizationId + "!", errorMessageNotExistingOrganizationId);
    }


    //Helper function
    private EmployeeResponse mapEmployeeToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();
    }

}
