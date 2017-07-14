package mx.com.saganet.bingo.database.hack;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("ClientesEO")
public class ClientesEO extends JavaBeanT implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8054578031250068606L;
	private Integer id; 
	private String nickname;
	private String password;
	private String nombre;
	
	@Override
	public String toString() {
		return "ClientesEO [id=" + id + ", nickname=" + nickname + ", password=" + password + ", nombre=" + nombre
				+ "]";
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
	
	
	
}
