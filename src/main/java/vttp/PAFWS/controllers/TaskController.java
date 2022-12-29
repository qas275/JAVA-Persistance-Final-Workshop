package vttp.PAFWS.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vttp.PAFWS.models.Task;
import vttp.PAFWS.models.User;
import vttp.PAFWS.services.TaskService;

@Controller
public class TaskController {
    
    @Autowired
    TaskService taskService;

    @PostMapping(path = "/addTask")
    public String addTask(@RequestBody MultiValueMap<String, String> form){
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");  
        User user = new User();
        user.setName(form.getFirst("name"));
        user.setUsername(form.getFirst("username"));
        Task task = new Task();
        try {
            task.setDate(formatter.parse(form.getFirst("date")));
        } catch (ParseException e) {
            System.out.println("\n\n\n %s\n\n\n".formatted(form.getFirst("date")));
            e.printStackTrace();
        }
        task.setPriority(Integer.parseInt(form.getFirst("priority")));
        task.setDescription(form.getFirst("description"));
        try {
            taskService.upsertTask(user, task);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "results";
    }

}
