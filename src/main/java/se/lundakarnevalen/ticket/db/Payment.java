package se.lundakarnevalen.ticket.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;

import se.lundakarnevalen.ticket.db.framework.Column;
import se.lundakarnevalen.ticket.db.framework.Mapper;

public class Payment extends Entity {
	@Column
	protected int id;
	@Column
	protected int transaction_id;
	@Column
	protected int order_id;
	@Column
	protected String paymentmethod;
	@Column
	protected String paymentreference;

	private static final String TABLE = "`payments`";
	private static final String COLS = Entity.getCols(Price.class);

	private Payment() throws SQLException {
	}

	private static Payment create(ResultSet rs) throws SQLException {
		Payment rate = new Payment();
		populateColumns(rate, rs);
		return rate;
	}



	public static Payment createPaymentAllUnpaid(int user_id, int order_id, int amount, String paymentMethod, String paymentreference) throws SQLException, JSONException {
		
		Connection con = getCon();
		try {
			con.setAutoCommit(false);

			int transactionId = Transaction.create(con, user_id, id, 0);
			String query = "SELECT * FROM tickets as t join seats as s on s.active_ticket_id = t.id where `order_id`=?, `paid`=? FOR UPDATE";
			PreparedStatement stmt = prepare(query);
			stmt.setInt(1, order_id);
			stmt.setBoolean(2, false);

			List<Ticket> tickets = new Mapper<Ticket>(stmt).toEntityList(rs -> Ticket.create(rs));

			int totalAmount = 0;

			for(Tricket ticket : tickets){
				totalAmount += ticket.price;
			}

			if(amount == totalAmount)
			{
				String createSql = "INSERT INTO payments SET `order_id`=?, `transaction_id`=?, `amount`=?, `paymentmethod`=?, `paymentreference`=?";
				PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, order_id);
				stmt.setInt(2, transaction_id);
				stmt.setInt(3, amount);
				stmt.setInt(4, paymentMethod);
				stmt.setInt(5, paymentreference);
				int paymentId = executeInsert(stmt);
			}

			for(Tricket ticket : tickets){
				ticket.SetAsPaid(transactionId);
			}

	}

}
