/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.last.entities;

import java.time.LocalDateTime;

public class Registration {
    private int id;
    private int userId;
    private int eventId;
    private LocalDateTime registrationDate;

    public Registration() {}

    public Registration(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
    public int getId() { 
        return id; 
    }
    public void setId(int id) {
        this.id = id; 
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId; 
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
