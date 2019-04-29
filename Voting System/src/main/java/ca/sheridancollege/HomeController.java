package ca.sheridancollege;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.sheridancollege.beans.Vote;
import ca.sheridancollege.beans.Voter;
import ca.sheridancollege.dao.DAO;

@Controller
public class HomeController {
	DAO dao = new DAO();
	
	@RequestMapping("/")
	public String goIndex(Model model) {
		return "index";
	}
	
	@RequestMapping("/DummyRecordsGenerated")
	public String goDummy(Model model) {
		dao.generateVoters();
		model.addAttribute("msg", "Records generated!");
		return "index";
	}
	
	@RequestMapping("/View")
	public String goView(Model model) {
		model.addAttribute("voterList", dao.getVoters());
		return "display";
	}
	
	@RequestMapping("/edit/{sin}")
	public String editVoter(Model model, @PathVariable int sin) {
		Voter voter = dao.getVoter(sin);
		synchronized(Voter.class) {
			dao.deleteVoter(sin);
		}
		model.addAttribute("voter", voter);
		return "register";
	}
	
	@RequestMapping("/delete/{sin}")
	public String deleteVoter(Model model, @PathVariable int sin) {
		dao.deleteVoter(sin);
		model.addAttribute("voterList", dao.getVoters());
		return "display";
	}
	
	@RequestMapping("/Register")
	public String goRegister(Model model) {
		model.addAttribute("voter", new Voter());
		return "register";
	}
	
	@RequestMapping("/Registered")
	public String goRegistered(Model model, @ModelAttribute Voter voter) {
		if (voter.getBirthdate().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
			//check age
			String[] bdate = voter.getBirthdate().split("-", 3);
			LocalDate start = LocalDate.of(Integer.parseInt(bdate[0]), Integer.parseInt(bdate[1]), Integer.parseInt(bdate[2]));
			LocalDate end = LocalDate.now();// use for age-calculation: LocalDate.now()
			long years = ChronoUnit.YEARS.between(start, end);
			if (years<18) {
				model.addAttribute("msg", "You are under 18, and therefore ineligible to vote.");
			}
			else {
				//check if sin is the right format
				if (String.valueOf(voter.getSin()).length() == 9) {
					//check if voter sin already exists
					if (!dao.checkVoterExists(voter.getSin())){
						synchronized(Voter.class){
							dao.addVoter(voter);
						}
						model.addAttribute("msg",  "Your voter status has been registered!");
						return "index";
					}
					else {
						model.addAttribute("msg", "Voter's SIN already exists in the system.");
					}
				}
				else {
					model.addAttribute("msg", "Please enter a valid SIN");
				}
			}
		}
		else {
			model.addAttribute("msg", "Please enter a valid birth date.");
		}
		return "register";
	}
	
	@RequestMapping("/Vote")
	public String goVote(Model model) {
		model.addAttribute("voter", new Voter());
		model.addAttribute("valid", false);
		return "vote";
	}
	
	@RequestMapping("/Voting")
	public String goVotingCheck(Model model, @ModelAttribute Voter voter) {
		// check if sin exists
		Voter v = dao.getVoter(voter.getSin());
		if (dao.checkVoterExists(v.getSin())) {
			//check if voter has voted before
			if (v.getVote() == null) {
				model.addAttribute("valid", true);
				model.addAttribute("voter", v);
				model.addAttribute("vote", new Vote());
			}
			else {
				model.addAttribute("msg", "You have already voted. You may only vote once.");
			}
		}
		else {
			model.addAttribute("msg", "That SIN has not been registered to vote. Please register before you vote.");
		}
		return "vote";
	}
	
	@RequestMapping("/Voting/{sin}")
	public String goVoting(Model model, @PathVariable int sin, @ModelAttribute Vote vote) {
		Voter voter = dao.getVoter(sin);
		Vote v = new Vote(voter, vote.getVotedParty());
		dao.addVote(v);
		model.addAttribute("msg", "You have successfully voted for the " + vote.getVotedParty() +".");
		return "index";
	}
	
