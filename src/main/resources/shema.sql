CREATE SEQUENCE test_run_id_seq START 1;
CREATE TABLE public.test_runs
(
    id int DEFAULT NEXTVAL('test_run_id_seq') PRIMARY KEY NOT NULL,
    total text NOT NULL,
    passed text NOT NULL,
    failed text NOT NULL,
    skipped text NOT NULL,
    ignored text NOT NULL,
    created_at timestamp,
    updated_at timestamp
);
CREATE UNIQUE INDEX test_run_id_uindex ON public.test_runs (id);

CREATE SEQUENCE suite_id_seq START 1;
CREATE TABLE public.suites
(
    id int DEFAULT NEXTVAL('suite_id_seq') PRIMARY KEY NOT NULL,
    name text NOT NULL,
    duration text NOT NULL,
    test_run_id int NOT NULL
);
CREATE UNIQUE INDEX suite_id_uindex ON public.suites (id);

CREATE SEQUENCE test_id_seq START 1;
CREATE TABLE public.tests
(
    id int DEFAULT NEXTVAL('test_id_seq') PRIMARY KEY NOT NULL,
    name text NOT NULL,
    duration text NOT NULL,
    suite_id int NOT NULL
);
CREATE UNIQUE INDEX test_id_uindex ON public.tests (id);

CREATE SEQUENCE class_id_seq START 1;
CREATE TABLE public.classes
(
    id int DEFAULT NEXTVAL('class_id_seq') PRIMARY KEY NOT NULL,
    name text NOT NULL,
    test_id int NOT NULL
);
CREATE UNIQUE INDEX class_id_uindex ON public.classes (id);

CREATE SEQUENCE method_id_seq START 1;
CREATE TABLE public.methods
(
    id int DEFAULT NEXTVAL('method_id_seq') PRIMARY KEY NOT NULL,
    name text NOT NULL,
    duration text NOT NULL,
    status text NOT NULL,
    exception text NOT NULL,
    class_id int NOT NULL
);
CREATE UNIQUE INDEX method_id_uindex ON public.methods (id);