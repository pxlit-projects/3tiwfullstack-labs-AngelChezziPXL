package be.pxl.services.organization.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name="organization")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;

    // TODO: Hoe kan ik deze toevoegen aan de DB zodat de query van de repository kan werken?
    @Transient
    private List<Employee> employees = Collections.emptyList();
    @Transient
    private List<Department> departments = Collections.emptyList();
}
