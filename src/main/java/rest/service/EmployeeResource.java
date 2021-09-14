package rest.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.EmployeeDTO;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("employee")
public class EmployeeResource {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    EmployeeFacade em =  EmployeeFacade.getFacade(emf);
    
     private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllEmployees() {
        List<Employee> e = em.getAllEmployees();
        List<EmployeeDTO> eDTO = new LinkedList<>();
        for (Employee eTemp: e){
            eDTO.add(new EmployeeDTO(eTemp));   
        }
        return gson.toJson(eDTO);
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeById(@PathParam("id") int id) {
        Employee e = em.getEmployeeById(id);
        EmployeeDTO eDTO = new EmployeeDTO(e);
        return gson.toJson(eDTO);
    }
    
    @GET
    @Path("/heighestpaid")
    @Produces({MediaType.APPLICATION_JSON})
    public String getHighestPaidEmployee() {
        Employee e = em.getEmployeesWithHighestSalary();
        EmployeeDTO eDTO = new EmployeeDTO(e);
        return gson.toJson(eDTO);
    }
    
    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getEmployeeByName(@PathParam("name") String name) {
        List<Employee> e = em.getEmployeesByName(name);
        List<EmployeeDTO> eDTO = new LinkedList<>();
        for (Employee eTemp: e){
            eDTO.add(new EmployeeDTO(eTemp));   
        }
        return gson.toJson(eDTO);
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        throw new UnsupportedOperationException();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Employee entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
