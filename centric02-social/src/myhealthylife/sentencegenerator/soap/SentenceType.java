
package myhealthylife.sentencegenerator.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per sentenceType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="sentenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idSentenceType" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="privateType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sentenceType", propOrder = {
    "idSentenceType",
    "name",
    "motive",
    "privateType"
})
@XmlRootElement(name = "sentenceType")
public class SentenceType {

    protected Long idSentenceType;
    protected String name;
    protected Boolean motive;
    protected boolean privateType;

    /**
     * Recupera il valore della propriet� idSentenceType.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdSentenceType() {
        return idSentenceType;
    }

    /**
     * Imposta il valore della propriet� idSentenceType.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdSentenceType(Long value) {
        this.idSentenceType = value;
    }

    /**
     * Recupera il valore della propriet� name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della propriet� name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della propriet� motive.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMotive() {
        return motive;
    }

    /**
     * Imposta il valore della propriet� motive.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMotive(Boolean value) {
        this.motive = value;
    }

    /**
     * Recupera il valore della propriet� privateType.
     * 
     */
    public boolean isPrivateType() {
        return privateType;
    }

    /**
     * Imposta il valore della propriet� privateType.
     * 
     */
    public void setPrivateType(boolean value) {
        this.privateType = value;
    }

}
