
package myhealthylife.nutritionservice.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteFoodTypeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteFoodTypeResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idFoodType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteFoodTypeResponse", propOrder = {
    "idFoodType"
})
public class DeleteFoodTypeResponse {

    protected long idFoodType;

    /**
     * Gets the value of the idFoodType property.
     * 
     */
    public long getIdFoodType() {
        return idFoodType;
    }

    /**
     * Sets the value of the idFoodType property.
     * 
     */
    public void setIdFoodType(long value) {
        this.idFoodType = value;
    }

}