package mx.com.saganet.bingo.database.educacion;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("SeccionesEO")
public class SeccionesEO extends JavaBeanT implements Serializable{

	private static final long serialVersionUID = -5365301582295385319L;
	private Integer id;
	private String nombre;
	private String descripcion;
	@Override
	public String toString() {
		return "SeccionesEO [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
