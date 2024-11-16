package com.bitspilani.coursemasterservice.service;

import com.bitspilani.coursemasterservice.dto.CourseMasterDTO;
import com.bitspilani.coursemasterservice.model.CourseMaster;
import com.bitspilani.coursemasterservice.repository.CourseMasterRepository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Coursemasterservice {

    public static final String COURSE_REGISTRATION = "/register/course/";
    @Value("${courseregistration.service.url}")
    private String courseRegistrationServiceUrl;

    private final CourseMasterRepository courseMasterRepository;
    private final RestTemplate restTemplate;

    public Coursemasterservice(CourseMasterRepository courseMasternRepository,
        RestTemplate restTemplate) {
        this.courseMasterRepository = courseMasternRepository;
        this.restTemplate = restTemplate;
    }

    public CourseMasterDTO addUpdateCourse(CourseMasterDTO CourseMasterDTO) {
        CourseMaster courseMaster = getCourseMaster(CourseMasterDTO);
        CourseMaster course = this.courseMasterRepository.save(courseMaster);
        return getCourseMasterDTO(course);
    }

    private void checkIfCourseIsRegistered(Long courseId) {
        String getCourseRegistrationEndpoint = StringUtils.join(courseRegistrationServiceUrl, COURSE_REGISTRATION, courseId);
        ResponseEntity<Object> response = restTemplate.getForEntity(getCourseRegistrationEndpoint, Object.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Student not found or service unavailable");
        }
    }

    public void removeCourse(Long courseId) {
        //checkIfCourseIsRegistered(courseId);
        courseMasterRepository.deleteByCourseId(courseId);
    }

    public Boolean isCourseAvailable(Long courseId) {
        return courseMasterRepository.existsByCourseId(courseId);
    }

    public CourseMasterDTO getCourse(Long courseId) {
        return getCourseMasterDTO(courseMasterRepository.findById(courseId).get());
    }

    public List<CourseMasterDTO> getAllCourses() {
        List<CourseMasterDTO> courseMasterDTOs = new ArrayList<CourseMasterDTO>();
        courseMasterRepository.findAll().forEach(course->{
            courseMasterDTOs.add(getCourseMasterDTO(course));
        });
        return courseMasterDTOs;
    }

    private CourseMasterDTO getCourseMasterDTO(CourseMaster course) {
        CourseMasterDTO courseMasterDTO = new CourseMasterDTO();
        courseMasterDTO.setCourseId(course.getCourseId());
        courseMasterDTO.setCourseName(course.getCourseName());
        courseMasterDTO.setCourseDescription(course.getCourseDescription());
        courseMasterDTO.setCourseDuration(course.getCourseDuration());
        return courseMasterDTO;
    }

    private static CourseMaster getCourseMaster(CourseMasterDTO courseMasterDTO) {
        CourseMaster course = new CourseMaster();
        course.setCourseId(courseMasterDTO.getCourseId());
        course.setCourseName(courseMasterDTO.getCourseName());
        course.setCourseDescription(courseMasterDTO.getCourseDescription());
        course.setCourseDuration(courseMasterDTO.getCourseDuration());
        return course;
    }
}
