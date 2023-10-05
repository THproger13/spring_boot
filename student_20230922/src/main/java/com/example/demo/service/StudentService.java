package com.example.demo.service;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Long save(StudentDTO studentDTO) {
        System.out.println("studentDTO = " + studentDTO);
        // DTO -> Entity 변환을 위한 메서드 호출
        StudentEntity studentEntity = StudentEntity.toSaveEntity(studentDTO);
        Long savedId = studentRepository.save(studentEntity).getId();
        return savedId;
    }

    public List<StudentDTO> findAll() {
        List<StudentEntity> studentEntityList = studentRepository.findAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        /*
            List<StudentEntity> -> List<StudentDTO> 로 옮겨 담는 코드 작성
            Entity -> DTO 변환하는 메서드는 DTO에 정의
         */
//        for (StudentEntity studentEntity: studentEntityList) {
////            StudentDTO studentDTO = StudentDTO.toDTO(studentEntity);
////            studentDTOList.add(studentDTO);
//            studentDTOList.add(StudentDTO.toDTO(studentEntity));
//        }
        studentEntityList.forEach(entity -> {
            studentDTOList.add(StudentDTO.toDTO(entity));
        });
        return studentDTOList;
    }

    public StudentDTO findById(Long id) {
//        Optional<StudentEntity> optionalStudentEntity = studentRepository.findById(id);
//        if (optionalStudentEntity.isPresent()) {
//            // 있다
//            StudentEntity studentEntity = optionalStudentEntity.get();
//            return StudentDTO.toDTO(studentEntity);
//        } else {
//            // 없다
//            return null;
//        }
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return StudentDTO.toDTO(studentEntity);

    }

    public void update(StudentDTO studentDTO) {
        StudentEntity studentEntity = StudentEntity.toUpdateEntity(studentDTO);
        /*
            save() 에 넘기는 엔티티 객체에 pk 값이 들어있으면 update 쿼리가 나가고
            pk 값이 없으면 insert 쿼리가 나감.
         */
        studentRepository.save(studentEntity);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}










