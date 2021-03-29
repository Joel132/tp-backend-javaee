CREATE TABLE public.cliente
(
    id_cliente integer NOT NULL,
    nombre character varying(50) NOT NULL,
    apellido character varying(50) NOT NULL,
	nro_documento integer NOT NULL,
	tipo_documento character varying(10) NOT NULL,
	nacionalidad character varying(30) NOT NULL,
	email character varying(50) NOT NULL,
	telefono character varying(30),
	fecha_nacimiento date NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente)
);
CREATE SEQUENCE public.cliente_sec;
CREATE TABLE public.concepto_puntos
(
    id_concepto integer NOT NULL,
    descripcion character varying(50) NOT NULL,
    puntos_requerido integer NOT NULL,
    CONSTRAINT concepto_pkey PRIMARY KEY (id_concepto)
);
CREATE SEQUENCE public.concepto_sec;
CREATE TABLE public.regla_puntos
(
    id_regla integer NOT NULL,
    limite_inferior integer,
	limite_superior integer,
    equivalencia integer NOT NULL,
    CONSTRAINT regla_pkey PRIMARY KEY (id_regla)
);
CREATE SEQUENCE public.regla_sec;
CREATE TABLE public.vigencia_puntos
(
    id_vigencia integer NOT NULL,
    fecha_inicio date NOT NULL,
	fecha_fin date NOT NULL,
    duracion_dias integer NOT NULL,
    CONSTRAINT vigencia_pkey PRIMARY KEY (id_vigencia)
);
CREATE SEQUENCE public.vigencia_sec;
CREATE TABLE public.bolsa_puntos
(
    id_bolsa integer NOT NULL,
    fecha_asignacion date NOT NULL,
	fecha_vencimiento date NOT NULL,
    id_cliente integer NOT NULL,
    puntaje_asignado integer NOT NULL,
	puntaje_utilizado integer NOT NULL,
	saldo integer NOT NULL,
	monto integer NOT NULL,
    CONSTRAINT bolsa_pkey PRIMARY KEY (id_bolsa),
    CONSTRAINT fk_1 FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.bolsa_sec;
CREATE TABLE public.uso_puntos
(
    id_uso integer NOT NULL,
    fecha date NOT NULL,
    id_cliente integer NOT NULL,
    puntaje_utlizado integer NOT NULL,
	id_concepto integer NOT NULL,
    CONSTRAINT uso_pkey PRIMARY KEY (id_uso),
    CONSTRAINT fk_1 FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT fk_2 FOREIGN KEY (id_concepto)
        REFERENCES public.concepto_puntos (id_concepto) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.uso_sec;
CREATE TABLE public.detalle_uso_puntos
(
    id_detalle integer NOT NULL,
    id_uso integer NOT NULL,
    puntaje_utlizado integer NOT NULL,
	id_bolsa integer NOT NULL,
    CONSTRAINT detalle_pkey PRIMARY KEY (id_detalle),
    CONSTRAINT fk_1 FOREIGN KEY (id_uso)
        REFERENCES public.uso_puntos (id_uso) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT fk_2 FOREIGN KEY (id_bolsa)
        REFERENCES public.bolsa_puntos (id_bolsa) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE SEQUENCE public.detalle_sec;