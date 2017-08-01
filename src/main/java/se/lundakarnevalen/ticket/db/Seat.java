package se.lundakarnevalen.ticket.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Seat extends Entity {
	@Column(name = "id")
	public final int id;
	@Column(name = "performance_id")
	private int performance_id;
	@Column(name = "category_id")
	private int category_id;
	@Column(name = "active_ticket_id")
	private int active_ticket_id;
	@Column(name = "profile_id")
	private int profile_id;

	private static final String TABLE = "`seats`";
	private static final String COLS = Entity.getCols(Seat.class);

	private Seat(int id) throws SQLException {
		this.id = id;
	}

	private static Seat create(ResultSet rs) throws SQLException {
		Seat perf = new Seat(rs.getInt("id"));
		perf.performance_id = rs.getInt("performance_id");
		perf.category_id = rs.getInt("category_id");
		perf.active_ticket_id = rs.getInt("active_ticket_id");
		perf.profile_id = rs.getInt("profile_id");
		return perf;
	}

	public static List<Seat> getByPerformance(int performance_id) throws SQLException {
		String query = "SELECT " + COLS + " FROM " + TABLE + " WHERE `performance_id`=?";
		PreparedStatement stmt = prepare(query);
		stmt.setInt(1, performance_id);
		return new Mapper<Seat>(stmt).toEntityList(rs -> Seat.create(rs));
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("performance_id", performance_id);
		json.put("category_id", category_id);
		json.put("active_ticket_id", active_ticket_id);
		json.put("profile_id", profile_id);
		return json;
	}

	public static Seat getSingle(long id) throws SQLException {
		String query = "SELECT " + COLS + " FROM " + TABLE + " WHERE `id`=?";
		PreparedStatement stmt = prepare(query);
		stmt.setLong(1, id);
		return new Mapper<Seat>(stmt).toEntity(rs -> Seat.create(rs));
	}

	public static Seat create(int performance_id, int category_id) throws SQLException, JSONException {
		String query = "INSERT INTO " + TABLE + " SET `performance_id`=?, `category_id`=?";
		System.out.println(query + " : " + performance_id + " : " + category_id);
		PreparedStatement stmt = prepare(query);
		stmt.setInt(1, performance_id);
		stmt.setInt(2, category_id);
		int id = executeInsert(stmt);
		return getSingle(id);
	}
}
