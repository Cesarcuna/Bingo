package mx.com.saganet.bingo.hack;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mx.com.saganet.bingo.database.hack.ClientesEO;
import mx.com.saganet.bingo.database.hack.RutasEO;
import mx.com.saganet.bingo.database.hack.SeguimientoUsuariosEO;
import mx.com.saganet.bingo.database.hack.SuscripcionesEO;
import mx.com.saganet.bingo.database.hack.UsuariosEO;


@org.springframework.web.bind.annotation.RestController
@Controller
public class ServicioR {
	@Autowired
	SqlSession sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(ServicioR.class);
	private static final String ApiKey="ADFtyuo1-23uYIO9d-h0389lsajncmYIUbbYU-Xr4";
	private static final String ApiWeb="WEgijds823-0u23-dmcOPmsmOOoOldsdm-asdpopYt0";
	
	@RequestMapping(value = "/resgistro", method = RequestMethod.POST, params = {"key", "nick","password","nombre","ruta","tipo"})
	public JsonObject resgistro(
			@RequestParam(value = "key") String key,
			@RequestParam(value = "nick") String nick,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "ruta") String ruta,
			@RequestParam(value = "tipo") String tipo){
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			logger.debug("tipo. " +tipo);
			if(tipo.equals("0x001")){
				UsuariosEO usuario= new UsuariosEO();
				usuario.setNickname(nick);
				usuario.setPassword(password);
				usuario.setNombre(nombre);
				usuario.setIdRuta(Integer.valueOf(ruta));
				sqlSession.insert("hack.usuarios.insertar", usuario);
			}else if(tipo.equals("0x002")){
				ClientesEO clientes = new ClientesEO();
				clientes.setNickname(nick);
				clientes.setNombre(nombre);
				clientes.setPassword(password);
				sqlSession.insert("hack.clientes.insertar", clientes);
			}
			List<RutasEO> rutas;
			rutas=sqlSession.selectList("hack.ruta.listado");
			if(rutas!=null){
				JsonArray array = new JsonArray();
				for(int i=0; i<rutas.size();i++){
					JsonObject item = new JsonObject();
					item.addProperty("id", rutas.get(i).getId());
					item.addProperty("nombre", rutas.get(i).getNombre());
					item.addProperty("nodos", rutas.get(i).getRutas());
					array.add(item);
				}
				son.add("servicio", array);
			}
		}
		return son;
	}
	
	//No creo utilizarle
	@RequestMapping(value = "/suscripcion", method = RequestMethod.POST, params = {"key", "idusuario","idruta"})
	public JsonObject suscripcion(
			@RequestParam(value = "key") String key,
			@RequestParam(value = "idusuario") String idusuario,
			@RequestParam(value = "idruta") String idruta) {
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			SuscripcionesEO suscripciones= new SuscripcionesEO();
			suscripciones.setIdCliente(Integer.valueOf(idusuario));
			suscripciones.setIdRuta(Integer.valueOf(idruta));
			sqlSession.insert("hack.usuarios.insertar", suscripciones);
			JsonArray array = new JsonArray();
			JsonObject item = new JsonObject();
			item.addProperty("response", "0x003");
			array.add(item);
			son.add("servicio", array);
		}
		return son;
	}
	
	@RequestMapping(value = "/traking", method = RequestMethod.POST, params = {"key", "idruta"})
	public JsonObject traking(
			@RequestParam(value = "key") String key,
			@RequestParam(value = "idruta") String idruta) {
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			List<UsuariosEO> usuarios;
			usuarios=sqlSession.selectList("hack.usuarios.listado", Integer.valueOf(idruta));
			if(usuarios!=null){
				JsonArray array = new JsonArray();
				for(int i=0; i<usuarios.size();i++){
					JsonObject item = new JsonObject();
					item.addProperty("id", usuarios.get(i).getId());
					item.addProperty("longitud", usuarios.get(i).getLongitud());
					item.addProperty("latitud", usuarios.get(i).getLatitud());
					array.add(item);
				}
				son.add("servicio", array);
			}
		}
		return son;
	}
	
	@RequestMapping(value = "/dondestoy", method = RequestMethod.POST, params = {"key", "idusuario", "latitud", "longitud"})
	public JsonObject dondeestoy(
			@RequestParam(value = "key") String key,
			@RequestParam(value = "idusuario") String idusuario,
			@RequestParam(value = "latitud") String latitud,
			@RequestParam(value = "longitud") String longitud) {
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			SeguimientoUsuariosEO seguimiento = new SeguimientoUsuariosEO();
			SeguimientoUsuariosEO n = new SeguimientoUsuariosEO();
			seguimiento.setIdUsuario(Integer.valueOf(idusuario));
			seguimiento.setLatitud(latitud);
			seguimiento.setLongitud(longitud);
			n= sqlSession.selectOne("hack.seguimiento.porId", idusuario);
			if(n==null)sqlSession.insert("hack.seguimiento.insertar", seguimiento);
			else sqlSession.update("hack.seguimiento.actualizar", seguimiento);
			JsonArray array = new JsonArray();
			JsonObject item = new JsonObject();
			item.addProperty("response", "0x003");
			array.add(item);
			son.add("servicio", array);
		}
		return son;
	}
	
	@RequestMapping(value = "/rutas", method = RequestMethod.POST, params = {"key"})
	public JsonObject rutas(
			@RequestParam(value = "key") String key) {
		List<RutasEO> rutas;
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			rutas=sqlSession.selectList("hack.ruta.listado");
			if(rutas!=null){
				JsonArray array = new JsonArray();
				for(int i=0; i<rutas.size();i++){
					JsonObject item = new JsonObject();
					item.addProperty("id", rutas.get(i).getId());
					item.addProperty("nombre", rutas.get(i).getNombre());
					item.addProperty("nodos", rutas.get(i).getRutas());
					array.add(item);
				}
				son.add("servicio", array);
			}
		}
		return son;
	}
		
	private String dosCrypt(String type){
		String Base="";
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			switch(type){
				case "App":
					m.update(ApiKey.getBytes(), 0, ApiKey.length());
					break;
				case "Web":
					m.update(ApiWeb.getBytes(), 0, ApiWeb.length());
					break;
			}
			Base = new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Base;
	}
}
