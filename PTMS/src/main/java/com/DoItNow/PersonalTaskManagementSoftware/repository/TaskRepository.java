package com.DoItNow.PersonalTaskManagementSoftware.repository;

import com.DoItNow.PersonalTaskManagementSoftware.model.NewTask;
import com.DoItNow.PersonalTaskManagementSoftware.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The TaskRepository class
 *
 * @author ibrahim KARAYEL
 * @version 1.0
 * Date 4/27/2018.
 */
public interface TaskRepository extends CrudRepository<Task, Integer> {

    //@Query("from Task t where t.status=:status")
    List<Task> findByStatus(String status);

    List<Task> findByUserIdAndStatus(int userId,  String status);

    @Query(value = "SELECT title, \"end\", start FROM tasks WHERE user_id=:userId", nativeQuery = true)
    List<NewTask> findAll(@Param("userId") Integer userId);

 /*   @Query("from Task t where t.userId=:userId and  t.status=:status")
    List<Task> findByUserIdStatus(@Param("userId") int userId, @Param("status") String status);*/

    @Query("from Task t where t.id BETWEEN  :start and :end")
    List<Task> findBetween(@Param("start") int start, @Param("end") int end);

}
