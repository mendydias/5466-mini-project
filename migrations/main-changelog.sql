--liquibase formatted sql

--changeset mendydias:1 label:employees
create table main.employees(
    emp_id bigint identity(1,1) not null,
    name varchar(255) not null,
    role varchar(255) not null,
    constraint PK_emp_id primary key clustered (emp_id)
);
GO
--rollback drop table main.employees

--changeset mendydias:2 label:addresses
create table main.countries(
    country_id bigint not null,
    code varchar(5) not null,
    name varchar(255) not null,
    phone int,
    continent varchar(150) not null,
    currency varchar(20),
    constraint PK_country_id primary key clustered (country_id)
);
GO
--rollback drop table main.countries

create table main.provinces(
    province_id bigint not null,
    name_en varchar(255),
    name_si varchar(255),
    name_ta varchar(255),
    country_id bigint not null,
    constraint PK_province_id primary key clustered (province_id)
);
GO
--rollback drop table main.provinces

alter table main.provinces 
add constraint FK_province_country
foreign key (country_id) references main.countries(country_id) 
on delete cascade on update cascade;
GO
--rollback alter table main.provinces drop constraint FK_province_country

create nonclustered index IX_province_country on main.provinces (country_id);
GO
--rollback drop index IX_province_country on main.provinces

create table main.districts(
    district_id bigint not null,
    name_en varchar(255),
    name_si varchar(255),
    name_ta varchar(255),
    province_id bigint not null,
    constraint PK_district_id primary key clustered (district_id)
);
GO
--rollback drop table main.districts

alter table main.districts
add constraint FK_district_province
foreign key (province_id) references main.provinces(province_id)
on delete cascade on update cascade;
GO
--rollback alter table main.districts drop constraint FK_district_province

create nonclustered index IX_district_province on main.districts(province_id);
GO
--rollback drop index IX_district_province on main.districts

create table main.cities(
    city_id bigint not null,
    name_en varchar(255),
    name_si varchar(255),
    name_ta varchar(255),
    subname_en varchar(255),
    subname_si varchar(255),
    subname_ta varchar(255),
    postcode varchar(50),
    latitude varchar(150),
    longitude varchar(150),
    district_id bigint not null,
    constraint PK_city_district primary key clustered (city_id)
);
GO
--rollback drop table main.cities 

alter table main.cities
add constraint FK_city_district
foreign key (district_id) references main.districts(district_id)
on delete cascade on update cascade;
GO
--rollback alter table main.cities drop constratin FK_city_district

create nonclustered index IX_city_district on main.cities (district_id);
GO
--rollback drop index IX_city_district on main.cities

create table main.addresses(
    address_id bigint identity(1,1) not null,
    house_num varchar(50) not null,
    street varchar(255) not null,
    city_id bigint not null,
    constraint PK_address_id primary key clustered (address_id)
);
GO
--rollback drop table main.addresses

alter table main.addresses
add constraint FK_city_address
foreign key (city_id) references main.cities(city_id)
on delete cascade on update cascade; 
GO
--rollback alter table main.addresses drop constraint FK_city_address

create nonclustered index IX_address_city on main.addresses (city_id);
GO
--rollback drop index IX_address_city on main.addresses 

--changeset mendydias:3 label:connect_addresses_to_employees
create table main.emp_addresses(
    emp_id bigint not null,
    address_id bigint not null,
    date datetime default getdate(),
    constraint PK_emp_address primary key clustered (emp_id, address_id)
);
GO
--rollback drop table emp_addresses

alter table main.emp_addresses
add constraint FK_employee_addresses
foreign key (emp_id) references main.employees (emp_id)
on delete cascade on update cascade;
GO
--rollback alter table main.emp_addresses drop constraint FK_employee_addresses

alter table main.emp_addresses
add constraint FK_addresses_employee
foreign key (address_id) references main.addresses (address_id)
on delete cascade on update cascade; 
GO
--rollback alter table main.emp_addresses drop constraint FK_addresses_employee

