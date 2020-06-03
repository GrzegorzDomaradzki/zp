package edu.agh.zp.repositories;

import edu.agh.zp.objects.DocumentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DocumentStatusRepository extends JpaRepository<DocumentStatusEntity, Long> {
    List<DocumentStatusEntity> findByDocStatusName(String Name);
    List<DocumentStatusEntity> findAll();
    List<DocumentStatusEntity> findByDocStatusNameIn(Collection<String> Names);
    List<DocumentStatusEntity> findByDocStatusIDIn(List<Long> ids);
}
