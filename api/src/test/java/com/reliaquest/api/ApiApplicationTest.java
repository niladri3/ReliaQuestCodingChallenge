package com.reliaquest.api;

import com.reliaquest.api.client.RQEmployeeClient;
import com.reliaquest.api.exception.ClientException;
import com.reliaquest.api.model.EmployeeCreateRequest;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.model.rqserver.RQEmployeeCreateRequest;
import com.reliaquest.api.model.rqserver.RQEmployeeDeleteRequest;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ApiApplicationTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private RQEmployeeClient rqEmployeeClient;

    private List<EmployeeResponse> createdEmployeeResponseList;

    @BeforeEach
    void setup() {
        createdEmployeeResponseList = createDummyEmployees();
    }

    @Test
    void createEmployeeReturnsResponse() {
        EmployeeCreateRequest employeeCreateRequest = EmployeeCreateRequest.builder().name("Mike").salary(5000).age(29).title("Engineer").build();
        EmployeeResponse employeeResponse = EmployeeResponse.builder().id(UUID.randomUUID()).employeeName("Mike").employeeAge(29).employeeTitle("Engineer")
                .employeeSalary(5000).employeeEmail("mike@owncompany.com").build();

        Mockito.when(rqEmployeeClient.createEmployee(RQEmployeeCreateRequest.from(employeeCreateRequest))).thenReturn(employeeResponse);
        EmployeeResponse result = employeeService.createEmployee(employeeCreateRequest);
        assertNotNull(result);
        assertEquals(employeeCreateRequest.getName(), result.getEmployeeName());
        assertEquals(employeeCreateRequest.getSalary(), result.getEmployeeSalary());
        assertEquals(employeeCreateRequest.getAge(), result.getEmployeeAge());
        assertEquals(employeeCreateRequest.getTitle(), result.getEmployeeTitle());
    }

    @Test
    void getEmployeeByIdReturnsEmployee(){
        Mockito.when(rqEmployeeClient.getEmployeeById("4942d668-d289-4d6d-a6d3-eb9d2c972ed1"))
                .thenReturn(EmployeeResponse.builder().id(UUID.fromString("4942d668-d289-4d6d-a6d3-eb9d2c972ed1")).employeeName("Stella Sauer").employeeSalary(446388)
                        .employeeAge(39).employeeTitle("Central Developer").employeeEmail("mcshayne@company.com").build());

        EmployeeResponse result = employeeService.getEmployeeById("4942d668-d289-4d6d-a6d3-eb9d2c972ed1");
        assertNotNull(result);
        assertEquals("Stella Sauer", result.getEmployeeName());
        assertEquals(446388, result.getEmployeeSalary());
        assertEquals(39, result.getEmployeeAge());
        assertEquals("Central Developer", result.getEmployeeTitle());
    }
    

    private List<EmployeeResponse> createDummyEmployees() {
        return Arrays.asList(
                EmployeeResponse.builder().id(UUID.fromString("7b4fa0f4-0503-4731-80f3-0454a76d9a59")).employeeName("Bernie Walsh").employeeSalary(477439)
                        .employeeAge(25).employeeTitle("Central Accounting Officer").employeeEmail("magik_mike@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("154f3948-98d2-437a-afb3-ef6a69c646cf")).employeeName("Denice Jaskolski").employeeSalary(460312).employeeAge(17)
                        .employeeTitle("Construction Agent").employeeEmail("bamity@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("fa93e301-f630-446a-a3ec-cde926c6ed30")).employeeName("Lavinia Mraz").employeeSalary(254784).employeeAge(29)
                        .employeeTitle("Global Planner").employeeEmail("lMraz@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("43a395d3-dc8b-4bf6-94b7-dcca530cb82b")).employeeName("Beryl Buckridge").employeeSalary(76617).employeeAge(63)
                        .employeeTitle("Chief Healthcare Associate").employeeEmail("voyatouch@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("f9add171-86a4-443c-9c10-99454520d12d")).employeeName("Mr. Winnifred White").employeeSalary(76617)
                        .employeeAge(63).employeeTitle("Chief Healthcare Associate").employeeEmail("bitchin_blair@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("2b9f70e3-03c2-4727-a7d1-f50d4b27792e")).employeeName("Mozell Becker").employeeSalary(460785)
                        .employeeAge(17).employeeTitle("Principal Legal Manager").employeeEmail("fixsan@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("433eb3ea-9788-49ed-b203-ba1910b73281")).employeeName("Dwight Schumm").employeeSalary(260091)
                        .employeeAge(65).employeeTitle("Central Manufacturing Engineer").employeeEmail("mcshayne@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("7df1f259-d7f4-4edf-8d4c-f99633af15a8")).employeeName("Devin Parisian").employeeSalary(112130)
                        .employeeAge(43).employeeTitle("Sales Planner").employeeEmail("omg_its_laura@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("fa93e301-f630-446a-a3ec-cde926c6ed30")).employeeName("Avery Leuschke").employeeSalary(384723)
                        .employeeAge(33).employeeTitle("IT Consultant").employeeEmail("xovictoriaox@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("4942d668-d289-4d6d-a6d3-eb9d2c972ed1")).employeeName("Stella Sauer").employeeSalary(446388)
                        .employeeAge(39).employeeTitle("Central Developer").employeeEmail("mcshayne@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("e7b35d86-0b05-47de-b03b-6a5cc2428102")).employeeName("Miss Dorian Sawayn").employeeSalary(368384)
                        .employeeAge(55).employeeTitle("Corporate Technology Agent").employeeEmail("mcshayne@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("4ae71d69-3569-4357-8ccb-419880c7df00")).employeeName("Benedict Waters").employeeSalary(464627)
                        .employeeAge(20).employeeTitle("Administration Developer").employeeEmail("opela@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("635d6706-0d3a-47ef-9971-101eeda3b096")).employeeName("Elanor Boyer").employeeSalary(142542)
                        .employeeAge(44).employeeTitle("Customer Assistant").employeeEmail("cardify@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("58113405-4790-4c55-83d1-e58e911e2a7a")).employeeName("Vickie Walter Jr.").employeeSalary(371911)
                        .employeeAge(67).employeeTitle("International Hospitality Manager").employeeEmail("bitchip@company.com").build(),

                EmployeeResponse.builder().id(UUID.fromString("87da2791-476a-4707-82be-7b8d606ae355")).employeeName("Arica Parker DDS").employeeSalary(60622)
                        .employeeAge(33).employeeTitle("Chief Designer").employeeEmail("mcshayne@company.com").build());
    }
}
