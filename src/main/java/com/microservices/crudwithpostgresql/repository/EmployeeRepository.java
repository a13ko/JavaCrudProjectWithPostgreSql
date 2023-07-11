package com.microservices.crudwithpostgresql.repository;

import com.microservices.crudwithpostgresql.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
