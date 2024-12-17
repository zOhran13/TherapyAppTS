package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session, String> {
    List<Session> findByPatientIsNullAndPsychologist(Psychologist psychologistId);
    List<Session> findByPsychologist(Psychologist psychologistId);
    Optional<Session> findByPsychologistAndPatient(Psychologist psychologist, Patient patient);
    Optional<Session> findByPatient(Patient patientId);
    void deleteByPsychologistAndDayAndTime(Psychologist psychologistId, String day, String time);
    boolean existsByPatient(Patient patientId);
    Optional<Session> findByPsychologistAndDayAndTimeAndPatientIsNull(Psychologist psychologistId, String day, String time);

    @Transactional
    @Modifying
    @Query("UPDATE Session s SET s.patient.userId = :patientId WHERE s.psychologist.userId = :psychologistId")
    void updatePatientIdByUserIdAndPsychologistId(String patientId, String psychologistId);
}

