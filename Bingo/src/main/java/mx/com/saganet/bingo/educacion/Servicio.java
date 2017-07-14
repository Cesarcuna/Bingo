package mx.com.saganet.bingo.educacion;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

import mx.com.saganet.bingo.database.educacion.ContactoEO;
import mx.com.saganet.bingo.database.educacion.ContingenteEO;
import mx.com.saganet.bingo.database.educacion.GruposEO;
import mx.com.saganet.bingo.database.educacion.RegistroEO;
import mx.com.saganet.bingo.database.educacion.SeccionesEO;

@org.springframework.web.bind.annotation.RestController
@Controller
public class Servicio {
	private static final Logger logger = LoggerFactory.getLogger(Servicio.class);
	private static final String ApiKey="ADFtyuo1-23uYIO9d-h0389lsajncmYIUbbYU-Xr4";
	private static final String ApiWeb="WEgijds823-0u23-dmcOPmsmOOoOldsdm-asdpopYt0";
	
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping(value = "/contingente", method = RequestMethod.POST, params = {"key"})
	public JsonObject contingente(@RequestParam(value = "key") String key) {
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			List<ContingenteEO> mapeoObj;
			mapeoObj=sqlSession.selectList("educacion.contingente.listado");
			if(mapeoObj!=null){
				JsonArray array = new JsonArray();
				for(int i=0; i<mapeoObj.size();i++){
					JsonObject item = new JsonObject();
					item.addProperty("id", mapeoObj.get(i).getId());
					item.addProperty("acciones", mapeoObj.get(i).getAcciones());
					item.addProperty("descripcion", mapeoObj.get(i).getDescripcion());
					item.addProperty("nombre", mapeoObj.get(i).getNombre());
					item.addProperty("unidadmedida", mapeoObj.get(i).getUnidadMedida());
					item.addProperty("unidadmedidavalor", mapeoObj.get(i).getUnidadMedidaValor());
					array.add(item);
				}
				son.add("servicio", array);
			}
		}
		return son;
	}
	

	@RequestMapping(value = "/nuevoRegistro", method = RequestMethod.POST, params = {"key", "contingente", "grupos"})
	public JsonObject nuevoRegistro(@RequestParam(value = "key") String key,
			@RequestParam(value = "contingente") String contingente,
			@RequestParam(value = "grupos") String grupos) {
		RegistroEO registro= new RegistroEO();
		registro.setIdContingente(Integer.valueOf(contingente));
		registro.setIdGrupos(Integer.valueOf(grupos));
		sqlSession.insert("educacion.registro.insertar",registro);
		return null;
	}
	
	@RequestMapping(value = "/nuevoContacto", method = RequestMethod.POST, params = {"key", "nombre", "telefono", "email","grupos"})
	public JsonObject nuevoContacto(@RequestParam(value = "key") String key,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "telefono") String telefono,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "grupos") String grupos) {
		ContactoEO obj= new ContactoEO();
		obj.setUsuario(nombre);
		obj.setTelefono(telefono);
		obj.setEmail(email);
		logger.debug(obj.toString());
		sqlSession.insert("educacion.contactos.insertar",obj);
		GruposEO grupo = new GruposEO();
		grupo.setIdSeccion(Integer.valueOf(grupos));
		sqlSession.insert("educacion.grupos.insertar",grupo);
		return null;
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST, params = {"key"})
	public JsonObject registro(@RequestParam(value = "key") String key) {
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("Web"))){
			List<RegistroEO> mapeoObj;
			List<GruposEO> gruposObj;
			List<ContingenteEO> contingenciasObj;
			List<ContactoEO> contactosObj;
			List<SeccionesEO> seccionesObj;
			mapeoObj=sqlSession.selectList("educacion.registro.listado");
			if(mapeoObj!=null){
				JsonArray array = new JsonArray();
				for(int i=0; i<mapeoObj.size();i++){
					JsonObject item = new JsonObject();
					item.addProperty("id", mapeoObj.get(i).getId());
					//item.addProperty("idContingente", mapeoObj.get(i).getIdContingente());
					contingenciasObj=sqlSession.selectList("educacion.contingente.porId", mapeoObj.get(i).getIdContingente());
					if(contingenciasObj!=null){
						JsonArray subarray1 = new JsonArray();
						for(int j=0; j<contingenciasObj.size();j++){
							JsonObject subitem = new JsonObject();
							subitem.addProperty("id", contingenciasObj.get(j).getId());
							subitem.addProperty("nombre", contingenciasObj.get(j).getNombre());
							subitem.addProperty("unidadMedida", contingenciasObj.get(j).getUnidadMedida());
							subitem.addProperty("unidadMedidaValor", contingenciasObj.get(j).getUnidadMedidaValor());
							subitem.addProperty("descripcion", contingenciasObj.get(j).getDescripcion());
							subitem.addProperty("acciones", contingenciasObj.get(j).getAcciones());
							subarray1.add(subitem);
						}
						item.add("idContingente",subarray1);
					}
					//item.addProperty("idGrupos", mapeoObj.get(i).getIdGrupos());
					gruposObj=sqlSession.selectList("educacion.grupos.porId", mapeoObj.get(i).getIdGrupos());
					if(gruposObj!=null){
						JsonArray subarray1 = new JsonArray();
						for(int j=0; j<gruposObj.size();j++){
							JsonObject subitem = new JsonObject();
							subitem.addProperty("id", gruposObj.get(j).getId());
							//subitem.addProperty("idContacto", gruposObj.get(j).getIdContacto());
							contactosObj=sqlSession.selectList("educacion.contactos.porId", gruposObj.get(j).getIdContacto());
							if(contactosObj!=null){
								JsonArray subarray2 = new JsonArray();
								for(int k=0; k<contactosObj.size();k++){
									JsonObject subitem2 = new JsonObject();
									subitem2.addProperty("id", contactosObj.get(k).getId());
									subitem2.addProperty("usuario", contactosObj.get(k).getUsuario());
									subitem2.addProperty("telefono", contactosObj.get(k).getTelefono());
									subitem2.addProperty("email", contactosObj.get(k).getEmail());
									subarray2.add(subitem2);
								}
								subitem.add("idContacto", subarray2);
							}
							//subitem.addProperty("idSeccion", gruposObj.get(j).getIdSeccion());
							seccionesObj=sqlSession.selectList("educacion.secciones.porId", gruposObj.get(j).getIdSeccion());
							if(seccionesObj!=null){
								JsonArray subarray2 = new JsonArray();
								for(int k=0; k<seccionesObj.size();k++){
									JsonObject subitem2 = new JsonObject();
									subitem2.addProperty("id", seccionesObj.get(k).getId());
									subitem2.addProperty("nombre", seccionesObj.get(k).getNombre());
									subitem2.addProperty("descripcion", seccionesObj.get(k).getDescripcion());
									subarray2.add(subitem2);
								}
								subitem.add("idSeccion", subarray2);
							}
							subarray1.add(subitem);
						}
						item.add("idGrupos",subarray1);
					}
					
					item.addProperty("fecha", mapeoObj.get(i).getFecha().toString());
					item.addProperty("informado", mapeoObj.get(i).getInformado());
					array.add(item);
					mapeoObj.get(i).setInformado(true);
					sqlSession.update("educacion.registro.actualizar", mapeoObj.get(i));
				}
				son.add("servicio", array);
				logger.debug(mapeoObj.toString());
				//Marcar como enviado al mensaje
			}
			logger.debug(son.toString());
		}
		return son;
	}
	
	
	@RequestMapping(value = "/secciones", method = RequestMethod.POST, params = {"key"})
	public JsonObject secciones(@RequestParam(value = "key") String key) {
		JsonObject son = new JsonObject();
		if(key.equals(dosCrypt("App"))){
			List<SeccionesEO> mapeoObj;
			mapeoObj=sqlSession.selectList("educacion.secciones.listado");
			if(mapeoObj!=null){
				JsonArray array = new JsonArray();
				for(int i=0; i<mapeoObj.size();i++){
					JsonObject item = new JsonObject();
					item.addProperty("id", mapeoObj.get(i).getId());
					item.addProperty("nombre", mapeoObj.get(i).getNombre());
					item.addProperty("descripcion", mapeoObj.get(i).getDescripcion());
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
