package sandbox.model;

public class Company {
	private int id;
	private String name;
	private String desc;
	private String icon;
	private String address;
	
	public Company (int id, String name, String desc, String icon, String address){
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.icon = icon;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
