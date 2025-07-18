/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.last.entities;

import java.time.LocalDateTime;

public class Event {
    private int id;
    private String name;
    private String description;
    private LocalDateTime date;
    private String location;
    private int totalSeats;
    private int availableSeats;
    private int createdBy;
    private LocalDateTime createdAt;
    public Event() {}

    public Event(String name, String description, LocalDateTime date, String location, 
                int totalSeats, int createdBy) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.createdBy = createdBy;
    }
    public int getId() { 
        return id;
    }
    public void setId(int id) { 
        this.id = id; 
    }
    public String getName() { 
        return name;
    }
    public void setName(String name) {
        this.name = name; 
    }
    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description;
    }
    public LocalDateTime getDate() { 
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getLocation() { 
        return location; 
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(int totalSeats) { 
        this.totalSeats = totalSeats; 
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    public int getCreatedBy() { 
        return createdBy;
    }
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt;
    }
}
