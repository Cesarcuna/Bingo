package mx.com.saganet.bingo.database.hack;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("PuntosControlEO")
public class PuntosControlEO extends JavaBeanT implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4496175161089871373L;
	private Integer id;
	private int idRuta;
	private String longitud;
	private String latitud;
	private String nombre;
	
	@Override
	public String toString() {
		return "PuntosControlEO [id=" + id + ", idRuta=" + idRuta + ", longitud=" + longitud + ", latitud=" + latitud
				+ ", nombre=" + nombre + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
	
}
