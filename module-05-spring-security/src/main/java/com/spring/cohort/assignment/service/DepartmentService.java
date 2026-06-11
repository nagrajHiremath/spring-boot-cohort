package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.DepartmentDTO;
import com.spring.cohort.assignment.repository.DepartmentRepository;
import com.spring.cohort.assignment.entity.DepartmentEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public List<DepartmentDTO> getDepartmentList() {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();

        return departmentEntityList
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .toList();
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentRequest) {
        DepartmentEntity departmentEntity = modelMapper.map(departmentRequest, DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public DepartmentDTO getDepartment(Long id) {
        isDepartmentExist(id);
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(id);

        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentUpdateRequest) throws Exception {
       isDepartmentExist(departmentUpdateRequest.getId());
       DepartmentEntity departmentEntity = departmentRepository.findById(departmentUpdateRequest.getId()).get();

       modelMapper.map(departmentUpdateRequest, departmentEntity);

       return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public DepartmentDTO deleteDepartment(Long id) {
        isDepartmentExist(id);
        DepartmentEntity departmentEntity = departmentRepository.findById(id).get();
        departmentRepository.deleteById(id);
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public void isDepartmentExist(Long id){
        boolean isExist = departmentRepository.existsById(id);
        if(!isExist) throw  new EntityNotFoundException("Department not exist "+id);
    }
}
