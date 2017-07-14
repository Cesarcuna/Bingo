package heme.mx.com.arrow.DataBase.Controler;

import android.provider.BaseColumns;

/**
 * Created by LuisFernando on 15/05/2017.
 */

public class Colums implements BaseColumns {
    private static final String CREATE="CREATE TABLE IF NOT EXISTS ";
    public static final String SEQ="seq";
    public static final String NAME="name";
    public static final String COLUMN_VERSION="version";

    /*TABLA USUARIO*/
    public static final String TABLE_USUARIO="usuario";
    public static final String COLUMN_NOMBRE="nombre";
    public static final String COLUMN_PASSWORD="password";
    public static final String COLUMN_NICK="nick";
    public static final String CREATE_INDEX_USUARIO_ID=
            "CREATE INDEX index_"+
                    TABLE_USUARIO+
                    "_id ON "+
                    TABLE_USUARIO+
                    " (_id ASC);";
    public static final String CREATE_TABLE_USUARIO=
            CREATE + TABLE_USUARIO +
                    "(" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_NICK + " TEXT)" ;

    /*TABLA RUTAS*/
    public static final String TABLE_RUTAS="rutas";
    public static final String COLUMN_NODOS="nodos";
    public static final String CREATE_INDEX_RUTAS_ID=
            "CREATE INDEX index_"+
                    TABLE_RUTAS+
                    "_id ON "+
                    TABLE_RUTAS+
                    " (_id ASC);";
    public static final String CREATE_TABLE_RUTAS=
            CREATE + TABLE_RUTAS +
                    "(" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NOMBRE + " TEXT NOT NULL," +
                    COLUMN_NODOS + " TEXT NOT NULL)" ;
}
