DROP TABLE IF EXISTS posts CASCADE;
create table if not exists posts(
  id bigserial primary key,
  caption varchar(256) not null,
  content text not null,
  tags text not null,
  likes integer not null);

DROP TABLE IF EXISTS comments;
create table if not exists comments(
  id bigserial primary key,
  post_id bigserial not null,
  comment_num integer,
  comment text not null,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE);
