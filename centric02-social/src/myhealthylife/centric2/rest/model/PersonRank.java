package myhealthylife.centric2.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import myhealthylife.dataservice.soap.HealthProfile;

@XmlRootElement(name="personRank")
public class PersonRank {


    private long idPerson;
    private String username;
	private String firstname;
    private String lastname;
    private String sex;
    private Double points;
    
    
	public long getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(long idPerson) {
		this.idPerson = idPerson;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	
}
