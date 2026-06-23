package gym;

import static gym.TrainingSession.Age;

public class Group {
    //название группы
    private String title;
    //тип (взрослая или детская)
    private Age age;
    //длительность (в минутах)
    private int duration;

    public Group(String title, Age age, int duration) {
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}