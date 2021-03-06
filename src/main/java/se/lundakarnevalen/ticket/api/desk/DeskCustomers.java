package se.lundakarnevalen.ticket.api.desk;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import se.lundakarnevalen.ticket.api.Request;
import se.lundakarnevalen.ticket.db.Category;
import se.lundakarnevalen.ticket.db.Order;
import se.lundakarnevalen.ticket.db.Performance;
import se.lundakarnevalen.ticket.db.Rate;
import se.lundakarnevalen.ticket.db.Show;
import se.lundakarnevalen.ticket.db.Ticket;

@Path("/desk/customers")
@RolesAllowed("USER")
@Produces("application/json; charset=UTF-8")
public class DeskCustomers extends Request {
	@GET
	@Path("/create")
	public Response createCustomer(string data) throws SQLException {
		JSONObject input = new JSONObject(data);
		String name = input.getString("name"));
		String phone = input.getString("phone"));
		String email = input.getString("email"));
		assertNotNull(perf, 404);
		Customer customer = Customer.create(name, email, phone);
		return status(200).entity(customer).build();
	}

	@GET
	@Path("/{id}")
	public Response getCustomer(@PathParam("id") int id) throws SQLException {
		Customer customer = Customer.getSingle(id);
		assertNotNull(customer, 404);
		return status(200).entity(customer).build();
	}

	@GET
	@Path("/{id}/orders")
	public Response getOrders(@PathParam("id") int id) throws SQLException {
		Customer customer = Cutomer.getSingle(id);
		assertNotNull(order, 404);
		List<Order> orders = Orders.getByCustomer(id);
		return status(200).entity(orders).build();
	}
}
