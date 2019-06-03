START TRANSACTION;
DROP TABLE IF EXISTS
    public."Genre",
    public."School",
    public."ArtDirection",
    public."Museum",
    public."Exhibitions",
    public."Author",
    public."Picture",
    public."PictureAuthor",
    public."PictureExhibition",
    public."SchoolAuthor"
CASCADE;

CREATE TABLE public."Genre"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT genre_pk PRIMARY KEY (id),
    CONSTRAINT "Genre_name_key" UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Genre"
    OWNER to postgres;

CREATE TABLE public."School"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT school_pk PRIMARY KEY (id),
    CONSTRAINT "School_name_key" UNIQUE (name)

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."School"
    OWNER to postgres;

CREATE TABLE public."ArtDirection"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    "from" integer,
    "to" integer,
	CONSTRAINT ArtDirection_pk PRIMARY KEY (id),
    CONSTRAINT "ArtDirection_name_key" UNIQUE (name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."ArtDirection"
    OWNER to postgres;

CREATE TABLE public."Museum"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    city text COLLATE pg_catalog."default" NOT NULL,
    country text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default",
    "date of foundation" integer,
    CONSTRAINT museum_pk PRIMARY KEY (id),
    CONSTRAINT museum_name UNIQUE (name)

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Museum"
    OWNER to postgres;

CREATE TABLE public."Exhibitions"
(
    id serial NOT NULL,
    museum integer NOT NULL,
    "from" date,
    "to" date,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT exhibitions_pk PRIMARY KEY (id),
    CONSTRAINT "Exhibitions_fk0" FOREIGN KEY (museum)
        REFERENCES public."Museum" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Exhibitions"
    OWNER to postgres;

CREATE TABLE public."Author"
(
    id serial NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    "Date of Birth" date,
    "Date of death" date,
    note text COLLATE pg_catalog."default",
    "place of Birth" text COLLATE pg_catalog."default",
    "place of death" text COLLATE pg_catalog."default",
    CONSTRAINT author_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Author"
    OWNER to postgres;

CREATE TABLE public."Picture"
(
    id serial NOT NULL,
    name text NOT NULL,
    author integer NOT NULL,
	genre integer,
	"art direction" integer,
    "year of painting" integer,
    height integer,
    width integer,
    material text COLLATE pg_catalog."default",
    museum integer NOT NULL,
    pathToImage text,
    CONSTRAINT picture_pk PRIMARY KEY (id),
	CONSTRAINT "Picture_fk0" FOREIGN KEY (author)
        REFERENCES public."Author" (id) MATCH SIMPLE,
    CONSTRAINT "Picture_fk1" FOREIGN KEY (museum)
        REFERENCES public."Museum" (id) MATCH SIMPLE,
	CONSTRAINT "Picture_fk2" FOREIGN KEY (genre)
        REFERENCES public."Genre" (id) MATCH SIMPLE,
	CONSTRAINT "Picture_fk3" FOREIGN KEY ("art direction")
        REFERENCES public."ArtDirection" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Picture"
    OWNER to postgres;

CREATE TABLE public."PictureAuthor"
(
    id serial NOT NULL,
	author integer NOT NULL,
    picture integer NOT NULL,
    CONSTRAINT PictureAuthor_pk PRIMARY KEY (id),
	CONSTRAINT "PictureAuthor_fk0" FOREIGN KEY (author)
        REFERENCES public."Author" (id) MATCH SIMPLE,
	CONSTRAINT "PictureAuthor_fk1" FOREIGN KEY (picture)
        REFERENCES public."Picture" (id) MATCH SIMPLE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."PictureAuthor"
    OWNER to postgres;

CREATE TABLE public."PictureExhibition"
(
    id serial NOT NULL,
	exhibition integer NOT NULL,
    picture integer NOT NULL,
    CONSTRAINT PictureExhibition_pk PRIMARY KEY (id),
	CONSTRAINT "PictureExhibition_fk0" FOREIGN KEY (exhibition)
        REFERENCES public."Exhibitions" (id) MATCH SIMPLE,
	CONSTRAINT "PictureExhibition_fk1" FOREIGN KEY (picture)
        REFERENCES public."Picture" (id) MATCH SIMPLE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."PictureExhibition"
    OWNER to postgres;

CREATE TABLE public."SchoolAuthor"
(
    id serial NOT NULL,
	author integer NOT NULL,
    school integer NOT NULL,
    CONSTRAINT SchoolAuthor_pk PRIMARY KEY (id),
	CONSTRAINT "SchoolAuthor_fk0" FOREIGN KEY (author)
        REFERENCES public."Author" (id) MATCH SIMPLE,
	CONSTRAINT "SchoolAuthor_fk1" FOREIGN KEY (school)
        REFERENCES public."School" (id) MATCH SIMPLE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."SchoolAuthor"
    OWNER to postgres;

    DROP TABLE IF EXISTS
        public."PictureMuseum",
        public."Theft",
        public."Auction",
    CASCADE;

    CREATE TABLE public."PictureMuseum"
    (
        id serial NOT NULL,
        picture integer NOT NULL,
    	museum integer NOT NULL,
    	"from" date,
    	"to" date,
        CONSTRAINT PictureMuseum_pk PRIMARY KEY (id),
    	CONSTRAINT "PictureMuseum_fk0" FOREIGN KEY (picture)
            REFERENCES public."Picture" (id) MATCH SIMPLE,
        CONSTRAINT "PictureMuseum_fk1" FOREIGN KEY (museum)
            REFERENCES public."Museum" (id) MATCH SIMPLE
    )
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

    ALTER TABLE public."PictureMuseum"
        OWNER to postgres;

    INSERT INTO public."PictureMuseum" (picture, museum, "from", "to")
           SELECT id, museum, NULL, NULL FROM public."Picture";
    ALTER TABLE public."Picture" DROP COLUMN museum;

    CREATE TABLE public."Theft"
    (
        id serial NOT NULL,
        picture integer NOT NULL,
    	"from" date,
    	"to" date,
        CONSTRAINT Theft_pk PRIMARY KEY (id),
    	CONSTRAINT "Theft_fk0" FOREIGN KEY (picture)
            REFERENCES public."Picture" (id) MATCH SIMPLE
    )
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

    ALTER TABLE public."Theft"
        OWNER to postgres;


    CREATE TABLE public."Auction"
    (
        id serial NOT NULL,
        name text,
        picture integer NOT NULL,
    	seller integer NOT NULL,
    	buyer integer NOT NULL,
    	cost bigint,
    	"date" date NOT NULL,
        CONSTRAINT Auction_pk PRIMARY KEY (id),
    	CONSTRAINT "Auction_fk0" FOREIGN KEY (picture)
            REFERENCES public."Picture" (id) MATCH SIMPLE,
    	CONSTRAINT "Auction_fk1" FOREIGN KEY (seller)
            REFERENCES public."Museum" (id) MATCH SIMPLE,
    	CONSTRAINT "Auction_fk2" FOREIGN KEY (buyer)
            REFERENCES public."Museum" (id) MATCH SIMPLE
    )
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

    ALTER TABLE public."Auction"
        OWNER to postgres;

COMMIT;