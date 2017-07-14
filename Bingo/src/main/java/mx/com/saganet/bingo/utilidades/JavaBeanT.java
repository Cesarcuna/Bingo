package mx.com.saganet.bingo.utilidades;


public class JavaBeanT {
	public Integer getId(){
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		JavaBeanT check;
		
		check = (JavaBeanT) obj;
		
		if(this.getId().equals(check.getId()))
			return true;
		else
			return false;
	}
}
