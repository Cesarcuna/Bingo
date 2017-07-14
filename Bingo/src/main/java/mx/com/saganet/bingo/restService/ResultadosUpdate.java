package mx.com.saganet.bingo.restService;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

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

@org.springframework.web.bind.annotation.RestController
@Controller
public class ResultadosUpdate {
	private static final Logger logger = LoggerFactory.getLogger(ResultadosUpdate.class);
	@Autowired
	SqlSession sqlSession;
	private List<HashMap<String, Object>> listado;
	
	@RequestMapping(value = "/resultadosUpdate", method = RequestMethod.POST, params = { "usuario", "seccion", "casilla","contestados","fechafoto","longitud", "latitud", "imei", "filacolumna" })
	public JsonObject usuarioUpdate(@RequestParam(value = "usuario") String usuario,@RequestParam(value = "seccion") String seccion, @RequestParam(value = "casilla") String casilla,@RequestParam(value = "contestados") String contestados, @RequestParam(value = "fechafoto") String fechafoto, @RequestParam(value = "longitud") String longitud,@RequestParam(value = "latitud") String latitud,@RequestParam(value = "imei") String imei,@RequestParam(value = "filacolumna") String filacolumna,ModelMap model) throws UnsupportedEncodingException{
		System.out.println("usuario:: " + usuario);
		HashMap<String, Object> datosPOST=new HashMap<String, Object>();
		datosPOST.put("usuario", Integer.valueOf(usuario));
		datosPOST.put("seccion", seccion);
		datosPOST.put("casilla", casilla);
		datosPOST.put("contestados", contestados);
		datosPOST.put("fechafoto", fechafoto);
		datosPOST.put("longitud", longitud);
		datosPOST.put("latitud", latitud);
		datosPOST.put("imei", imei);
		datosPOST.put("filacolumna", filacolumna);
		System.out.println("contestados:: " + datosPOST.toString());
		//new String(casilla.getBytes(), "UTF-8")
		try{
			/* REGISTRAR LA SINCRONIZACION... */
			sqlSession.insert("usuarios.ResultadosSyncUpdate.registro", datosPOST);		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = "/registroMensajes", method = RequestMethod.POST, params = { "telefono", "mensaje"})
	public JsonObject resgitroMensajes(@RequestParam(value = "telefono") String telefono,@RequestParam(value = "mensaje") String mensaje,@RequestParam(value = "puerto") String puerto,ModelMap model) throws UnsupportedEncodingException{
		HashMap<String, Object> datosPOST=new HashMap<String, Object>();
		String grupoSocial="";
		boolean faltaGsocial=false;
		mensaje=(mensaje=mensaje.replace(" ", "")).toUpperCase();
		//if(!encontrarGrupo(mensaje)){
			//grupoSocial= String.valueOf(mensaje.charAt(0));
			//mensaje=mensaje.substring(2);
			//faltaGsocial=false;
		//}else{
			//faltaGsocial=true;
		//}
		/*
		 * F=FITZ	A=ABBY	R=ROWAN
		 */
		grupoSocial="D";
		JsonObject son = new JsonObject();
		datosPOST.put("telefono", telefono);
		datosPOST.put("mensaje", mensaje);
		datosPOST.put("programa", grupoSocial.equals("F")?"LICONSA":grupoSocial.equals("A")?"PROSPERA":grupoSocial.equals("R")?"INSUS":"Desconocido");
		datosPOST.put("puerto", puerto);
		//System.out.println("datos:: " + datosPOST.toString());
		//new String(casilla.getBytes(), "UTF-8")
		try{
			/* REGISTRAR LA SINCRONIZACION... */
			JsonObject itemDatos = new JsonObject();
			if(!faltaGsocial && (grupoSocial.equals("F") || grupoSocial.equals("A") || grupoSocial.equals("R") || grupoSocial.equals("D"))){
				String values=procesarRegistros(mensaje);
				sqlSession.insert("registro.RegistroMensajes.registro", datosPOST);		
				if(values.equals("")){
					itemDatos.addProperty("mensaje", "Registro exitoso");
				}
				else{
					itemDatos.addProperty("mensaje", "Información incorrecta");
					itemDatos.addProperty("folios", values);
				}
			}else{
				//itemDatos.addProperty("mensaje", "Falta programa o es incorrecto");
			}
			JsonArray datos = new JsonArray();
			datos.add(itemDatos);
			son.add("datos", datos);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return son;
	}
	
	private String procesarRegistros(String g){
		String folios="";
		StringTokenizer s2 = new StringTokenizer(g, ",");
		while (s2.hasMoreElements()) {
			String value= s2.nextElement().toString();
			try{
				Integer iu= Integer.parseInt(value);
				listado = sqlSession.selectList("registro.RegistroMensajes.verificarId", iu);
				if(listado.size()>0)
					for(HashMap<String, Object> map : listado)if(map.get("total").toString().equals("0"))folios+=String.valueOf(iu) + ", ";
			}
			catch(Exception e){
				folios+=value + ",";
				//e.printStackTrace();
			}
		}
		return folios;
	}
	
	private boolean encontrarGrupo(String g){
		boolean y=false;
		String p=String.valueOf(g.charAt(1));
		try{
			Integer u= Integer.parseInt(p);
			y=true;
		}catch(Exception e){
			y=false;
		}
		return y;
	}
}
