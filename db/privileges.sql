USE `lkticket`;

GRANT SELECT ON `profiles` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`name`), UPDATE(`name`) ON `shows` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`name`), UPDATE(`name`) ON `users` TO 'lkticket'@'localhost';

GRANT SELECT ON `user_profiles` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`token`,`user_id`) ON `user_tokens` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`show_id`, `start`) ON `performances` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`show_id`, `name`, `ticketCount`), UPDATE(`ticketCount`) ON `categories` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`show_id`, `name`) ON `rates` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT, UPDATE(`active_ticket_id`) ON `seats` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`category_id`, `rate_id`, `price`), DELETE ON `prices` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT(`expires`, `identifier`) ON `orders` TO 'lkticket'@'localhost';

GRANT SELECT, INSERT (`order_id`, `seat_id`, `rate_id`, `price`) ON `tickets` TO 'lkticket'@'localhost';
