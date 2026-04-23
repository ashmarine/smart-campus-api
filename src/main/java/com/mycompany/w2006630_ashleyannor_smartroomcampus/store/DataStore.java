/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w2006630_ashleyannor_smartroomcampus.store;

import com.mycompany.w2006630_ashleyannor_smartroomcampus.models.Sensor;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.models.Room;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.models.SensorReading;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ash
 */
public class DataStore {
    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
}
