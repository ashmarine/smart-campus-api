/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w2006630_ashleyannor_smartroomcampus.resources;

import com.mycompany.w2006630_ashleyannor_smartroomcampus.models.Sensor;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.models.SensorReading;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.store.DataStore;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.exceptions.SensorUnavailableException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author ash
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    
    private String sensorId;
    
    public SensorReadingResource(String sensorId){
    this.sensorId = sensorId;
}
    
   @GET
    public Response getReadings() {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor is not found")
                    .build();
        }
        
        List<SensorReading> sensorReadings = DataStore.readings.get(sensorId);
        
        if (sensorReadings == null){
            sensorReadings = new ArrayList<>();
        }
        return Response.ok(sensorReadings).build();
    }
    
    @POST
    public Response addReading(SensorReading reading, @Context UriInfo uriInfo) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor is not found")
                    .build();
        }
        
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("This sensor is in maintenance and cannot accept new readings");
        }
        

        if (reading == null || reading.getId() == null || reading.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Reading ID is required")
                    .build();
        }

        List<SensorReading> sensorReadings = DataStore.readings.get(sensorId);

        if (sensorReadings == null) {
            sensorReadings = new ArrayList<>();
            DataStore.readings.put(sensorId, sensorReadings);
        }

        sensorReadings.add(reading);

        sensor.setCurrentValue(reading.getValue());

        URI uri = uriInfo.getAbsolutePathBuilder().path(reading.getId()).build();
        return Response.created(uri)
                .entity(reading)
                .build();
    }
}