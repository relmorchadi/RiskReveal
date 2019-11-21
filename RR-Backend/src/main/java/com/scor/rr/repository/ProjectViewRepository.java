package com.scor.rr.repository;

//import com.scor.rr.domain.ProjectView;
//import com.scor.rr.domain.ProjectViewId;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ProjectViewRepository extends JpaRepository<ProjectView, ProjectViewId> {
//
//
//    @Query("select p from ProjectView p where p.workspaceId=:wsId and p.uwy=:uwy and (p.postInuredFlag=false or p.postInuredFlag is null) order by p.postInuredFlag desc, p.projectId desc")
//    List<ProjectView> findByWorkspaceIdAndUwy(@Param("wsId") String workspaceId, @Param("uwy") Integer uwy);
//
//
////    @Query("select p from ProjectView p where p.workspaceId=:wsId and p.uwy=:uwy and p.postInuredFlag=true")
//    Optional<ProjectView> findFirstByWorkspaceIdAndUwyAndPostInuredFlagIsTrue(@Param("wsId") String workspaceId, @Param("uwy") Integer uwy);
//
//}
