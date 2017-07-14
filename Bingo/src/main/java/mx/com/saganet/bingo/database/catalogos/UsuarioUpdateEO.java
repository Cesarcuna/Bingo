package mx.com.saganet.bingo.database.catalogos;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("UsuarioUpdateEO")
public class UsuarioUpdateEO extends JavaBeanT implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8513201309564333628L;
	/**
	 * 
	 */
	
	private Integer id;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String password;
	private String nick;
	private Integer version;
	private Integer operacion;
	@Override
	public String toString() {
		return "UsuarioUpdateEO [id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido
				+ ", segundoApellido=" + segundoApellido + ", password=" + password + ", nick=" + nick + ", version="
				+ version + ", operacion=" + operacion + "]";
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
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getOperacion() {
		return operacion;
	}
	public void setOperacion(Integer operacion) {
		this.operacion = operacion;
	}
	
	
}
