package be.pxl.services.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OrganizationServiceApplication. *
 */
@SpringBootApplication
@EnableFeignClients
public class OrganizationServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(OrganizationServiceApplication.class, args);
    }
}
