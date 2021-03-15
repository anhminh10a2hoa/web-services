package restful.services;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import restful.data.Employee;
import restful.data.EmployeeDao;
@Path("/empService")
public class EmployeeService {
    
    
    EmployeeDao employeeDao = new EmployeeDao();
   
    
    @GET
    @Path("/all_employees_xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Employee> getemployeesXML() {
        return employeeDao.getAllEmployees();
    }
    
    
    @GET
    @Path("/all_employees_json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getemployeesJSON() {
        return employeeDao.getAllEmployees();
    }
    
    @GET
    @Path("/all_employees_json_by_name/{employeeName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployeesByName(@PathParam("employeeName") String employeeName) {
        return employeeDao.getAllEmployeesByName(employeeName);
    }
    
    @GET
    @Path("/all_employees_json_by_purchase/{purchase}/{higherOrLower}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployeesByPurchase(@PathParam("purchase") double purchase, @PathParam("higherOrLower") boolean higherOrLower) {
        return employeeDao.getAllEmployeesByPurchase(purchase, higherOrLower);
    }
    
    
    @GET
    @Path("/employees/{employeeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("employeeID") int employeeID) {
        return employeeDao.getEmployee(employeeID);
    }
    
    
    
    @POST
    @Path("/addEmployee")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addEmployee(@FormParam("id") int id, @FormParam("name") String name, @FormParam("phoneNumber") String phoneNumber,  @FormParam("purchase") double purchase,
            @Context HttpServletResponse servletResponse) throws IOException {
        Employee emp = new Employee(id, name, phoneNumber, purchase);
        return employeeDao.addEmployee(emp);
    }
    
    
    
    @POST
    @Path("/addEmployeeObject")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addEmployeeObject(Employee emp) {
        String feedback = "addEmployeeObject Service Response: " + employeeDao.addEmployee(emp);
        return Response.status(Response.Status.OK).entity(feedback).build();
    }
    
    
    
    @PUT
    @Path("/update_employee")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateEmployee(@FormParam("id") int id, @FormParam("name") String name, @FormParam("phoneNumber") String phoneNumber,  @FormParam("purchase") double purchase,
            @Context HttpServletResponse servletResponse) throws IOException {
        Employee emp = new Employee(id, name, phoneNumber, purchase);
        return employeeDao.updateEmployee(emp);
    }
    
    
    
    @DELETE
    @Path("/employees/{employeeID}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteEmployee(@PathParam("employeeID") int employeeID) {
        return employeeDao.deleteEmployee(employeeID);
    }
    
    
    
    @OPTIONS
    @Path("/operations")
    @Produces(MediaType.TEXT_XML)
    public Response getSupportedOperations() {
        
        String operationList = "<operations>GET, POST, PUT, DELETE, OPTIONS</operations>";
        
        return Response
                  .status(Response.Status.OK)
                  .header("Operations", operationList)
                  .header("content-type", MediaType.TEXT_XML)
                  .build();
    }
}