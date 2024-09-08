package im.pupil.api.repository;

import im.pupil.api.model.Relocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
    Optional<Relocation> findById(int id);
    Optional<Relocation> findByNameAndPractice_Id(String name, int practiceId);
}