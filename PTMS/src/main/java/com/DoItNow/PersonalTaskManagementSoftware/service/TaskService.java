package com.DoItNow.PersonalTaskManagementSoftware.service;

import com.DoItNow.PersonalTaskManagementSoftware.model.NewTask;
import com.DoItNow.PersonalTaskManagementSoftware.model.Task;

import java.util.List;

/**
 * The TaskService interface
 *
 * @author ibrahim KARAYEL
 * @version 1.0
 * Date 4/27/2018.
 */
public interface TaskService {

    Task save(Task task);

    Boolean delete(int id);

    Task update(Task task);

    Task findById(int id);

    List<NewTask> findAll(Integer user_id);

    List<Task> findAll();

    List<Task> findByStatus(String status);

    List<Task> findByUserIdStatus(int userId, String status);

    List<Task> findBetween(int start, int end);

}