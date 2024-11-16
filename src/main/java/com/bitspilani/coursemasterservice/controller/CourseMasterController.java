package com.bitspilani.coursemasterservice.controller;

import com.bitspilani.coursemasterservice.dto.CourseMasterDTO;
import com.bitspilani.coursemasterservice.dto.ErrorResponse;
import com.bitspilani.coursemasterservice.service.Coursemasterservice;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/course")
public class CourseMasterController {

    private final Coursemasterservice coursemasterservice;

    public CourseMasterController(Coursemasterservice coursemasterservice) {
        this.coursemasterservice = coursemasterservice;
    }

    @PostMapping
    public ResponseEntity<CourseMasterDTO> addUpdateCourse(
        @RequestBody CourseMasterDTO courseMasterDTO) {
        return ResponseEntity.ok(coursemasterservice.addUpdateCourse(courseMasterDTO));
    }

    @DeleteMapping("removeCourse/{courseId}")
    public ResponseEntity<Void> removeCourse(@PathVariable("courseId") Long courseId) {
        coursemasterservice.removeCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/checkIfCourseExists/{courseId}")
    public ResponseEntity<Boolean> checkIfCourseExists(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(coursemasterservice.isCourseAvailable(courseId));
    }

    @GetMapping("/getCourseContentByCourseId/{courseId}")
    public ResponseEntity<CourseMasterDTO> getCourseContentByCourseId(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(coursemasterservice.getCourse(courseId));
    }
    
    @GetMapping("/getAllCourses")
    public ResponseEntity<List<CourseMasterDTO>> getAllCourses() {
        return ResponseEntity.ok(coursemasterservice.getAllCourses());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Course does not exist", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
