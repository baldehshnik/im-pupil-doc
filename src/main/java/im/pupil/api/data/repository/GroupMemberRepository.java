package im.pupil.api.data.repository;

import im.pupil.api.data.entity.group.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

    @Query("SELECT gm FROM GroupMember gm WHERE gm.code = :code AND gm.group.speciality.faculty.institution.id = :id")
    Optional<GroupMember> readGroupMemberOfInstitutionByCode(
            @Param("code") String code,
            @Param("id") Integer institutionId
    );

    @Query("SELECT gm FROM GroupMember gm WHERE gm.group.id = :id")
    List<GroupMember> readGroupMembers(@Param("id") Integer groupId);
}

























