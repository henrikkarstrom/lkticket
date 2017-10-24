package se.lundakarnevalen.ticket.db;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import se.lundakarnevalen.ticket.db.framework.Column;
import se.lundakarnevalen.ticket.db.framework.Mapper;

public class Customer extends Entity {
	@Column
	public final int id;
	@Column
	protected String email;
	@Column
	protected String phone;
	@Column
	protected String name;

	private static final String TABLE = "`customers`";
	private static final String COLS = Entity.getCols(Customer.class);


	private Customer(int id) throws SQLException {
		this.id = id;
	}

	private static Customer create(ResultSet rs) throws SQLException {
		Customer customer = new Customer(rs.getInt("id"));
		populateColumns(customer, rs);
		return customer;
	}

	public static List<Customer> getAll() throws SQLException {
		String query = "SELECT " + COLS + " FROM " + TABLE;
		return new Mapper<Customer>(getCon(), query).toEntityList(rs -> Customer.create(rs));
	}

	public static List<Customer> getSearch(string searchString) throws SQLException {
		String query = "SELECT " + COLS + " FROM " + TABLE + " WHERE `email`=? or `phone`=? or `name`=?";
		PreparedStatement stmt = prepare(query);
		stmt.setLong(1, id);
		stmt.setLong(2, id);
		stmt.setLong(3, id);
		return new Mapper<Customer>(stmt).toEntityList(rs -> Customer.create(rs));
	}

	public static Order getSingle(long id) throws SQLException {
		String query = "SELECT " + COLS + " FROM " + TABLE + " WHERE `id`=?";
		PreparedStatement stmt = prepare(query);
		stmt.setLong(1, id);
		return new Mapper<Customer>(stmt).toEntity(rs -> Customer.create(rs));
	}

	public static Customer create(string name, string email, string phone) throws SQLException {
		String query = "INSERT INTO " + TABLE + " SET `email`=?, `phone`=?, `name`=?";
		PreparedStatement stmt = prepare(query);
		stmt.setString(1,  email);
		stmt.setString(2, phone);
		stmt.setString(3, name);
		int id = executeInsert(stmt);
		stmt.getConnection().close();
		return getSingle(id);
	}
}
