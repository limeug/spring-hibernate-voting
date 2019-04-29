package ca.sheridancollege.beans;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Voter implements Serializable {

	private String fname;
	private String lname;
	private String birthdate;
	private String address;
	@Id
	private int sin;
	
	@OneToOne (cascade=CascadeType.ALL)
	private Vote vote;

	public Voter(String fname, String lname, String birthdate, String address, int sin) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.birthdate = birthdate;
		this.address = address;
		this.sin = sin;
	}
	
}
