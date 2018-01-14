package application;

public class Barber extends Person {

	int yrsOfServ,EmgPhNum;
		
	public Barber(String name, int yrsOfServ, int phoneNum, int emgPhNum) {
		super(name,phoneNum);
		this.yrsOfServ = yrsOfServ;
		this.phoneNum = phoneNum;
		EmgPhNum = emgPhNum;
	}



}
