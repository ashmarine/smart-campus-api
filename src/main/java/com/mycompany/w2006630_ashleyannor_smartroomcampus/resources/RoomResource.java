/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.w2006630_ashleyannor_smartroomcampus.resources;

import com.mycompany.w2006630_ashleyannor_smartroomcampus.models.Room;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.store.DataStore;
import com.mycompany.w2006630_ashleyannor_smartroomcampus.exceptions.RoomNotEmptyException;
import java.net.URI;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
/**
 *
 * @author ash
 */

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class RoomResource {
    @GET
    public Collection<Room> getAllRooms(){
        return DataStore.rooms.values();
    }
    
    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo){
        if (room == null || room.getId() == null || room.getId().trim().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID is required")
                    .build();
        }
        
        DataStore.rooms.put(room.getId(), room);
        
        URI uri = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();
        return Response.created(uri)
                .entity(room)
                .build();
    }
    
    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId")String roomId){
        Room room = DataStore.rooms.get(roomId);
        
        if (room == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }
        return Response.ok(room).build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                 .entity("Room not found")
                 .build();
    }

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room can't be deleted because sensors are still assigned to it");
    }

    DataStore.rooms.remove(roomId);
    return Response.ok("Room deleted successfully").build();
}
}
