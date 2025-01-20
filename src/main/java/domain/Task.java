package domain;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private boolean done = false;


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(int id,String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDone(boolean done){
        this.done = done;
    }

    public boolean getDone(){
        return this.done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
