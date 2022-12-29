package vttp.PAFWS.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.PAFWS.models.Task;
import vttp.PAFWS.models.User;
import vttp.PAFWS.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    TaskRepository taskRepository;

    @Transactional
    public void upsertTask(User user, Task task) throws OrderException{
        Optional<User> userRes = taskRepository.findUserByUserId(user.getUsername());
        if(userRes.isEmpty()){
            taskRepository.insertUser(user);
        }
        if(!taskRepository.insertTask(task)){
            throw new OrderException("unable to add task");
        }
    }
}
