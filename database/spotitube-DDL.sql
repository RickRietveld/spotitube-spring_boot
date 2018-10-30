drop database spotitube;
create database spotitube;
use spotitube;

create table playlist
(
	id int auto_increment
		primary key,
	name varchar(255) not null,
	user varchar(255) not null,
	owner tinyint(1) not null,
	foreign key (user) references user (user)
			on update cascade on delete cascade
)
;

create table token
(
	token varchar(255) not null
		primary key,
	user varchar(255) not null,
	expiryDate date not null,
	constraint fk_user
		foreign key (user) references user (user)
			on update cascade on delete cascade
)
;

create table track
(
	id int(255) not null
		primary key,
	title varchar(255) not null,
	performer varchar(255) not null,
	duration int(255) not null,
	album varchar(255) null,
	playcount int(255) null,
	publicationDate varchar(255) null,
	description varchar(255) null,
	offlineAvailable tinyint(1) not null
)
;

create table trackPlaylistRelation
(
	playlistId int(255) not null,
	trackId int(255) not null,
	offlineAvailable tinyint(1) not null,
	primary key (playlistId, trackId),
	constraint fk_trackPlaylistRelation_playlist
		foreign key (playlistId) references playlist (id)
			on update cascade on delete cascade,
	constraint fk_trackPlaylistRelation_track
		foreign key (trackId) references track (id)
			on update cascade on delete cascade
)
;

create table user
(
	user varchar(255) not null
		primary key,
	password varchar(255) not null
)
;






