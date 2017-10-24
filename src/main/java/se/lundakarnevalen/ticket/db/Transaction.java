package se.lundakarnevalen.ticket.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.mysql.cj.api.jdbc.Statement;

import se.lundakarnevalen.ticket.db.framework.Column;
import se.lundakarnevalen.ticket.db.framework.Table;
import se.lundakarnevalen.ticket.db.framework.Mapper;

@Table(name = "transactions")
public class Transaction extends Entity {
	@Column
	public final int id;
	@Column
	protected String user_id;
	@Column
	protected Timestamp date;
	@Column
	protected int order_id;
	@Column
	protected int profile_id;


	private static final String TABLE = "`transactions` ";
	private static final String COLS = Entity.getCols(Transaction.class);

	private Transaction(int id) throws SQLException {
		this.id = id;
	}

	private static Transaction create(ResultSet rs) throws SQLException {
		Transaction transaction = new Transaction(rs.getInt("id"));
		populateColumns(transaction, rs);
		return transaction;
	}

	public static Transaction getSingle(long id) throws SQLException {
		String query = "SELECT " + COLS + " FROM " + TABLE + " WHERE `id`=?";
		PreparedStatement stmt = prepare(query);
		stmt.setLong(1, id);
		return new Mapper<Transaction>(stmt).toEntity(rs -> Ticket.create(rs));
	}


	public static int create(Connection con, int user_id, int order_id, int profile_id) throws SQLException {
		String query = "INSERT INTO `transactions` SET `user_id`=?, `order_id`=?, `profile_id`=?";
		PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, user_id);
		stmt.setInt(2, order_id);
		stmt.setInt(3, profile_id);
		return executeInsert(stmt);
	}	
}
