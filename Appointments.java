package application;

public class Appointments implements Comparable<Appointments> 
{
	String clName,time,brbrName,phone;
	
	public Appointments(String cln, String ti, String brbr,String phoneNum) {
		
		clName = cln;
		time = ti;
		brbrName = brbr;
		phone = phoneNum;
		 
	}

	public String getClName() {
		return clName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setBrbrName(String brbrName) {
		this.brbrName = brbrName;
	}

	public String getBrbrName() {
		return brbrName;
	}

	@Override
	public String toString()
	{
		
		return "Appointment Customer Name=" + clName +
							",Appointment Time=" + time + 
							",Customer Phone =" + phone+
							",Barber Name=" + brbrName ;
	}

	public String getTime()
	{
		return time;
	}
	
	public int switchStr(String strTime)
	{
		int hh=Integer.parseInt(strTime.substring(0, 2));
		int mm=Integer.parseInt(strTime.substring(3, 5));
		String dt=strTime.substring(6, 8);
		if(dt.equals("Pm")&&hh<12)
			{
			hh+=12;
			}
		return  hh*100+mm;
	}

	@Override
	public int compareTo(Appointments rhs)
	{
		if(switchStr(this.getTime())>switchStr(rhs.getTime()))
		return 1;
		else if(switchStr(this.getTime())>switchStr(rhs.getTime()))
			return 0;
		else
			return -1;
	}

	
	
	
	
}
