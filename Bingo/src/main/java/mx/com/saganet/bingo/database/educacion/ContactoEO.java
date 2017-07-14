package mx.com.saganet.bingo.database.educacion;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.utilidades.JavaBeanT;

@Alias("ContactoEO")
public class ContactoEO extends JavaBeanT implements Serializable{

	private static final long serialVersionUID = 3941415893283653896L;
	private Integer id;
	private String usuario;
	private String telefono;
	private String email;
	
	
	
	@Override
	public String toString() {
		return "ContactoEO [id=" + id + ", usuario=" + usuario + ", telefono=" + telefono + ", email=" + email + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
