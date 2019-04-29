package ca.sheridancollege.beans;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vote implements Serializable {
	
	@Id
	@GeneratedValue
	private int id;
	private String votedParty;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="vote")
	private Voter voter;
	
	@Transient
	private String[] voteList = {"Liberal Party","Conservative Party","New Democratic Party","Bloc Quebecois","Green Party"};
	
	public Vote (Voter voter, String votedParty) {
		this.voter = voter;
		this.votedParty = votedParty;
	}
}
