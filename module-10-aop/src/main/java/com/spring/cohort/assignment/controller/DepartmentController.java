package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.dto.DepartmentDTO;
import com.spring.cohort.assignment.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id){
        return ResponseEntity.ok().body(departmentService.getDepartment(id));
    }
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getDepartmentList(){
        return ResponseEntity.ok().body(departmentService.getDepartmentList());
    }

    @PutMapping
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentUpdateRequest) throws Exception {
        return ResponseEntity.ok().body(departmentService.updateDepartment(departmentUpdateRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable Long id){
        return ResponseEntity.ok().body(departmentService.deleteDepartment(id));
    }

}
