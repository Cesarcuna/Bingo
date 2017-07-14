package mx.com.saganet.bingo.restService;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mx.com.saganet.bingo.utilidades.Paquete;

public class UsuarioSeccionesUpdate {
	private List<HashMap<String, Object>> listado;
	protected Paquete fragmento;
	public JsonArray obtenerUsuarioSeccion(HashMap<String, Object> mapa, SqlSession sqlSession, boolean isFragmento){
		JsonArray elementos= new JsonArray();
		if(isFragmento)listado = sqlSession.selectList("administracion.UsuarioSeccionesUpdate.buscarUsuariosSeccionesFragmento", mapa);
		else listado = sqlSession.selectList("administracion.UsuarioSeccionesUpdate.buscarUsuariosSecciones", mapa);
		
		if(listado.size()>0){
			for(HashMap<String, Object> map : listado){
				JsonObject item = new JsonObject();
				item.addProperty("pkey", map.get("pkey").toString());
				item.addProperty("id_usuario", map.get("id_usuario").toString());
				item.addProperty("id_seccion", map.get("id_seccion").toString());
				item.addProperty("id_casilla", map.get("id_casilla").toString());
				item.addProperty("version", map.get("version").toString());
				item.addProperty("operacion", map.get("operacion").toString());
				elementos.add(item);
			}
		}
		fragmento=new Paquete(elementos, true);
		return fragmento.Fragmento();
	}
	
	public Paquete getFragmento() {
		return fragmento;
	}

	public int Cantidad(){
		return listado.size();
	}
	
	public void Limpiar() throws UnsupportedOperationException{
		listado.clear();
	}

}
