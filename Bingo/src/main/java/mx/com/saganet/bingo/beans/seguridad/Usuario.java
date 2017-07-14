package mx.com.saganet.bingo.beans.seguridad;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import mx.com.saganet.bingo.database.administracion.UsuarioEO;


public class Usuario extends User {
	private static final long serialVersionUID = 8247256886680715375L;
	
	private UsuarioEO usuario;
	
	public Usuario(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities); 
	}

	
	
	public Usuario(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}



	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + "] \n" + super.toString();
	}

	public UsuarioEO getUsuario() {
		return usuario;
	}



	public void setUsuario(UsuarioEO usuario) {
		this.usuario = usuario;
	}


}
