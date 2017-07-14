package mx.com.saganet.bingo.database.administracion;

import java.io.Serializable;
import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

import mx.com.saganet.bingo.database.EntityObject;



@Alias("UsuarioEO")
public class UsuarioEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 705717660039700078L;

	private Integer id;
	private String nick;
	private String nombre;
	private String pw;	
	private Boolean habilitado;
	private Timestamp ultimoAcceso;
	private String mail;
	
	public UsuarioEO() {

	} 

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}


	public Timestamp getUltimoAcceso() {
		return ultimoAcceso;
	}


	public void setUltimoAcceso(Timestamp ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}