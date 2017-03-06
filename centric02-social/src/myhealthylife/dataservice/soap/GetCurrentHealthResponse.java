
package myhealthylife.dataservice.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getCurrentHealthResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getCurrentHealthResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="currentHealth" type="{http://soap.dataservice.myhealthylife/}currentHealth" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCurrentHealthResponse", propOrder = {
    "currentHealth"
})
public class GetCurrentHealthResponse {

    protected CurrentHealth currentHealth;

    /**
     * Recupera il valore della proprietà currentHealth.
     * 
     * @return
     *     possible object is
     *     {@link CurrentHealth }
     *     
     */
    public CurrentHealth getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Imposta il valore della proprietà currentHealth.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrentHealth }
     *     
     */
    public void setCurrentHealth(CurrentHealth value) {
        this.currentHealth = value;
    }

}
