package vttp.PAFWS.models;

import java.util.UUID;

public class User {
    
    private String user_id;
    private String username;
    private String name;
    private Integer task_id;

    public User(){
        this.user_id = UUID.randomUUID().toString().substring(0,8);
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getTask_id() {
        return task_id;
    }
    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }



}
