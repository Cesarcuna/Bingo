package heme.mx.com.arrow.MapsTraking;

import java.util.List;

/**
 * Created by LuisFernando on 29/06/2017.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
