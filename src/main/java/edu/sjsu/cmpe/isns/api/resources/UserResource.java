package edu.sjsu.cmpe.isns.api.resources;

import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe.isns.domain.User;
import edu.sjsu.cmpe.isns.dto.UserDto;
import edu.sjsu.cmpe.isns.dto.UsersDto;
import edu.sjsu.cmpe.isns.repository.DBConnection;
import edu.sjsu.cmpe.isns.ui.views.HomeView;


@Path("isns/v1/users")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {           
    public UserResource() 
    {
        // do nothing
    }
    
    @POST
    //@Consumes(MediaType.TEXT_HTML)
    public Response addUser(@FormParam("firstname") String firstname,
    		@FormParam("lastname") String lastname,
    		@FormParam("email") String email,
    		@FormParam("phno") String phno,
    		@FormParam("dept") String department,
    		@FormParam("username") String username,
    		@FormParam("password") String password) throws UnknownHostException 
    {
    	
    	User request=new User();
    	request.setFirstName(firstname);
    	request.setLastName(lastname);
    	request.seteMail(email);
    	request.setPhoneNumber(phno);
    	request.setDepartment(department);
    	request.setUserName(username);
    	request.setPassword(password);
    	System.out.println("User Resource"+username);
    	//ObjectMapper mapper = new ObjectMapper();
    	//JsonGenerator jgen;
		//mapper.writeValue(jgen, request);
    	UserDto ud=new UserDto(request);
        ud.makeConnection();
        ud.createUser(request);
        return Response.status(200)
    			.entity(new HomeView("adminpage.mustache"))
    			.build();
    }
    
    @GET
    @Path("/allusers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() throws UnknownHostException 
    {
    	System.out.println("In users");
    	UsersDto usersResponse = new UsersDto();
    	usersResponse.getUsers();      
        return Response.status(200)
    			.entity(usersResponse)
    			.build();
    }

    @GET
   // @Path("/alldeptusers")
    @Path("/alldeptusers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getdeptUsers(@QueryParam("dept") String department) throws UnknownHostException 
    {
    	UsersDto usersResponse1 = new UsersDto();
    	//List<User> usr=usersResponse1.getAlldeptUsers(department);
    	System.out.println(usersResponse1);
        return Response.status(200)
    			.entity(usersResponse1.getAlldeptUsers(department))
    			.build();
    }
        
   }
