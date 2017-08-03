package se.lundakarnevalen.ticket.db.framework;

import java.sql.SQLException;

/**
 * Allows the creation of an Entity from a ResultSet to throw an SQLException.
 * 
 * @author Kalle Elmér
 */

@FunctionalInterface
public interface ResultSetFunction<T, R> {
	R apply(T t) throws SQLException;
}
