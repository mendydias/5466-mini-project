/*
    Author: N. W. L. U. R. D. Nanayakkara
    Reg no: 321424374
 */

-- Register an employee and add said employee to a department
BEGIN TRANSACTION;

INSERT INTO main.employees(name, role, dept_id)
VALUES ('Ranmal Dias', 'Mediator', 4);

UPDATE main.dept_hods
SET current_hod = 0
WHERE hod = (SELECT TOP(1) hod FROM main.dept_hods WHERE dept_id = 4 ORDER BY date_appointed DESC);

INSERT INTO main.dept_hods(hod, dept_id, date_appointed, current_hod)
VALUES (16, 4, GETDATE(), 1);

SELECT * FROM main.dept_hods;

COMMIT;

-- Deleting an employee and all their projects
BEGIN TRANSACTION;

DELETE FROM main.employees WHERE emp_id = 4;
SELECT * FROM main.employees;

COMMIT;