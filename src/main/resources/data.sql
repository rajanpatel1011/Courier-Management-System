-- Seed data migrated from legacy MySQL dump (V1__init.sql)
INSERT IGNORE INTO tbl_offices (id, off_name, address, city, ph_no, office_time, contact_person) VALUES
(1, 'Fast Courier - Jalgaon', '290, shani peth, jalgaon', 'Jalgaon', '0257-25125', '10.00 am - 9.00 pm', 'Shammi Kapur'),
(2, 'Fast Courier - Aurangabad', '20/12, sector 12, bhavani peth', 'Aurangabad', '0245-858521', '10.00 am - 9.00 pm', 'Amol Patil'),
(3, 'Fast Courier - Pune', '230, Fashion Street', 'pune', '020-25125', '10.00 am - 9.00 pm', 'Atul Nigade');

INSERT IGNORE INTO tbl_courier_officers (cid, officer_name, off_pwd, address, email, ph_no, office, reg_date) VALUES
(1, 'kapil', 'kapil', 'asif nagar , hyderabad', 'kapil@gmail.com', '9890989989', 'Fast Courier - Jalgaon', '2011-01-30 09:25:21'),
(2, 'Ashraf Sk.', 'ashraf', '11, bhaguday nagar', 'ashraf@gmail.com', '9854254125', 'Fast Courier - Aurangabad', '2011-01-30 09:40:42'),
(3, 'sunil', 'sunil', '390, sani peth', 'sunil@gmail.com', '9890989989', 'Fast Courier - Pune', '2011-01-30 17:50:34');

INSERT IGNORE INTO tbl_courier (cid, cons_no, ship_name, phone, s_add, rev_name, r_phone, r_add, type, weight, invice_no, qty, book_mode, freight, mode, pick_date, pick_time, status, comments, book_date) VALUES
(1, 'M22P7KHM', 'Tousif Khan', '020 253623', 's sd sdsd', 'Rizwan Ahmed', '020 88552', 'sd sd s', 'Parcel', 20, '252525', 12, 'TBB', 240, 'Road', '29/01/2011', '12', 'Completed', 'ds ds d', '2011-01-29'),
(2, 'QIWWGIQP', 'Asif khan', '020 253623', 'shani peth', 'munna bhai', '020 88552', 'asdas das d', 'Documents', 20, '252525', 12, 'TBB', 240, 'Train', '29/01/2011', '4', 'Delivered', 'Plz deliver it', '2011-01-29'),
(3, 'Q906F73L', 'Amol sarode', '9532653652', 'metha nagar, bhusawal', 'sunil pal', '8585425412', 'balliram peth', 'Documents', 12, '239098', 12, 'ToPay', 200, 'Air', '26/01/2013', '4', 'In Transit', 'Thanks', '2011-01-29'),
(4, '2THBV8UM', 'Farzana Sk', '9532652365', 'xzyz', 'Asif Khan', '9852451254', 'ABC', 'Parcel', 2, '23788', 4, 'Paid', 90, 'Road', '20/01/2011', '12', 'Delivered', 'Plz transit', '2011-01-30');

INSERT IGNORE INTO tbl_courier_track (id, cid, cons_no, current_city, status, comments, bk_time) VALUES
(1, 1, 'M22P7KHM', 'Fast Courier - Jalgaon', 'Delayed', 'Delay due to rain', '2011-01-30 10:23:04'),
(3, 1, 'M22P7KHM', 'Fast Courier - Jalgaon', 'Delayed', 'Delayed due to rain', '2011-01-30 10:26:43'),
(4, 4, '2THBV8UM', 'Fast Courier - Aurangabad', 'Delayed', 'Due to rain', '2011-01-30 17:44:52'),
(5, 1, 'M22P7KHM', 'Fast Courier - Jalgaon', 'Completed', 'Completed', '2011-01-30 17:49:11');
