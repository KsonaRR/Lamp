package com.groovylamp.data;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groovylamp.models.File;
import com.groovylamp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Users {

    public static Users usersInstance;
    public ArrayList<User> userArrayList = new ArrayList<>();
    private Users() {}
    public static Users getInstance() {
        if (usersInstance == null) {
            usersInstance = new Users();
        }
        return usersInstance;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(userArrayList);
    }

    public void setUsers(List<User> users) {
        userArrayList = new ArrayList<>(users);
    }
    @SuppressLint("NewApi")
    public User findUser(String login){
        List<User> users = userArrayList.stream().filter(i -> i.getLogin().equals(login)).collect(Collectors.toList());

        return users.size() == 0 ? null : users.get(0);
    }
    @SuppressLint("NewApi")
    public boolean findUserLogin(String login){
        return userArrayList.stream().anyMatch(i -> i.getLogin().equals(login));
    }
    public void addUser(User user) { userArrayList.add(user); }
    public boolean saveUsers(String path){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        File file = new File();
        return file.WriteFile(path, "users.json", gson.toJson(userArrayList));
    }
    public void loadUsers(String path){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        File file = new File();
        setUsers(Arrays.asList(gson.fromJson(file.ReadFile(path, "users.json"),User[].class)));
    }
}
