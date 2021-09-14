/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jobe
 */
public class EmployeeFacadeTest {
    
    private static Employee e1;
    private static Employee e2;
    private static Employee e3;
    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;
    
    public EmployeeFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("pu");
        facade = EmployeeFacade.getFacade(emf);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         System.out.println("Before each");
         facade.deleteAllEmployees();
         e1 = facade.createEmployee("Jønke Bønke","HA vænget 23", 45000);
         e2 = facade.createEmployee("Ole Fehår", "Kalshnikovgade 23", 28000);
         e3 = facade.createEmployee("Anders And", "Stenhusvænget 1", 47500);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetFacade() {
        System.out.println("getFacade");
        EmployeeFacade result = EmployeeFacade.getFacade(emf);
        assertNotNull(result);
    }

    @Test
    public void testGetEmployeeById() {
        System.out.println("getEmployeeById");
        int id = e2.getId().intValue();
        EmployeeFacade instance = EmployeeFacade.getFacade(emf);
        int expResult = id;
        Employee result = instance.getEmployeeById(id);
        assertEquals(expResult, (int)result.getId());
       
    }
    @Test
    public void testGetEmployeesByName() {
        System.out.println("getEmployeesByName");
        String name = "Jønke Bønke";
        int listSize = 1;
        EmployeeFacade instance = EmployeeFacade.getFacade(emf);
        List<Employee> result = instance.getEmployeesByName(name);
        assertEquals(listSize, result.size());
        assertEquals(e1, result.get(0));
    }

    @Test
    public void testGetAllEmployees() {
        System.out.println("getAllEmployees");
        EmployeeFacade instance = EmployeeFacade.getFacade(emf);
        List<Employee> result = instance.getAllEmployees();
        assertEquals(3, result.size());
        for (Employee e: result){
            assertTrue(e.equals(e1) || e.equals(e2) || e.equals(e3));
        }
    }

    @Test
    public void testDeleteAllEmployees() {
        System.out.println("deleteAllEmployees");
        EmployeeFacade instance = EmployeeFacade.getFacade(emf);
        instance.deleteAllEmployees();
        List<Employee> result = instance.getAllEmployees();
        assertEquals(0, result.size());
    }

    @Test
    public void testCreateEmployee() {
        System.out.println("createEmployee");
        String name = "Jon Bertelsen";
        String address = "Nordre Frihavnsgade 28";
        int salary = 100000;
        EmployeeFacade instance = EmployeeFacade.getFacade(emf);
        Employee e4 = instance.createEmployee(name, address, salary);
        List<Employee> result = instance.getAllEmployees();
        assertEquals(4, result.size());
    }
    
    @Test
    public void testGetEmployeesWithHighestSalary() {
        System.out.println("getEmployeesWithHighestSalary");
        EmployeeFacade instance = EmployeeFacade.getFacade(emf);
        String expResult = "Anders And";
        Employee result = instance.getEmployeesWithHighestSalary();
        System.out.println(result.toString());
        assertEquals(expResult, result.getName());
    }
    
}
