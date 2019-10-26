package com.source.project.repos;

import com.source.project.domain.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TypeRep extends CrudRepository<Type, Long> {
    Type findById(Integer id);
    Type findByTypename(String typename);
    Type findByParentId(Integer parentId);

    @Query(value="WITH RECURSIVE r AS (SELECT id, parent_id, typename FROM type WHERE id = :childId UNION SELECT type.id, type.parent_id, type.typename  FROM type JOIN r  ON type.id = r.parent_id ) SELECT * FROM r;",
            nativeQuery = true)
    List<Type> findTree(@Param("childId") Integer childId);

    List<Type> findByParentIdIsNull();
    List<Type> findByParentIdIsNotNull();
    List<Type> findAllByParentId(Integer id);
}
