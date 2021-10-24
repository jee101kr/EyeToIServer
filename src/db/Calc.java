package db;

import model.DAO;

import java.util.ArrayList;
import java.util.List;

public class Calc {
    public List<DAO> circle(double ltt, double lgt){
        System.out.println("[read] ltt : " + ltt + " lgt : " + lgt);

        DB db = new DB();
        List<DAO> models = db.getDBList();
        List<DAO> resultModels = new ArrayList<>();

        for(DAO model : models){
            if(distance(ltt, lgt, model.getLatitude(), model.getLongitude()) < 100)
                resultModels.add(model);
        }

        for(DAO model : resultModels){
            System.out.println(model.getLatitude() + " " + model.getLongitude());
        }

        return resultModels;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;

        dist = dist * 1609.344;

        return dist;
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}