--changeset mendydias:4 label:telephone
create table main.telephone(
    tel_id bigint identity(1,1) not null,
    number bigint,
    landline bit default 0,
    whatsapp bit default 0,
    country_id bigint not null,
    emp_id bigint not null,
    constraint PK_telephone primary key clustered (tel_id)
);
GO
--rollback drop table main.telephone

alter table main.telephone
add constraint FK_tel_country
foreign key (country_id) references main.countries (country_id)
on delete cascade on update cascade;
GO
--rollback alter table main.telephone drop constraint FK_tel_country

alter table main.telephone
add constraint FK_tel_employee
foreign key (emp_id) references main.employees (emp_id)
on delete cascade on update cascade;
GO
--rollback alter table main.telephone drop constraint FK_tel_employee 

create nonclustered index IX_tel_country on main.telephone (country_id);
GO
--rollback drop index IX_tel_country on main.telephone

create nonclustered index IX_tel_employee on main.telephone (emp_id);
GO
--rollback drop index IX_tel_employee on main.telephone

--changeset mendydias:5 label:email
create table main.emails(
    email_id bigint identity(1,1) not null,
    email varchar(150) not null,
    date_added datetime not null default getdate(),
    emp_id bigint not null,
    constraint PK_emails primary key clustered (email_id)
);
GO
--rollback drop table main.emails

alter table main.emails
add constraint FK_emails_employee
foreign key (emp_id) references main.employees (emp_id)
on delete cascade on update cascade;
GO
--rollback alter table main.emails drop constraint FK_emails_employee

create nonclustered index IX_emails_emp on main.emails (emp_id);
GO
--rollback drop index IX_emails_emp on main.emails

--changeset mendydias:6 label:departments
create table main.departments(
    dept_id bigint identity(1,1) not null,
    name varchar(255) not null,
    constraint PK_departments primary key clustered (dept_id)
);
GO
--rollback drop table main.departments

create table main.dept_hods(
    hod bigint not null,
    dept_id bigint not null,
    date_appointed datetime not null,
    constraint PK_dept_hod primary key clustered (hod, dept_id)
);
GO
--rollback drop table main.dept_hods

alter table main.dept_hods
add constraint FK_dept_employee
foreign key (hod) references main.employees (emp_id)
on delete cascade on update cascade; 
GO
--rollback alter table main.dept_hods drop constraint FK_dept_employee

alter table main.dept_hods
add constraint FK_hod_dept
foreign key (dept_id) references main.departments (dept_id)
on delete cascade on update cascade; 
GO
--rollback alter table main.dept_hods drop constraint FK_hod_dept

alter table main.employees add dept_id bigint;
GO
--rollback alter table main.employee drop column dept_id
alter table main.employees 
add constraint FK_emp_dept 
foreign key (dept_id) references main.departments (dept_id)
GO
--rollback alter table main.employees drop constraint FK_emp_dept

create nonclustered index IX_emp_dept on main.employees (dept_id);
--rollback drop index IX_emp_dept on main.employees

--changeset mendydias:7 label:project
create table main.projects(
    proj_id bigint identity(1,1) not null,
    name varchar(255) not null,
    constraint PK_projects primary key clustered (proj_id)
);
GO
--rollback drop table main.projects

create table main.project_assignments(
    proj_id bigint not null,
    dept_id bigint not null,
    date_assigned datetime not null default getdate(),
    constraint PK_proj_assign primary key clustered (proj_id, dept_id)
);
GO
--rollback drop table main.project_assignments

alter table main.project_assignments
add constraint FK_projects
foreign key (proj_id) references main.projects (proj_id)
on delete cascade on update cascade;
GO
--rollback alter table drop constraint FK_projects

alter table main.project_assignments
add constraint FK_departments
foreign key (dept_id) references main.departments (dept_id)
on delete cascade on update cascade;
GO
--rollback alter table drop constraint FK_departments

--changeset mendydias:8 label:current_column
alter table main.dept_hods
add "current_hod" bit default 1;
--rollback alter table drop column "current"
