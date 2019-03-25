CREATE SEQUENCE test_run_id_seq START 1;

CREATE TABLE public.test_runs
(
    id int DEFAULT NEXTVAL('test_run_id_seq') PRIMARY KEY NOT NULL,
    total text NOT NULL,
    passed text NOT NULL,
    failed text NOT NULL,
    skipped text NOT NULL,
    ignored text NOT NULL,
    run_result json,
    created_at timestamp,
    updated_at timestamp
);

CREATE UNIQUE INDEX test_run_id_uindex ON public.users (id);

CREATE SEQUENCE suite_id_seq START 1;

CREATE TABLE public.suites
(
    id int DEFAULT NEXTVAL('suite_id_seq') PRIMARY KEY NOT NULL,
    name text NOT NULL,
    duration text NOT NULL,
    test_run_id text NOT NULL
    created_at timestamp,
    updated_at timestamp
);

CREATE UNIQUE INDEX suite_id_uindex ON public.users (id);