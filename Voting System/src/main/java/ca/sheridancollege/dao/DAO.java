package ca.sheridancollege.dao;

import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ca.sheridancollege.beans.Vote;
import ca.sheridancollege.beans.Voter;

public class DAO {
	
	private boolean recordsGenerated = false;
	SessionFactory sessionFactory = new Configuration()
			.configure("hibernate.cfg.xml")
			.buildSessionFactory();
	
	public void generateVoters() {
		
		if (!recordsGenerated) {
			String[] names = {"Amado","Rounds","Toi","Owings","Jed","Cataldo","Delorse","Canterbury","Brittney","Mccaffrey","Randolph","Caryl","Calandra","Jacks","Nadene","Kepley","Precious","Murphy","Christen","Palomino","Jasmine","Lettinga","Dell","Temme","Lawanda","Nauman","Octavia","Wilmer","Aleisha","Lashley","Mauro","Winchester","Rachael","Mcbain","Luke","Winburn","Bok","Thurber","Karan","Ventimiglia","Ingrid","Coca","Gema","Eberhardt","Chaya","Alpers","August","Ahner","Benita","Lamontagne","Shirlene","Saraiva","Annamaria","Zenz","Racquel","Dawkins","Dollie","Bales","Liane","Galante","Miss","Zendejas","Rod","Mickelsen","Stacy","Chiu","Thi","Abalos","Andree","Duarte","Yael","Sorrell","Jamila","Longfellow","Ian","Keating","Sabina","Guerrier","Kylee","Eckart","Bethann","Monroy","Ashlea","Deaner","Raymon","Ritenour","Viki","Durrance","Eulalia","Schilke","Rachell","Redmon","Chantel","Kilgore","Merlin","Knebel","Janie","Rusch","Stephane","Ovalle"};			
			int i = 0;
			Random rand = new Random();
			while (i < 200) {
				String fname = names[rand.nextInt(names.length)];
				String lname = names[rand.nextInt(names.length)];
				//generate date
				int year = (rand.nextInt(2002 - 1939 + 1) + 1939) ;
				int month = (rand.nextInt(12) + 1) ;
				String monthString;
				if (month <10) {
					monthString = "0" + month;
				}
				else {
					monthString = String.valueOf(month);
				}
				String dayString;
				int day = 0;
				if (month == 2) {
					if (year % 4 == 0) {
						day = (rand.nextInt(29) +1) ;
					}
					else {
						day = (rand.nextInt(28) +1) ;
					}
				}
				else if (month %2 == 1) {
					day = (rand.nextInt(30) +1) ;
				}
				else {
					day = (rand.nextInt(31) +1) ;
				}
				if (day <10) {
					dayString = "0" + month;
				}
				else {
					dayString = String.valueOf(month);
				}
				String birthdate = year + "-"+monthString+"-"+dayString;
				String address = "Fake Street" + i;
				int sin = rand.nextInt(999999999 - 111111111 + 1) + 111111111;
				
				int voted = rand.nextInt(100);
				String[] parties = {"Liberal Party", "Conservative Party", "New Democratic Party", "Bloc Quebecois", "Green Party" };
				
				Voter voter = new Voter(fname, lname, birthdate, address, sin);
				if (!checkVoterExists(voter.getSin())) {
					addVoter(voter);
					i += 1;
					if (voted < 70) {
						int n = rand.nextInt(5);
						Vote vote = new Vote(voter, parties[n]);
						addVote(vote);
					}
				}
			}
		}
	}
	
	public boolean checkVoterExists(int sin) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		boolean exists = false;
		
		if (session.get(Voter.class, sin) != null) {
			exists = true;
		}
		session.getTransaction().commit();
		session.close();
		
		return exists;
	}
	
	public void addVoter(Voter voter) {
		Session session = sessionFactory.openSession();
		// db access starts with this
		session.beginTransaction();
		
			//add player to db
			session.save(voter);
			
		// db access ends with this
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Voter> getVoters() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Voter");
		List<Voter> voterList = (List<Voter>) query.getResultList();
		
		session.getTransaction().commit();
		session.close();
		
		return voterList;
	}
	
	public Voter getVoter(int sin) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Voter where sin = " + sin);
		List<Voter> voterList = (List<Voter>) query.getResultList();
		
		Voter voter = voterList.get(0);
		
		session.getTransaction().commit();
		session.close();
		
		return voter;
	}
	
	public void addVote(Vote vote) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Voter voter = vote.getVoter();
		voter.setVote(vote);
		
		session.update(voter);
		session.save(vote);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Vote> getVotes(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Vote");
		List<Vote> voteList = (List<Vote>) query.getResultList();
		
		session.getTransaction().commit();
		session.close();
		
		return voteList;
	}

	public void deleteVoter(int sin) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Voter v = (Voter) session.get(Voter.class, sin);
		session.delete(v);
		
		session.getTransaction().commit();
		session.close();
		
	}
}
