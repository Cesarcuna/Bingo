package mx.com.saganet.bingo.components.archivos;


import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//import com.saganet.syncdm.database.archivos.SincronizacionArchivoEO;

@Component("SincronizacionArchivosC")
public class SincronizacionArchivosC {
	
	@Autowired
	SqlSession sqlSession;

	private static final Logger logger = LoggerFactory.getLogger(SincronizacionArchivosC.class);

	public Integer getIdSincronizacionArchivo(){
		return sqlSession.selectOne("archivos.sincronizacionArchivos.getIdSincronizacionArchivo");
	}
	
	//public void guardarSincronizacionArchivo(SincronizacionArchivoEO nuevo){
	//	logger.debug("SincronizacionArchivoEO Recibido: {}", nuevo);
	//	sqlSession.insert("archivos.sincronizacionArchivos.insertarSincronizacionArchivo", nuevo);
	//	logger.debug("SincronizacionArchivo guardado correctamente.");
	//}
	
}
