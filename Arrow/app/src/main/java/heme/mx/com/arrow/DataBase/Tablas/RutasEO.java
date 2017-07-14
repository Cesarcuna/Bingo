package heme.mx.com.arrow.DataBase.Tablas;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import heme.mx.com.arrow.DataBase.Controler.Colums;
import heme.mx.com.arrow.DataBase.Controler.Component;

/**
 * Created by Chris on 14/07/2017.
 */

public class RutasEO extends Component {

    private int _id;
    private String nombre;
    private String nodos;
    private ContentValues v;

    public RutasEO() {
    }

    public RutasEO(int _id, String nombre, String nodos) {
        this._id = _id;
        this.nombre = nombre;
        this.nodos = nodos;
    }

    @Override
    public String toString() {
        return "RutasEO{" +
                "_id=" + _id +
                ", nombre='" + nombre + '\'' +
                ", nodos='" + nodos + '\'' +
                '}';
    }

    @Override
    public ContentValues contentValues() {
        v=new ContentValues();
        v.put(Colums._ID,get_id());
        v.put(Colums.COLUMN_NOMBRE,getNombre());
        v.put(Colums.COLUMN_NODOS,getNodos());
        return v;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return new RutasEO(
                jsonObject.optInt("id"),
                jsonObject.optString("nombre"),
                jsonObject.optString("nodos"));
    }

    @Override
    public String tableName() {
        return Colums.TABLE_RUTAS;
    }


    @Override
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNodos() {
        return nodos;
    }

    public void setNodos(String nodos) {
        this.nodos = nodos;
    }
}
