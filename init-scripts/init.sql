\c todo_db;

CREATE TABLE tasks (id integer generated always as identity primary key, name varchar(255) unique not null, description varchar(255), done boolean not null);