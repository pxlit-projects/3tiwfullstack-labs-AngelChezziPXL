package be.pxl.services.employee.config;

import be.pxl.services.employee.domain.Employee;
import be.pxl.services.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        if(employeeRepository.count() == 0) {
            List<Employee> employees = employeeRepository.saveAll(Arrays.asList(
                    // High School Staff
                    Employee.builder().name("Jan").age(24).position("Math Teacher").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Anna").age(28).position("Science Teacher").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Tom").age(32).position("History Teacher").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Sara").age(27).position("Physical Education Teacher").departmentId(3L).organizationId(1L).build(),
                    Employee.builder().name("Lucas").age(29).position("Art Teacher").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Olivia").age(35).position("Music Teacher").departmentId(3L).organizationId(3L).build(),
                    Employee.builder().name("James").age(26).position("English Teacher").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Emily").age(30).position("Assistant Principal").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Michael").age(38).position("Principal").departmentId(1L).organizationId(3L).build(),
                    Employee.builder().name("Sophia").age(33).position("Guidance Counselor").departmentId(3L).organizationId(3L).build(),
                    Employee.builder().name("Daniel").age(45).position("School Librarian").departmentId(2L).organizationId(1L).build(),
                    Employee.builder().name("Grace").age(40).position("School Nurse").departmentId(3L).organizationId(1L).build(),
                    Employee.builder().name("Jack").age(25).position("IT Specialist").departmentId(1L).organizationId(2L).build(),
                    Employee.builder().name("Chloe").age(29).position("Chemistry Teacher").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Henry").age(34).position("Biology Teacher").departmentId(1L).organizationId(3L).build(),
                    Employee.builder().name("Ava").age(31).position("Drama Teacher").departmentId(3L).organizationId(3L).build(),
                    Employee.builder().name("Ethan").age(27).position("Substitute Teacher").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Isabella").age(36).position("Office Administrator").departmentId(3L).organizationId(2L).build(),
                    Employee.builder().name("Noah").age(39).position("Dean of Students").departmentId(2L).organizationId(3L).build(),
                    Employee.builder().name("Mia").age(23).position("Teaching Assistant").departmentId(1L).organizationId(2L).build(),

                    // Students
                    Employee.builder().name("Liam").age(15).position("Student").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Emma").age(16).position("Student").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Noah").age(14).position("Student").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Olivia").age(17).position("Student").departmentId(3L).organizationId(1L).build(),
                    Employee.builder().name("William").age(16).position("Student").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Sophia").age(15).position("Student").departmentId(3L).organizationId(3L).build(),
                    Employee.builder().name("James").age(14).position("Student").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Ava").age(17).position("Student").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Benjamin").age(18).position("Student").departmentId(1L).organizationId(3L).build(),
                    Employee.builder().name("Charlotte").age(15).position("Student").departmentId(3L).organizationId(3L).build(),
                    Employee.builder().name("Lucas").age(14).position("Student").departmentId(2L).organizationId(1L).build(),
                    Employee.builder().name("Amelia").age(16).position("Student").departmentId(3L).organizationId(1L).build(),
                    Employee.builder().name("Mason").age(17).position("Student").departmentId(1L).organizationId(2L).build(),
                    Employee.builder().name("Harper").age(15).position("Student").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Ethan").age(16).position("Student").departmentId(1L).organizationId(3L).build(),
                    Employee.builder().name("Ella").age(14).position("Student").departmentId(3L).organizationId(3L).build(),
                    Employee.builder().name("Alexander").age(17).position("Student").departmentId(1L).organizationId(1L).build(),
                    Employee.builder().name("Isabella").age(16).position("Student").departmentId(2L).organizationId(2L).build(),
                    Employee.builder().name("Logan").age(15).position("Student").departmentId(2L).organizationId(3L).build(),
                    Employee.builder().name("Mia").age(14).position("Student").departmentId(1L).organizationId(2L).build()
            ));
        }

    }
}
