package mx.com.saganet.bingo.database.hack;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("SeguimientoUsuariosEO")
public class SeguimientoUsuariosEO extends JavaBeanT implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4545026222162302049L;
	private Integer id;
	private Integer idUsuario;
	private String longitud;
	private String latitud;
	@Override
	public String toString() {
		return "SeguimientoUsuariosEO [Id=" + id + ", idUsuario=" + idUsuario + ", longitud=" + longitud + ", latitud="
				+ latitud + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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
	
	
}
