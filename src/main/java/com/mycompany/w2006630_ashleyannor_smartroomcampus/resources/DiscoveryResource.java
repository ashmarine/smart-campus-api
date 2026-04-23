/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w2006630_ashleyannor_smartroomcampus.resources;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ash
 */
@Path("/")
public class DiscoveryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getApiInfo(){
    
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("name", "Smart Campus Sensor and Room Management API");
    response.put("version", "v1");
    response.put("status", "online");
    
    Map<String, String> adminContact = new LinkedHashMap<>();
    adminContact.put("team", "Smart Campus Support");
    adminContact.put("email", "smartcampussupport@westminster.ac.uk");
    response.put("adminContact", adminContact);
    
    Map<String, String> resources = new LinkedHashMap<>();
    resources.put("self", "/api/v1");
    resources.put("rooms", "/api/v1/rooms");
    resources.put("sensors", "/api/v1/sensors");
    response.put("resources", resources);
    
    return response;
   }
}
