package mx.com.saganet.bingo.database.educacion;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("ContingenteEO")
public class ContingenteEO extends JavaBeanT implements Serializable{
	private static final long serialVersionUID = 2681394202288126485L;
	private Integer id;
	private String unidadMedida;
	private Integer UnidadMedidaValor;
	private String nombre;
	private String descripcion;
	private String acciones;
	@Override
	public String toString() {
		return "ContingenteEO [id=" + id + ", unidadMedida=" + unidadMedida + ", UnidadMedidaValor=" + UnidadMedidaValor
				+ ", nombre=" + nombre + ", descripcion=" + descripcion + ", acciones=" + acciones + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public Integer getUnidadMedidaValor() {
		return UnidadMedidaValor;
	}
	public void setUnidadMedidaValor(Integer unidadMedidaValor) {
		UnidadMedidaValor = unidadMedidaValor;
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
	public String getAcciones() {
		return acciones;
	}
	public void setAcciones(String acciones) {
		this.acciones = acciones;
	}
	
	
}
