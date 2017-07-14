package mx.com.saganet.bingo.components;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.com.saganet.bingo.HomeController;

@Component("SubirArchivoC")
@Scope(value = "request")
public class SubirArchivoC {

	private UploadedFile file;

	private static final Logger logger = LoggerFactory.getLogger(SubirArchivoC.class);

	public void handleFileUpload(FileUploadEvent event) {
		final UploadedFile lfile = event.getFile();
		logger.debug("SiteFormManager::handleFileUpload {} --> File equals {}", event, lfile);
		logger.debug("Hola mundo.....................");
		
	}
	
	public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
