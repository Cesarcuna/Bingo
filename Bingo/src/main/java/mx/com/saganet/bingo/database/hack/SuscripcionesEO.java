package mx.com.saganet.bingo.database.hack;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("SuscripcionesEO")
public class SuscripcionesEO extends JavaBeanT implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7737754032124536621L;
	private Integer id;
	private int idCliente;
	private int idRuta;
	private String fecha;
	@Override
	public String toString() {
		return "SuscripcionesEO [id=" + id + ", idCliente=" + idCliente + ", idRuta=" + idRuta + ", fecha=" + fecha
				+ "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
		
	
}
