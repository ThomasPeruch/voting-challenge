CREATE TABLE if not exists public.topic (
	id bigserial NOT NULL,
	description varchar(200) NOT NULL,
	CONSTRAINT topic_pkey PRIMARY KEY (id));

CREATE TABLE if not exists public.session_voting(
	id bigserial not null,
	topic_id integer not null,
	voting_start timestamp not null,
	voting_end timestamp not null,
	votes_for_yes numeric DEFAULT 0,
	votes_for_no numeric DEFAULT 0,
	primary key (id),
	foreign key (topic_id) references topic(id));

CREATE TABLE if not exists public.vote(
	id bigserial not null,
	session_id integer not null,
	associate_id integer not null,
	vote_value boolean not null,
	primary key (id),
	foreign key (session_id) references session_voting(id));