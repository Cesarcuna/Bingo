package mx.com.saganet.bingo.beans.seguridad;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import mx.com.saganet.bingo.database.administracion.UsuarioEO;



public class UsuariosServicio extends JdbcDaoImpl {

	private Usuario usuario;

	private SqlSession sqlSession;
	
	public UsuariosServicio() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;

		user = (User) super.loadUserByUsername(username);

		usuario = new Usuario(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
		
		//Implementacion propia
		usuario.setUsuario(porNick(usuario.getUsername()));
		
		return usuario;
	}
	
	public UsuarioEO porNick(String nick) {
		UsuarioEO usuario;

		usuario = sqlSession.selectOne("administracion.usuarios.porNick", nick);

		return usuario;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
