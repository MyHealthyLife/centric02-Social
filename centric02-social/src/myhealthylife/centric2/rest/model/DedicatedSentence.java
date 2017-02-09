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
@XmlType(propOrder={"idDedicatedSentence", "idUserOne", "idUserTwo", "idSentence"})
public class DedicatedSentence implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long idDedicatedSentence;

	@Column(name="userone")
	private Integer idUserOne;
	
	@Column(name="usertwo")
	private Integer idUserTwo;

	@Column(name="sentence")
	private Integer idSentence;
	
	
	public DedicatedSentence() {
	}


	public long getIdDedicatedSentence() {
		return idDedicatedSentence;
	}


	public void setIdDedicatedSentence(long idDedicatedSentence) {
		this.idDedicatedSentence = idDedicatedSentence;
	}


	public Integer getIdUserOne() {
		return idUserOne;
	}


	public void setIdUserOne(Integer idUserOne) {
		this.idUserOne = idUserOne;
	}


	public Integer getIdUserTwo() {
		return idUserTwo;
	}


	public void setIdUserTwo(Integer idUserTwo) {
		this.idUserTwo = idUserTwo;
	}


	public Integer getIdSentence() {
		return idSentence;
	}


	public void setIdSentence(Integer idSentence) {
		this.idSentence = idSentence;
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
