
package myhealthylife.dataservice.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wind_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wind_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="speed" type="{http://soap.dataservice.myhealthylife/}speed_type"/>
 *         &lt;element name="gusts" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direction" type="{http://soap.dataservice.myhealthylife/}direction_type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wind_type", propOrder = {
    "speed",
    "gusts",
    "direction"
})
public class WindType {

    @XmlElement(required = true)
    protected SpeedType speed;
    @XmlElement(required = true)
    protected String gusts;
    @XmlElement(required = true)
    protected DirectionType direction;

    /**
     * Gets the value of the speed property.
     * 
     * @return
     *     possible object is
     *     {@link SpeedType }
     *     
     */
    public SpeedType getSpeed() {
        return speed;
    }

    /**
     * Sets the value of the speed property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpeedType }
     *     
     */
    public void setSpeed(SpeedType value) {
        this.speed = value;
    }

    /**
     * Gets the value of the gusts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGusts() {
        return gusts;
    }

    /**
     * Sets the value of the gusts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGusts(String value) {
        this.gusts = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link DirectionType }
     *     
     */
    public DirectionType getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectionType }
     *     
     */
    public void setDirection(DirectionType value) {
        this.direction = value;
    }

}
