package myhealthylife.centric2.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import myhealthylife.centric2.dao.CentricServiceDao;


@Entity
@Table(name="DedicatedSentence")
@NamedQuery(name="DedicatedSentence.findAll", query="SELECT d FROM DedicatedSentence d")
@XmlRootElement(name="dedicatedSentence")
@XmlType(propOrder={"idDedicatedSentence", "idUserOne", "idUserTwo", "idSentence", "sentenceText"})
public class DedicatedSentence implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long idDedicatedSentence;

	@Column(name="userone")
	private Long idUserOne;
	
	@Column(name="usertwo")
	private Long idUserTwo;

	@Column(name="idSentence")
	private Long idSentence;
	
	@Column(name="sentencetext")
	private String sentenceText;
	
	public DedicatedSentence() {
	}


	public long getIdDedicatedSentence() {
		return idDedicatedSentence;
	}


	public void setIdDedicatedSentence(long idDedicatedSentence) {
		this.idDedicatedSentence = idDedicatedSentence;
	}


	public Long getIdUserOne() {
		return idUserOne;
	}


	public void setIdUserOne(Long idUserOne) {
		this.idUserOne = idUserOne;
	}


	public Long getIdUserTwo() {
		return idUserTwo;
	}


	public void setIdUserTwo(Long idUserTwo) {
		this.idUserTwo = idUserTwo;
	}


	public Long getIdSentence() {
		return idSentence;
	}


	public void setIdSentence(Long idSentence) {
		this.idSentence = idSentence;
	}


	public String getSentenceText() {
		return sentenceText;
	}


	public void setSentenceText(String sentenceText) {
		this.sentenceText = sentenceText;
	}


	/* DATABASE OPERATIONS */
	public static DedicatedSentence getDedicatedSentenceById(long dedicatedSentenceId) {
		EntityManager em = CentricServiceDao.instance.createEntityManager();
		DedicatedSentence p = em.find(DedicatedSentence.class, dedicatedSentenceId);
		CentricServiceDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<DedicatedSentence> getAll() {

		System.out.println("--> Initializing Entity manager...");
		EntityManager em = CentricServiceDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
	    List<DedicatedSentence> list = em.createNamedQuery("DedicatedSentence.findAll", DedicatedSentence.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
	    CentricServiceDao.instance.closeConnections(em);
	    return list;
	}
	
	public static DedicatedSentence saveDedicatedSentence(DedicatedSentence p) {
		EntityManager em = CentricServiceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    CentricServiceDao.instance.closeConnections(em);
	    return p;
	}
	
	public static DedicatedSentence updateDedicatedSentence(DedicatedSentence p) {
		EntityManager em = CentricServiceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    CentricServiceDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeDedicatedSentence(DedicatedSentence p) {
		EntityManager em = CentricServiceDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    CentricServiceDao.instance.closeConnections(em);
	}
	
	
}
