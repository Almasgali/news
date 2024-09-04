insert into "user" (id, name, surname, email, password) values
    (1, 'a', 'a', 'a@mail.com', 'aaaaaaaa'),
    (2, 'b', 'b', 'b@mail.com', 'bbbbbbbb'),
    (3, 'c', 'c', 'c@mail.com', 'cccccccc') on conflict do nothing;

insert into "article" (id, title, text, date) values
    (1, 'A', 'abacaba', now()),
    (2, 'B', 'bbacaba', '2024-08-18 00:02:00'),
    (3, 'C', 'cbacaba', now()),
    (4, 'D', 'dbacaba', now()),
    (5, 'E', 'ebacaba', '2024-08-17 00:05:00'),
    (6, 'F', 'fbacaba', now()) on conflict do nothing;

insert into "comment" (id, text, user_id, article_id, date) values
    (1, 'A', 1, 1, '2024-08-19 00:01:00'),
    (2, 'A', 1, 1, '2024-08-19 00:02:00'),
    (3, 'A', 2, 1, '2024-08-19 00:01:00'),
    (4, 'A', 3, 1, '2024-08-19 00:01:00'),
    (5, 'A', 2, 2, '2024-08-19 00:03:00'),
    (6, 'A', 3, 2, '2024-08-19 00:04:00') on conflict do nothing;

insert into "article_like" (user_id, article_id) values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 5),
    (3, 6) on conflict do nothing;

DROP SEQUENCE IF EXISTS user_seq;
CREATE SEQUENCE user_seq START 4;
ALTER TABLE "user" ALTER COLUMN id SET DEFAULT nextval('user_seq');

DROP SEQUENCE IF EXISTS article_seq;
CREATE SEQUENCE article_seq START 7;
ALTER TABLE "article" ALTER COLUMN id SET DEFAULT nextval('article_seq');

DROP SEQUENCE IF EXISTS comment_seq;
CREATE SEQUENCE comment_seq START 7;
ALTER TABLE "comment" ALTER COLUMN id SET DEFAULT nextval('comment_seq');
