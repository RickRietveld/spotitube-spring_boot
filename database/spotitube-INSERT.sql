use spotitube;

insert into `user`
(
`user`, `password`
)
values
(
'rickrietveld', 'password'
);

insert into `track`
(
`id`, `title`, `performer`, `duration`, `album`, `playcount`, `publicationDate`, `description`, `offlineAvailable`
)
values
(
'3', 'Ocean and a rock', 'Lisa Hannigan', '337', 'Sea sew', NULL, NULL, NULL, '0'
),
(
'4', 'So Long, Marianne', 'Leonard Cohen', '546', 'Songs of Leonard Cohen', NULL, NULL, NULL, '0'
),
(
'5', 'One', 'Metallica', '423', NULL, '37', '1-11-2001', 'Long Version', '1'
);