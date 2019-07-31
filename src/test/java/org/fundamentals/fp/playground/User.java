package org.fundamentals.fp.playground;

public class User {
    private String name;
    private String id;

    public User(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }
}
