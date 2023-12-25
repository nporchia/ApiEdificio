create table if not exists edificios
(
    id        int auto_increment
        primary key,
    direccion varchar(255) null,
    nombre    varchar(255) null
);

create table if not exists areas_comunes
(
    id          int auto_increment
        primary key,
    nombre      varchar(255) null,
    edificio_id int          null,
    constraint FKhivdwdejgyjj02v1xpwf1ba4c
        foreign key (edificio_id) references edificios (id)
);

create table if not exists unidades
(
    id          int auto_increment
        primary key,
    estado      enum ('ALQUILADA', 'NOALQUILADA') null,
    numero      varchar(255)                      null,
    piso        int                               not null,
    edificio_id int                               null,
    constraint UK54biwylbpnf2sf4lh0qi5pwvx
        unique (edificio_id, numero),
    constraint FK2c6u69rdsrd4tgr3dejdod5yi
        foreign key (edificio_id) references edificios (id)
);

create table if not exists usuarios
(
    id       int auto_increment
        primary key,
    nombre   varchar(255)                                                      null,
    password varchar(255)                                                      null,  
    email      varchar(255)                                                    null,
    rol      enum ('ROLE_ADMINISTRADOR', 'ROLE_INQUILINO', 'ROLE_PROPIETARIO') null,
    usuario  varchar(255)                                                      null,
    constraint UK_io49vjba68pmbgpy9vtw8vm81
        unique (nombre)
);

create table if not exists reclamos
(
    id            int auto_increment
        primary key,
    descripcion   varchar(255)                                                                   null,
    estado        enum ('ABIERTO', 'ANULADO', 'DESESTIMADO', 'EN_PROCESO', 'NUEVO', 'TERMINADO') null,
    resolucion    varchar(255)                                                                   null,
    area_comun_id int                                                                            null,
    edificio_id   int                                                                            null,
    unidad_id     int                                                                            null,
    usuario_id    int                                                                            null,
    constraint FK4vh6amjxo01db7o5rxhnwp4n9
        foreign key (area_comun_id) references areas_comunes (id),
    constraint FK6h6l3b42erahmfwrdeqdjko4r
        foreign key (edificio_id) references edificios (id),
    constraint FKipke2b5h454p1aifjy24ic6kf
        foreign key (usuario_id) references usuarios (id),
    constraint FKox1b4fnj96svdfhkifawijfq9
        foreign key (unidad_id) references unidades (id)
);

create table if not exists imagen
(
    id           bigint auto_increment
        primary key,
    datos_imagen longblob null,
    reclamo_id   int      null,
    constraint FKfxflx69jqhluqvpw2ohse147o
        foreign key (reclamo_id) references reclamos (id)
);

create table if not exists unidad_usuario
(
    unidad_fk_id  int not null,
    usuario_fk_id int not null,
    constraint FK5wubl1r0lmw1c7p3hjbollhor
        foreign key (unidad_fk_id) references unidades (id),
    constraint FKsxok2sdwvw9sfdix0f508l3ik
        foreign key (usuario_fk_id) references usuarios (id)
);