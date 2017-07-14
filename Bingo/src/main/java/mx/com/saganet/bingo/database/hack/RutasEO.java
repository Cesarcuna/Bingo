package mx.com.saganet.bingo.database.hack;
import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("RutasEO")
public class RutasEO extends JavaBeanT implements Serializable{

	private static final long serialVersionUID = 7553803690986393759L;
	private Integer id;
	private String nombre;
	private String rutas;
	
	
	
	@Override
	public String toString() {
		return "RutasEO [id=" + id + ", nombre=" + nombre + ", rutas=" + rutas + "]";
	}
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRutas() {
		return rutas;
	}
	public void setRutas(String rutas) {
		this.rutas = rutas;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
