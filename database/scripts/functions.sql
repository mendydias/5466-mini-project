/*
 Author: N. W. L. U. R. D. Nanayakkara
 Reg no: 321424374
 */

--Hire a new employee
CREATE PROCEDURE main.register(@name varchar(255), @role varchar(255), @department bigint, @house_num varchar(50),
                               @street varchar(255),
                               @city_id bigint, @email varchar(150), @telephone bigint)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRANSACTION;

    -- save employee details
    INSERT INTO main.employees (name, role, dept_id) VALUES (@name, @role, @department);

    DECLARE @new_id bigint;
    SELECT @new_id = emp_id
    FROM main.employees
    WHERE name = @name;

    -- save address
    INSERT INTO main.addresses(house_num, street, city_id) VALUES (@house_num, @street, @city_id);
    DECLARE @new_address_id bigint;
    SELECT @new_address_id = address_id FROM main.addresses WHERE house_num = @house_num;

    -- link the two addresses
    INSERT INTO main.emp_addresses(emp_id, address_id) VALUES (@new_id, @new_address_id);

    -- save telephone number
    DECLARE @country_id bigint;
    SELECT @country_id = p.country_id
    FROM main.cities ci
             INNER JOIN main.districts d ON ci.district_id = d.district_id
             INNER JOIN main.provinces p on p.province_id = d.province_id
    WHERE ci.city_id = @city_id;
    INSERT INTO main.telephone(number, country_id, emp_id) VALUES (@telephone, @country_id, @new_id);

    -- save email address
    INSERT INTO main.emails (email, date_added, emp_id) VALUES (@email, GETDATE(), @new_id);
    COMMIT;

END;
GO

-- Calculate working period
CREATE FUNCTION main.TENURE(@emp_id bigint)
    RETURNS INT
    WITH EXECUTE AS CALLER
AS
BEGIN
    DECLARE @date DATETIME;
    SELECT @date = date_appointed FROM dept_hods WHERE hod = @emp_id;
    RETURN DATEDIFF(month, @date, GETDATE());
END
GO