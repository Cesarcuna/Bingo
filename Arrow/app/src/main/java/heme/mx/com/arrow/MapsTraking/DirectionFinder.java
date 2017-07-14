package heme.mx.com.arrow.MapsTraking;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import heme.mx.com.arrow.logger.LoggerC;


/**
 * Created by LuisFernando on 29/06/2017.
 */
public class DirectionFinder {
    LoggerC log=new LoggerC(DirectionFinder.class);
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyANq-BDRLVxXgpipLtVRPPxpE-8Yz0g_7g";
    private DirectionFinderListener listener;
    private String origin;
    private String destination;

    public DirectionFinder(DirectionFinderListener listener, String origin, String destination) {
        this.listener = listener;
        this.origin = origin;
        this.destination = destination;
    }

    public void execute() throws UnsupportedEncodingException {
        listener.onDirectionFinderStart();
        //Log.e("->", "createUrl() " +createUrl());
        new DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        //String urlOrigin = URLEncoder.encode(origin, "utf-8");
        //String urlDestination = URLEncoder.encode(destination, "utf-8");
        //return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&key=" + GOOGLE_API_KEY;
        return DIRECTION_URL_API + "origin=" + origin + "&destination=" + destination + "&key=" + GOOGLE_API_KEY;
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSon(String data) throws Exception {
        if (data == null)return;
        List<Route> routes = new ArrayList<Route>();
        JSONObject jsonData = new JSONObject(data);
        //Log.e("->", "JSON " +jsonData.toString());
        JSONArray jsonRoutes = jsonData.optJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.optJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonRoute.optJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.optJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.optJSONObject(0);
            JSONObject jsonDistance = jsonLeg.optJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.optJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.optJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.optJSONObject("start_location");

            route.distance = new Distance(jsonDistance.optString("text"), jsonDistance.optInt("value"));
            route.duration = new Duration(jsonDuration.optString("text"), jsonDuration.optInt("value"));
            route.endAddress = jsonLeg.optString("end_address");
            route.startAddress = jsonLeg.optString("start_address");
            route.startLocation = new LatLng(jsonStartLocation.optDouble("lat"), jsonStartLocation.optDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.optDouble("lat"), jsonEndLocation.optDouble("lng"));
            route.points = decodePolyLine(overview_polylineJson.optString("points"));

            routes.add(route);
        }

        listener.onDirectionFinderSuccess(routes);
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
