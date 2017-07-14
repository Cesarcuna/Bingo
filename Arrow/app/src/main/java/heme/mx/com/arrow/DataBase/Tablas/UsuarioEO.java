package heme.mx.com.arrow.DataBase.Tablas;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import heme.mx.com.arrow.DataBase.Controler.Colums;
import heme.mx.com.arrow.DataBase.Controler.Component;

/**
 * Created by Chris on 14/07/2017.
 */

public class UsuarioEO extends Component {

    private int _id;
    private String nombre;
    private String password;
    private String nick;
    private ContentValues v;

    public UsuarioEO() {
    }

    public UsuarioEO(int _id, String nombre, String password, String nick) {
        this._id = _id;
        this.nombre = nombre;
        this.password = password;
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "UsuarioEO{" +
                "_id=" + _id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

    @Override
    public ContentValues contentValues() {
        v=new ContentValues();
        v.put(Colums._ID,get_id());
        v.put(Colums.COLUMN_NOMBRE,getNombre());
        v.put(Colums.COLUMN_PASSWORD,getPassword());
        v.put(Colums.COLUMN_NICK,getNick());
        return v;
    }

    @Override
    public Component JSONValues(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public String tableName() {
        return Colums.TABLE_USUARIO;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
