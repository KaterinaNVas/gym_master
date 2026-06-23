package gym;


public class Group {
    private String title;
    private Age age;
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