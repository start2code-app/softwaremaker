create table SOFTWAREMAKER_QUICK_BOOKS_TOKEN (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    ACCESS_TOKEN varchar(1000) not null,
    REFRESH_TOKEN varchar(1000) not null,
    REALM_ID varchar(255) not null,
    EXPIRE_TS datetime(3) not null,
    REFRESH_TOKEN_EXPIRE_TS datetime(3) not null,
    --
    primary key (ID)
);