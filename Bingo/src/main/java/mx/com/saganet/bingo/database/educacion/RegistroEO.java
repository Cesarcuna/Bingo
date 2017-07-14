package mx.com.saganet.bingo.database.educacion;

import java.io.Serializable;
import java.sql.Date;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("RegistroEO")
public class RegistroEO extends JavaBeanT implements Serializable{

	private static final long serialVersionUID = -5720150316347981811L;
	private Integer id;
	private Integer idContingente;
	private Integer idGrupos;
	private String fecha;
	private Boolean informado;
	@Override
	public String toString() {
		return "RegistroEO [id=" + id + ", idContingente=" + idContingente + ", idGrupos=" + idGrupos + ", fecha="
				+ fecha + ", informado=" + informado + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdContingente() {
		return idContingente;
	}
	public void setIdContingente(Integer idContingente) {
		this.idContingente = idContingente;
	}
	
	public Integer getIdGrupos() {
		return idGrupos;
	}
	public void setIdGrupos(Integer idGrupos) {
		this.idGrupos = idGrupos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Boolean getInformado() {
		return informado;
	}
	public void setInformado(Boolean informado) {
		this.informado = informado;
	}
	
	
	
}
