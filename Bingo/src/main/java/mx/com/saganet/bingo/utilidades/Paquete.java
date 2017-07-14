package mx.com.saganet.bingo.utilidades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Paquete {
	private static final Logger logger = LoggerFactory.getLogger(Paquete.class);
	//TODO una humbrar minimo de paquetes muy grande causara un consumo mayor de DATOS en el dispositivo
	//un humbrar mayor causara perdida de datos por exceder limites de trafico de datos por paquete...
	private final int MAX_PACKAGE_SIZE=50;
	private JsonArray elementos= new JsonArray();
	private boolean activo;
	private int numPaquetes=0;
	private int ultimoId=0;
	
	public Paquete(JsonArray elementos, boolean activo) {
		this.activo=activo;
		this.elementos=elementos;
		getNumPaquetes();
	}
	
	public JsonArray Fragmento() {
		if(activo){
			if(elementos.size()>0 && elementos.size()<MAX_PACKAGE_SIZE){
				return elementos;
			}else if(elementos.size()>MAX_PACKAGE_SIZE){
				//Se ejecutara si el tamanio del paquete revasa el permitido, para ello se enviaran paquetes(por partes)
				return Principal(elementos, MAX_PACKAGE_SIZE);
			}
		}
		return elementos;
	}
		
	public int getNumPaquetes() {
		return numPaquetes==0?NumeroPaquetes():numPaquetes;
	}
	
	//Numero de paquetes a enviar por el metodo POST
	public int NumeroPaquetes(){
		if(elementos.size()>MAX_PACKAGE_SIZE){
				return (int) Math.ceil((double)(elementos.size()/(double)MAX_PACKAGE_SIZE));
		}else{
			return 1;
		}
	}
	
	public int getUltimoId() {
		return ultimoId;
	}

	//Retorna los primero elementos del paquete
	private JsonArray Principal(JsonArray elementos, int c){
		JsonArray i= new JsonArray();
		for(int f=0;f<c;f++)i.add(elementos.get(f));
		ultimoId=((JsonObject)elementos.get(c)).get("pkey").getAsInt();
		return i;
	}
}
