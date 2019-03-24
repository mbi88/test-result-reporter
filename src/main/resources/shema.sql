CREATE SEQUENCE users_id_seq START 1;

CREATE TABLE public.users
(
    id int DEFAULT NEXTVAL('users_id_seq') PRIMARY KEY NOT NULL,
    name text NOT NULL,
    email text NOT NULL,
    data json,
    created_at timestamp,
    updated_at timestamp
);

CREATE UNIQUE INDEX users_id_uindex ON public.users (id);
