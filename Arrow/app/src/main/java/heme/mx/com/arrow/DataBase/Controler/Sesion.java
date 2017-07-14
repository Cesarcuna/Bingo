package heme.mx.com.arrow.DataBase.Controler;

import android.content.ContentValues;
import android.content.Context;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import heme.mx.com.arrow.DataBase.Tablas.RutasEO;
import heme.mx.com.arrow.Variables.ArrowC;
import heme.mx.com.arrow.logger.LoggerC;


/**
 * Created by LuisFernando on 15/05/2017.
 */

public class Sesion extends SQLiteOpenHelper {
    LoggerC log=new LoggerC(Sesion.class);
    private static Sesion instance;
    Context c=null;

    public static synchronized Sesion getHelper(Context context) {
        if (instance == null)
            instance = new Sesion(context);
        return instance;
    }

    public Sesion(Context context) {
        super(context, ArrowC.DATA_BASE_NAME, null, ArrowC.DATA_BASE_VERSION);
        c=context;
        SQLiteDatabase.loadLibs(context);
    }

    public long SVE(Component component){
        SQLiteDatabase sqld= getWritableDatabase();
        return sqld.insert(component.tableName(),null,component.contentValues());
    }

    public long UDE(Component component){
        SQLiteDatabase sqld= getWritableDatabase();
        return sqld.update(component.tableName(),component.contentValues(),"_id="+component.get_id(), null);
    }

    public long UDE(Component component, String[] colums, String[] values){
        SQLiteDatabase sqld= getWritableDatabase();
        ContentValues v =new ContentValues();
        for(int c=0;c<colums.length; c++){
            v.put(colums[0],values[0]);
        }
        return sqld.update(component.tableName(),v,"_id="+component.get_id(), null);
    }

    public int getLastAutoId(Component component, SQLiteDatabase db) {
        int index = 0;
        Cursor cursor = component.AutoId(db);
        if(cursor.getCount() > 0){
            if (cursor.moveToFirst()) index = cursor.getInt(cursor.getColumnIndex(Colums.SEQ));
            cursor.close();return index+1;
        }else {
            return index;
        }
    }

    public int getLastAutoId(Component component) {
        return getLastAutoId(component, getReadableDatabase());
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CREATE_TABLES(sqLiteDatabase);
        SVE_DEFAULT_DATA(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Colums.TABLE_USUARIO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Colums.TABLE_RUTAS);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    SQLiteDatabase getReadableDatabase() {
        return(super.getReadableDatabase(ArrowC.DATA_BASE_SECURITY));
    }

    SQLiteDatabase getWritableDatabase() {
        return(super.getWritableDatabase(ArrowC.DATA_BASE_SECURITY));
    }

    private  void CREATE_TABLES(SQLiteDatabase db) {
        db.execSQL(Colums.CREATE_TABLE_USUARIO);
        db.execSQL(Colums.CREATE_INDEX_USUARIO_ID);
        db.execSQL(Colums.CREATE_TABLE_RUTAS);
        db.execSQL(Colums.CREATE_INDEX_RUTAS_ID);
    }

    private void SVE_DEFAULT_DATA(SQLiteDatabase db){

    }

    public boolean getUsers(String nick, String password){
        Cursor cursor = this.getReadableDatabase().query(Colums.TABLE_USUARIO,
                new String[]{Colums._ID,Colums.COLUMN_NOMBRE,Colums.COLUMN_PASSWORD,Colums.COLUMN_NICK},
                Colums.COLUMN_NICK + "=? AND " + Colums.COLUMN_PASSWORD + "=? ",new String[]{nick,password},null,null,Colums._ID);
        if (cursor != null && cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        return false;
    }

    public List<RutasEO> rutas(){
        List<RutasEO> rut= new ArrayList<RutasEO>();
        Cursor cursor = this.getReadableDatabase().query(Colums.TABLE_RUTAS,
                new String[]{Colums._ID,Colums.COLUMN_NOMBRE,Colums.COLUMN_NODOS},
                null,null,null,null,Colums._ID);
        if (cursor != null && cursor.getCount() > 0){
            if (cursor.moveToFirst()) {
                do {
                    rut.add(new RutasEO(
                            cursor.getInt(cursor.getColumnIndex(Colums._ID)),
                            cursor.getString(cursor.getColumnIndex(Colums.COLUMN_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(Colums.COLUMN_NODOS))
                    ));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        return rut;
    }

    public List<String> rutasLista(){
        List<String> rut= new ArrayList<String>();
        ArrowC.respuestas= new ArrayList<String>();
        Cursor cursor = this.getReadableDatabase().query(Colums.TABLE_RUTAS,
                new String[]{Colums._ID,Colums.COLUMN_NOMBRE,Colums.COLUMN_NODOS},
                null,null,null,null,Colums._ID);
        if (cursor != null && cursor.getCount() > 0){
            if (cursor.moveToFirst()) {
                do {
                    rut.add(
                            cursor.getString(cursor.getColumnIndex(Colums.COLUMN_NOMBRE))
                    );
                    ArrowC.respuestas.add(cursor.getString(cursor.getColumnIndex(Colums._ID)));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        return rut;
    }

}
