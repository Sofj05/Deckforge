package org.example.deckforge.Domain;

import org.example.deckforge.Domain.Enums.Decktype;
import org.example.deckforge.Domain.Enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private User organizer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private LocalTime time;

    private String location;
    private int maxParticipants;
    private List<Integer> participants;
    private Decktype format;
    private String rules;
    private Status status;

    public Event(){}

    public Event(String name, User organizer, LocalDate date, LocalTime time, String location, int maxParticipants, List<Integer> participants, Decktype format, String rules, Status status){
        this.name = name;
        this.organizer = organizer;
        this.date = date;
        this.time = time;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.participants = participants;
        this.format = format;
        this.rules = rules;
        this.status = status;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    
    public void setOrganizer(User organizer){
        this.organizer = organizer;
    }
    public User getOrganizer(){
        return organizer;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }
    public LocalDate getDate(){
        return date;
    }

    public void setTime(LocalTime time){
        this.time = time;
    }
    public LocalTime getTime(){
        return time;
    }

    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return location;
    }

    public void setMaxParticipants(int maxParticipants){
        this.maxParticipants = maxParticipants;
    }
    public int getMaxParticipants(){
        return maxParticipants;
    }

    public void setParticipants(List<Integer> participants){
        this.participants = participants;
    }
    public List<Integer> getParticipants(){
        return participants;
    }

    public void setFormat(Decktype format){
        this.format = format;
    }
    public Decktype getFormat(){
        return format;
    }

    public void setRules(String rules){
        this.rules = rules;
    }
    public String getRules(){
        return rules;
    }

    public void setStatus(Status status){
        this.status = status;
    }
    public Status getStatus(){
        return status;
    }
}
