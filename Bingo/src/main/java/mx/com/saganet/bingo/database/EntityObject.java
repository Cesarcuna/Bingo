package mx.com.saganet.bingo.database;


public class EntityObject {
	public Integer getId(){
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		EntityObject check;
		
		check = (EntityObject) obj;
		
		if(this.getClass().equals(obj.getClass()))
			if(this.getId().equals(check.getId()))
				return true;

		return false;
	}
}
