
package myhealthylife.nutritionservice.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateFood complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateFood">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="food" type="{http://soap.nutritionservice.myhealthylife/}food" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateFood", propOrder = {
    "food"
})
@XmlRootElement(name = "updateFood")
public class UpdateFood {

    protected Food food;

    /**
     * Gets the value of the food property.
     * 
     * @return
     *     possible object is
     *     {@link Food }
     *     
     */
    public Food getFood() {
        return food;
    }

    /**
     * Sets the value of the food property.
     * 
     * @param value
     *     allowed object is
     *     {@link Food }
     *     
     */
    public void setFood(Food value) {
        this.food = value;
    }

}
