package mx.com.saganet.bingo.restService;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CasillaUpdate {
	private List<HashMap<String, Object>> listado;
	public JsonArray obtenerCasilla(HashMap<String, Object> mapa, SqlSession sqlSession){
		JsonArray elementos= new JsonArray();
		listado = sqlSession.selectList("administracion.CasillasUpdate.buscarCasillas", mapa);
		if(listado.size()>0){
			for(HashMap<String, Object> map : listado){
				JsonObject item = new JsonObject();
				item.addProperty("pkey", map.get("pkey").toString());
				item.addProperty("tipo", map.get("tipo").toString());
				item.addProperty("version", map.get("version").toString());
				item.addProperty("operacion", map.get("operacion").toString());
				elementos.add(item);
			}
		}
		return elementos;
	}
	
	public int Cantidad(){
		return listado.size();
	}
	public void Limpiar() throws UnsupportedOperationException{
		listado.clear();
	}
}
