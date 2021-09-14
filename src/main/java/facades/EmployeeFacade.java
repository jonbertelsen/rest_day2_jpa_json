package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Employee getEmployeeById(int id){
      EntityManager em = emf.createEntityManager();
      try {
          Employee c = em.find(Employee.class,id);
          return c;
      } finally {
          em.close();
      }
    };
         
    public List<Employee> getEmployeesByName(String name){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name",Employee.class)
                    .setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    };
    
    public List<Employee> getAllEmployees(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Employee> query = 
                       em.createQuery("Select e from Employee e", Employee.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    };
     
   public void deleteAllEmployees(){
        EntityManager em = emf.createEntityManager();
        try {
                em.getTransaction().begin();
                Query query = em.createQuery("DELETE FROM Employee e");
                query.executeUpdate();
                em.getTransaction().commit();
        } finally {
            em.close();
        }
    }    
   
    public Employee createEmployee(String name, String address, int salary){
        EntityManager em = emf.createEntityManager();
        Employee e = new Employee(name, address, salary);
        try {
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
                return e;
        } finally {
            em.close();
        }
    }    
     
      public Employee getEmployeesWithHighestSalary(){
        EntityManager em = emf.createEntityManager();
        Employee res;
        try {
            Query q1 = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e2.salary) FROM Employee e2) ");
            try {
                res = (Employee) q1.getSingleResult();
            } catch (Exception e){
                throw(e);
            }
            return res;
        } finally {
            em.close();
        }
    };
    

}
