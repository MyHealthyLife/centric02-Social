package myhealthylife.centric2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import myhealthylife.centric2.rest.model.DedicatedSentence;

public enum CentricServiceDao {
	instance;
	private EntityManagerFactory emf;
	
	private CentricServiceDao() {
		if (emf!=null) {
			emf.close();
		}
		emf = Persistence.createEntityManagerFactory("centric02-social");
	}
	
	public EntityManager createEntityManager() {
		try {
			return emf.createEntityManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;    
	}

	public void closeConnections(EntityManager em) {
		em.close();
	}

	public EntityTransaction getTransaction(EntityManager em) {
		return em.getTransaction();
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	// Sentence related operations could also directly go into the "Sentence" Model
	public static DedicatedSentence getDedicatedSentenceById(Long sentenceId) {
		EntityManager em = instance.createEntityManager();
		DedicatedSentence p = em.find(DedicatedSentence.class, sentenceId);
		instance.closeConnections(em);
		return p;
	}
	
	public static List<DedicatedSentence> getAll() {
		EntityManager em = instance.createEntityManager();
	    List<DedicatedSentence> list = em.createNamedQuery("DedicatedSentence.findAll", DedicatedSentence.class).getResultList();
	    instance.closeConnections(em);
	    return list;
	}
	

}