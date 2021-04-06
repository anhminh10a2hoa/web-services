package rest.file.service;

import java.io.InputStream;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import helper.FileHandler;
import ws.customer.data.Customer;

@Path("/file/service")
public class FileHandlerService {
	
	FileHandler FileHandler = new FileHandler();
	
	@POST
	@Path("/upload/{filename}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadAttachment(@PathParam("filename") String fileName, InputStream attachmentInputStream) {

		return FileHandler.saveFile(attachmentInputStream, fileName);

	}
	
	@GET
	@Path("/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFilebyQuery(@QueryParam("filename") String fileName) {
		return FileHandler.download(fileName);
	}
	
	@GET
	@Path("/download/{filename}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFilebyPath(@PathParam("filename") String fileName) {
		return FileHandler.download(fileName);
	}
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Customer> getAllCustomer() {
		return FileHandler.getAllCustomer();
	}
		
	@GET
	@Path("/customers/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomerById(@PathParam("employeeId") int id) {
		return FileHandler.getCustomerById(id);
	}
	
	@DELETE
    @Path("/deleteCustomer/{employeeID}")
	@Produces(MediaType.APPLICATION_JSON)
    public String deleteCustomerById(@PathParam("employeeID") int id) {
        return FileHandler.deleteCustomerById(id);
    }
	
	@PUT
    @Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerById(Customer customer) {
		String feedback =  FileHandler.updateCustomerById(customer);
        return Response.status(Response.Status.OK).entity(feedback).build();
    }
	
	@POST
	@Path("/addCustomers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(Customer customer) {
		String feedback = FileHandler.addCustomer(customer);
		return Response.status(Response.Status.OK).entity(feedback).build();
	}

}