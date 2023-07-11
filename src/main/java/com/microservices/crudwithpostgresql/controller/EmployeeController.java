package com.microservices.crudwithpostgresql.controller;

import com.microservices.crudwithpostgresql.employee.Employee;
import com.microservices.crudwithpostgresql.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/get-all-workers")
    public ResponseEntity<List<Employee>> getAllWorkers(){
        try {
            List<Employee> workersList = new ArrayList<>();
            employeeRepository.findAll().forEach(workersList::add);
            if (workersList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(workersList,HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Employee> getWorkerId(@PathVariable Long id){
            Optional<Employee> workerData = employeeRepository.findById(id);
            return new ResponseEntity<Employee>(workerData.get(), HttpStatus.OK);
    }

    @PostMapping("add-worker")
    public ResponseEntity<Employee> addWorker(@RequestBody Employee worker){
        Employee workerObj = employeeRepository.save(worker);
        return new ResponseEntity<>(workerObj,HttpStatus.OK);
    }

    @PostMapping("/update-worker-by-id/{id}")
    public ResponseEntity<Employee> updateWorker(@PathVariable Long id , @RequestBody Employee newWorkerData){
        Optional<Employee> oldWorkerData = employeeRepository.findById(id);

        if (oldWorkerData.isPresent()){
            Employee updatedWorkerData = oldWorkerData.get();
            updatedWorkerData.setName(newWorkerData.getName());
            updatedWorkerData.setLastName(newWorkerData.getLastName());
            updatedWorkerData.setRole(newWorkerData.getRole());
            updatedWorkerData.setSalary(newWorkerData.getSalary());
            Employee workersObj = employeeRepository.save(updatedWorkerData);
            return new ResponseEntity<>(workersObj,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<HttpStatus> deleteWorkerById(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
