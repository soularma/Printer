package raspberry;

public class Temperature{
	
	public static final String EXTR1_DEFAULT = "200";
	public static final String EXTR2_DEFAULT = "200";
	public static final String EXTR3_DEFAULT = "200";
	public static final String EXTR4_DEFAULT = "200";
	public static final String EXTR5_DEFAULT = "200";
	public static final String TEMP_LIT_DEFAULT = "200";
		
	private String extrudeur1,extrudeur2,extrudeur3, extrudeur4, extrudeur5, tempLit;
	
	public Temperature(String extrudeur1, String extrudeur2, String extrudeur3, String extrudeur4, String extrudeur5, String tempLit ) {
		this.extrudeur1=extrudeur1;
		this.extrudeur2=extrudeur2;
		this.extrudeur3=extrudeur3;
		this.extrudeur4=extrudeur4;
		this.extrudeur5=extrudeur5;
		this.tempLit=tempLit;
	}
	public Temperature() {
		this(EXTR1_DEFAULT,EXTR2_DEFAULT,EXTR3_DEFAULT,EXTR4_DEFAULT,EXTR5_DEFAULT,TEMP_LIT_DEFAULT);
	}
	
	public String getTempExtrudeur(int numExtrudeur) {
		String value = "";
		switch (numExtrudeur) {
			case 1 : 
				value = this.extrudeur1;
				break;
			case 2 :
				value = this.extrudeur2;
				break;
			case 3 :
				value = this.extrudeur3;
				break;
			case 4 :
				value = this.extrudeur4;
				break;
			case 5 : 
				value = this.extrudeur5;
				break;
			default :
				value = "Extrudeur 1 : " + this.extrudeur1
				+ "\n Extrudeur 2 : " + this.extrudeur2 
				+ "\n Extrudeur 3 : " + this.extrudeur3 
				+ "\n Extrudeur 4 : " + this.extrudeur4 
				+ "\n Extrudeur 5 : " + this.extrudeur5;
		}
		return value;
	}
	public String getTempLit() {
		return this.tempLit + "\n";
	}
	
	public void setTempExtrudeur(String value,int numExtrudeur) {
		switch (numExtrudeur) {
			case 1 : 
				this.extrudeur1 = value;
				break;
			case 2 :
				this.extrudeur2 = value;
				break;
			case 3 :
				this.extrudeur3 = value;
				break;
			case 4 :
				this.extrudeur4 = value;
				break;
			case 5 : 
				this.extrudeur5 = value;
				break;
			default :
				System.out.println("Impossible setting extrudeur temperature");
		}
	}
	
	public void setTempLit(String temperatureLit){
		this.tempLit=temperatureLit;
	}
	
	public String getInfo() {
		String info = this.getTempExtrudeur(0) + "\n Température Lit : " + this.getTempLit();
		return info;
	}
	
	public String toUart() {
		String value = this.getTempExtrudeur(1) + "\0" + 
				this.getTempExtrudeur(2) + "\0" + 
				this.getTempExtrudeur(3) + "\0" +
				this.getTempExtrudeur(4) + "\0" +
				this.getTempExtrudeur(5) + "\0" +
				this.getTempLit();
				return value;
	}

}
