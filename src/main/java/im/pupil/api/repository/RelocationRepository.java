package im.pupil.api.repository;

import im.pupil.api.model.Relocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
}