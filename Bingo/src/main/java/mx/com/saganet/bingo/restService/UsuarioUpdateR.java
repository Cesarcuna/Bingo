package mx.com.saganet.bingo.restService;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mx.com.saganet.bingo.database.catalogos.UsuarioUpdateEO;


@org.springframework.web.bind.annotation.RestController
@Controller
public class UsuarioUpdateR {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioUpdateR.class);
	@Autowired
	SqlSession sqlSession;

	@RequestMapping(value = "/usuarioUpdate", method = RequestMethod.GET, params = { "usuario", "pw", "imei","longitud", "latitud" })
	public JsonObject usuarioUpdate(
			@RequestParam(value = "usuario") String usuario,
			@RequestParam(value = "pw") String pw, @RequestParam(value = "imei") String imei,
			@RequestParam(value = "longitud") String longitud, @RequestParam(value = "latitud") String latitud) {
		HashMap<String, Object> datosPOST;
		UsuarioUpdateEO mapaUsuario;
		String claveBase="";
		JsonObject son = new JsonObject();
		/* buscamos al usuario */
		datosPOST = new HashMap<String, Object>();
		datosPOST.put("imei", imei);
		datosPOST.put("longitud", longitud);
		datosPOST.put("latitud", latitud);
		datosPOST.put("tipo", "USUARIOS");
		/* ejecutamos el select por usuario */
		mapaUsuario = sqlSession.selectOne("administracion.UsuarioUpdate.buscarUsuario", usuario);
		/* VALIDAMOS DE QUE LA CONSULTA REGRESE ALGUN REGISTRO */
		if (mapaUsuario != null) {
			/* ENCRIPTAMOS LA CONTRTASEÑA Y VERIFICAMOS QUE LA INGRESADA SEA LA MISMA... */
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(mapaUsuario.getPassword().getBytes(), 0, mapaUsuario.getPassword().length());
				claveBase = new BigInteger(1, m.digest()).toString(16);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			if (pw.equals(claveBase)) {
				JsonArray array = new JsonArray();
					JsonObject item = new JsonObject();
					item.addProperty("id", mapaUsuario.getId());
					item.addProperty("nick", mapaUsuario.getNick());
					item.addProperty("password",claveBase);
					item.addProperty("nombre", mapaUsuario.getNombre());
					item.addProperty("paterno", mapaUsuario.getPrimerApellido());
					item.addProperty("materno", mapaUsuario.getSegundoApellido());
					item.addProperty("version", mapaUsuario.getVersion());
					item.addProperty("operacion", mapaUsuario.getOperacion());
					array.add(item);
				datosPOST.put("id_usuario", mapaUsuario.getId());
				/* REGISTRAR LA SINCRONIZACION... */
				sqlSession.insert("usuarios.UsuarioSyncUpdate.registro", datosPOST);
				son.add("usuario_update", array);
				SeccionesUpdate seccionesUpdate = new SeccionesUpdate();
				son.add("seccion_update", seccionesUpdate.obtenerSecciones(datosPOST, sqlSession));
				CasillaUpdate casillaUpdate= new CasillaUpdate();
				son.add("casilla_update", casillaUpdate.obtenerCasilla(datosPOST, sqlSession));
				UsuarioSeccionesUpdate usuarioSeccionesUpdate= new UsuarioSeccionesUpdate();
				JsonArray US_SEC=usuarioSeccionesUpdate.obtenerUsuarioSeccion(datosPOST, sqlSession, false);
				item = new JsonObject();
				item.addProperty("id", mapaUsuario.getId());
				item.addProperty("seccion_contar", seccionesUpdate.Cantidad());
				item.addProperty("casilla_contar", casillaUpdate.Cantidad());
				item.addProperty("usuario_seccion_contar", usuarioSeccionesUpdate.Cantidad());
				item.addProperty("usuario_seccion_paquetes", usuarioSeccionesUpdate.getFragmento().getNumPaquetes());
				item.addProperty("usuario_seccion_ultimo_id", usuarioSeccionesUpdate.getFragmento().getUltimoId());
				array = new JsonArray();
				array.add(item);
				son.add("datos_sync", array);
				son.add("usuario_seccion_update", US_SEC);
			} else {
				JsonArray arrayClaveIncorrecta = new JsonArray();
				JsonObject itemClaveIncorrecta = new JsonObject();
				itemClaveIncorrecta.addProperty("descripcion", "Clave incorrecta, porfavor intente nuevamente");
				arrayClaveIncorrecta.add(itemClaveIncorrecta);
				son.add("error_clave_incorrecta", arrayClaveIncorrecta);
			}
		}else {
			JsonArray arrayUserNoExiste = new JsonArray();
			JsonObject itemUserNoExiste = new JsonObject();
			itemUserNoExiste.addProperty("descripcion", "El usuario que ingresó no existe, favor de verificarlo");
			arrayUserNoExiste.add(itemUserNoExiste);
			son.add("error_usuario_no_existe", arrayUserNoExiste);
		}
		return son;
	}
	
	@RequestMapping(value = "/usuarioPaqueteUpdate", method = RequestMethod.POST, params = { "usuario", "pw", "imei","longitud", "latitud", "ultimo_id","id_usuario"})
	public JsonObject usuarioPaqueteUpdate(
			@RequestParam(value = "usuario") String usuario,
			@RequestParam(value = "pw") String pw, @RequestParam(value = "imei") String imei,
			@RequestParam(value = "longitud") String longitud, @RequestParam(value = "latitud") String latitud,
			@RequestParam(value = "ultimo_id") String ultimo_id,
			@RequestParam(value = "id_usuario") String id_usuario) {
		JsonObject son = new JsonObject();
		HashMap<String, Object> datosPOST;
		datosPOST = new HashMap<String, Object>();
		datosPOST.put("imei", imei);
		datosPOST.put("longitud", longitud);
		datosPOST.put("latitud", latitud);
		datosPOST.put("id_usuario", Integer.valueOf(id_usuario));
		datosPOST.put("ultimo_id", Integer.valueOf(ultimo_id));
		datosPOST.put("tipo", "PAQUETE");
		sqlSession.insert("usuarios.UsuarioSyncUpdate.registro", datosPOST);
		UsuarioSeccionesUpdate usuarioSeccionesUpdate= new UsuarioSeccionesUpdate();
		JsonArray US_SEC=usuarioSeccionesUpdate.obtenerUsuarioSeccion(datosPOST, sqlSession, true);
		JsonObject itemDatos = new JsonObject();
		itemDatos.addProperty("usuario_seccion_ultimo_id", usuarioSeccionesUpdate.getFragmento().getUltimoId());
		JsonArray datos = new JsonArray();
		datos.add(itemDatos);
		son.add("datos_sync", datos);
		son.add("usuario_seccion_update", US_SEC);
		return son;
		
	}
	
}
