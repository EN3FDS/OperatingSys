package software;

public class Interrupt {
	
	private final int SIZE = 64; //size chaque entree 64 bits
	private String name;
	private short address; //adresse de la routine
	private byte privilege; //Privilege level of the interrupt
	
	public Interrupt() {
		// TODO Auto-generated constructor stub
	}
	
	public Interrupt(String name, short address, byte privilege) {
		this.name=name;
		this.address=address;
		this.privilege=privilege;
	}

	public short getAddress() {
		return address;
	}

	public void setAddress(short address) {
		this.address = address;
	}

	public short getPrivilege() {
		return privilege;
	}

	public void setPrivilege(byte privilege) {
		this.privilege = privilege;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
