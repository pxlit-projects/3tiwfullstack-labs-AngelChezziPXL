package be.pxl.services.department.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "department")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long organizationId;
    private String name;

    // TODO: Hoe kan ik deze toevoegen aan de DB zodat de query van de repository kan werken?
    @Transient
    private List<Employee> employees = Collections.emptyList();
    private String position;
}
