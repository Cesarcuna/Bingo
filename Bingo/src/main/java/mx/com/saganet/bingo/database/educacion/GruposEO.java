package mx.com.saganet.bingo.database.educacion;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("GruposEO")
public class GruposEO extends JavaBeanT implements Serializable{

	private static final long serialVersionUID = -4315911946690406771L;
	private Integer id;
	private Integer idSeccion;
	private Integer idContacto;
	@Override
	public String toString() {
		return "GruposEO [id=" + id + ", idSeccion=" + idSeccion + ", idContacto=" + idContacto + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdSeccion() {
		return idSeccion;
	}
	public void setIdSeccion(Integer idSeccion) {
		this.idSeccion = idSeccion;
	}
	public Integer getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(Integer idContacto) {
		this.idContacto = idContacto;
	}
	
}
