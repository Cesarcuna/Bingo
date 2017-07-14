package mx.com.saganet.bingo.database.hack;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("UsuariosEO")
public class UsuariosEO extends JavaBeanT implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5117620404212119107L;
	private Integer id;
	private String nickname;
	private String password;
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	private Integer idRuta;
	private String tipoCamioneta;
	private String longitud;
	private String latitud;
	
	
	
	@Override
	public String toString() {
		return "UsuariosEO [id=" + id + ", nickname=" + nickname + ", password=" + password + ", nombre=" + nombre
				+ ", apellidoP=" + apellidoP + ", apellidoM=" + apellidoM + ", idRuta=" + idRuta + ", tipoCamioneta="
				+ tipoCamioneta + ", longitud=" + longitud + ", latitud=" + latitud + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoP() {
		return apellidoP;
	}
	public void setApellidoP(String apellidoP) {
		this.apellidoP = apellidoP;
	}
	public String getApellidoM() {
		return apellidoM;
	}
	public void setApellidoM(String apellidoM) {
		this.apellidoM = apellidoM;
	}


	public Integer getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}
	public String getTipoCamioneta() {
		return tipoCamioneta;
	}
	public void setTipoCamioneta(String tipoCamioneta) {
		this.tipoCamioneta = tipoCamioneta;
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
