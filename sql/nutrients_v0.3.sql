--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: rawdata; Type: SCHEMA; Schema: -; Owner: nutrients
--

CREATE SCHEMA rawdata;


ALTER SCHEMA rawdata OWNER TO nutrients;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = rawdata, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: food_items; Type: TABLE; Schema: rawdata; Owner: nutrients
--

CREATE TABLE food_items (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    nutrients_csv text
);


ALTER TABLE food_items OWNER TO nutrients;

--
-- Name: food_items_id_seq; Type: SEQUENCE; Schema: rawdata; Owner: nutrients
--

CREATE SEQUENCE food_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE food_items_id_seq OWNER TO nutrients;

--
-- Name: food_items_id_seq; Type: SEQUENCE OWNED BY; Schema: rawdata; Owner: nutrients
--

ALTER SEQUENCE food_items_id_seq OWNED BY food_items.id;


--
-- Name: food_items id; Type: DEFAULT; Schema: rawdata; Owner: nutrients
--

ALTER TABLE ONLY food_items ALTER COLUMN id SET DEFAULT nextval('food_items_id_seq'::regclass);


--
-- Data for Name: food_items; Type: TABLE DATA; Schema: rawdata; Owner: nutrients
--

COPY food_items (id, name, nutrients_csv) FROM stdin;
\.


--
-- Name: food_items_id_seq; Type: SEQUENCE SET; Schema: rawdata; Owner: nutrients
--

SELECT pg_catalog.setval('food_items_id_seq', 12, true);


--
-- Name: food_items constraint_name_uniq; Type: CONSTRAINT; Schema: rawdata; Owner: nutrients
--

ALTER TABLE ONLY food_items
    ADD CONSTRAINT constraint_name_uniq UNIQUE (name);


--
-- Name: food_items food_items_pkey; Type: CONSTRAINT; Schema: rawdata; Owner: nutrients
--

ALTER TABLE ONLY food_items
    ADD CONSTRAINT food_items_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

