package gym;

import java.util.*;

import static gym.TrainingSession.DayOfWeek;


public class Timetable {
    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);
        if (daySchedule == null) {
            daySchedule = new TreeMap<>();
            timetable.put(day, daySchedule);
        }

        List<TrainingSession> sessions = daySchedule.get(time);

        if (sessions == null) {
            sessions = new ArrayList<>();
            daySchedule.put(time, sessions);
        }

        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule != null) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                result.addAll(sessions);
            }
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule != null) {
            return daySchedule.getOrDefault(timeOfDay, new ArrayList<>());
        }
        return new ArrayList<>();
    }

    public void displayTimetable() {
        System.out.println("===== РАСПИСАНИЕ =====");

        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

            if (daySchedule != null && !daySchedule.isEmpty()) {
                System.out.println("\n" + day + ":");

                for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : daySchedule.entrySet()) {
                    TimeOfDay time = entry.getKey();
                    List<TrainingSession> sessions = entry.getValue();

                    System.out.println("  " + String.format("%02d:%02d",
                            time.getHours(), time.getMinutes()) + " - " +
                            sessions.size() + " тренировок:");

                    for (TrainingSession session : sessions) {
                        System.out.println("      " +
                                session.getGroup().getTitle() + " (" +
                                session.getCoach().getSurname() + " " +
                                session.getCoach().getName() + ")");
                    }
                }
            }
        }
    }

    public Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }

    public void setTimetable(Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable) {
        this.timetable = timetable;
    }
}