	@RequestMapping("/Stats")
	public String goStats(Model model) {
		List<Vote> votes = dao.getVotes();
		List<Voter> voters = dao.getVoters();
		
		//calculate % of wins for each party
		int liberal = 0, con = 0, ndp = 0, bq = 0, gp = 0;
		for (Vote vote: votes) {
			if (vote.getVotedParty().equals("Liberal Party")){
				liberal += 1;
			}
			else if (vote.getVotedParty().equals("Conservative Party")){
				con += 1;
			}
			else if (vote.getVotedParty().equals("New Democratic Party")){
				ndp += 1;
			}
			else if (vote.getVotedParty().equals("Bloc Quebecois")){
				bq += 1;
			}
			else if (vote.getVotedParty().equals("Green Party")){
				gp += 1;
			}	
		}
		double liberalP, conP, ndpP, bqP, gpP;
		
		
		liberalP = ((double)liberal/votes.size()) * 100;
		conP = ((double)con/votes.size()) * 100;
		ndpP = ((double)ndp/votes.size()) * 100;
		bqP = ((double)bq/votes.size()) * 100;
		gpP = ((double)gp/votes.size()) * 100;
		DecimalFormat df = new DecimalFormat("#.##");
		model.addAttribute("liberalP", Double.valueOf(df.format(liberalP)));
		model.addAttribute("conP", Double.valueOf(df.format(conP)));
		model.addAttribute("ndpP", Double.valueOf(df.format(ndpP)));
		model.addAttribute("bqP", Double.valueOf(df.format(bqP)));
		model.addAttribute("gpP", Double.valueOf(df.format(gpP)));
		
		//get % of voters who voted
		double votesToVoters = ((double)votes.size()/voters.size()) * 100;
		model.addAttribute("votesToVoters", Double.valueOf(df.format(votesToVoters)));
		
		//% of voters per age bracket
		int VyoungAdult = 0, Vadult = 0, VolderAdult = 0, Velderly = 0, youngAdult = 0, adult = 0, olderAdult = 0, elderly = 0;;
		for (Voter voter : voters) {
			String birthdate = voter.getBirthdate();
			String[] bdate = birthdate.split("-", 3);
			LocalDate start = LocalDate.of(Integer.parseInt(bdate[0]), Integer.parseInt(bdate[1]), Integer.parseInt(bdate[2]));
			LocalDate end = LocalDate.now();
			long years = ChronoUnit.YEARS.between(start, end);
			//if voter voted
			if (voter.getVote() != null) {
				if (years >= 60) {
					Velderly += 1;
				}
				else if (years >= 45) {
					VolderAdult += 1;
				}
				else if (years >= 30) {
					Vadult += 1;
				}
				else if (years >= 18) {
					VyoungAdult += 1;
				}
			}
			else {
				if (years >= 60) {
					elderly += 1;
				}
				else if (years >= 45) {
					olderAdult += 1;
				}
				else if (years >= 30) {
					adult += 1;
				}
				else if (years >= 18) {
					youngAdult += 1;
				}
			}
		}
		
		double youngAdultP = ((double)VyoungAdult/(VyoungAdult+youngAdult)) * 100;
		double adultP = ((double)Vadult/(Vadult+adult)) * 100;
		double olderAdultP = ((double)VolderAdult/(VolderAdult+olderAdult)) * 100;
		double elderlyP = ((double)Velderly/(Velderly+elderly)) * 100;
		
		model.addAttribute("youngAdultP", Double.valueOf(df.format(youngAdultP)));
		model.addAttribute("adultP", Double.valueOf(df.format(adultP)));
		model.addAttribute("olderAdultP", Double.valueOf(df.format(olderAdultP)));
		model.addAttribute("elderlyP", Double.valueOf(df.format(elderlyP)));
		
		return "stats";
	}
	
}
