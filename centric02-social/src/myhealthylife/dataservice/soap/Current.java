
package myhealthylife.dataservice.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for current_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="current_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="city" type="{http://soap.dataservice.myhealthylife/}city_type"/>
 *         &lt;element name="temperature" type="{http://soap.dataservice.myhealthylife/}temperature_type"/>
 *         &lt;element name="humidity" type="{http://soap.dataservice.myhealthylife/}umidity_type"/>
 *         &lt;element name="pressure" type="{http://soap.dataservice.myhealthylife/}pressure_type"/>
 *         &lt;element name="wind" type="{http://soap.dataservice.myhealthylife/}wind_type"/>
 *         &lt;element name="clouds" type="{http://soap.dataservice.myhealthylife/}clouds_type"/>
 *         &lt;element name="visibility" type="{http://soap.dataservice.myhealthylife/}visibility_type"/>
 *         &lt;element name="precipitation" type="{http://soap.dataservice.myhealthylife/}precipitation_type"/>
 *         &lt;element name="weather" type="{http://soap.dataservice.myhealthylife/}weather_type"/>
 *         &lt;element name="lastupdate" type="{http://soap.dataservice.myhealthylife/}lastupdate_type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "current_type", propOrder = {
    "city",
    "temperature",
    "humidity",
    "pressure",
    "wind",
    "clouds",
    "visibility",
    "precipitation",
    "weather",
    "lastupdate"
})
@XmlRootElement(name = "current")
public class Current {

    @XmlElement(required = true)
    protected CityType city;
    @XmlElement(required = true)
    protected TemperatureType temperature;
    @XmlElement(required = true)
    protected UmidityType humidity;
    @XmlElement(required = true)
    protected PressureType pressure;
    @XmlElement(required = true)
    protected WindType wind;
    @XmlElement(required = true)
    protected CloudsType clouds;
    @XmlElement(required = true)
    protected VisibilityType visibility;
    @XmlElement(required = true)
    protected PrecipitationType precipitation;
    @XmlElement(required = true)
    protected WeatherType weather;
    @XmlElement(required = true)
    protected LastupdateType lastupdate;

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link CityType }
     *     
     */
    public CityType getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link CityType }
     *     
     */
    public void setCity(CityType value) {
        this.city = value;
    }

    /**
     * Gets the value of the temperature property.
     * 
     * @return
     *     possible object is
     *     {@link TemperatureType }
     *     
     */
    public TemperatureType getTemperature() {
        return temperature;
    }

    /**
     * Sets the value of the temperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link TemperatureType }
     *     
     */
    public void setTemperature(TemperatureType value) {
        this.temperature = value;
    }

    /**
     * Gets the value of the humidity property.
     * 
     * @return
     *     possible object is
     *     {@link UmidityType }
     *     
     */
    public UmidityType getHumidity() {
        return humidity;
    }

    /**
     * Sets the value of the humidity property.
     * 
     * @param value
     *     allowed object is
     *     {@link UmidityType }
     *     
     */
    public void setHumidity(UmidityType value) {
        this.humidity = value;
    }

    /**
     * Gets the value of the pressure property.
     * 
     * @return
     *     possible object is
     *     {@link PressureType }
     *     
     */
    public PressureType getPressure() {
        return pressure;
    }

    /**
     * Sets the value of the pressure property.
     * 
     * @param value
     *     allowed object is
     *     {@link PressureType }
     *     
     */
    public void setPressure(PressureType value) {
        this.pressure = value;
    }

    /**
     * Gets the value of the wind property.
     * 
     * @return
     *     possible object is
     *     {@link WindType }
     *     
     */
    public WindType getWind() {
        return wind;
    }

    /**
     * Sets the value of the wind property.
     * 
     * @param value
     *     allowed object is
     *     {@link WindType }
     *     
     */
    public void setWind(WindType value) {
        this.wind = value;
    }

    /**
     * Gets the value of the clouds property.
     * 
     * @return
     *     possible object is
     *     {@link CloudsType }
     *     
     */
    public CloudsType getClouds() {
        return clouds;
    }

    /**
     * Sets the value of the clouds property.
     * 
     * @param value
     *     allowed object is
     *     {@link CloudsType }
     *     
     */
    public void setClouds(CloudsType value) {
        this.clouds = value;
    }

    /**
     * Gets the value of the visibility property.
     * 
     * @return
     *     possible object is
     *     {@link VisibilityType }
     *     
     */
    public VisibilityType getVisibility() {
        return visibility;
    }

    /**
     * Sets the value of the visibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link VisibilityType }
     *     
     */
    public void setVisibility(VisibilityType value) {
        this.visibility = value;
    }

    /**
     * Gets the value of the precipitation property.
     * 
     * @return
     *     possible object is
     *     {@link PrecipitationType }
     *     
     */
    public PrecipitationType getPrecipitation() {
        return precipitation;
    }

    /**
     * Sets the value of the precipitation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrecipitationType }
     *     
     */
    public void setPrecipitation(PrecipitationType value) {
        this.precipitation = value;
    }

    /**
     * Gets the value of the weather property.
     * 
     * @return
     *     possible object is
     *     {@link WeatherType }
     *     
     */
    public WeatherType getWeather() {
        return weather;
    }

    /**
     * Sets the value of the weather property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeatherType }
     *     
     */
    public void setWeather(WeatherType value) {
        this.weather = value;
    }

    /**
     * Gets the value of the lastupdate property.
     * 
     * @return
     *     possible object is
     *     {@link LastupdateType }
     *     
     */
    public LastupdateType getLastupdate() {
        return lastupdate;
    }

    /**
     * Sets the value of the lastupdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link LastupdateType }
     *     
     */
    public void setLastupdate(LastupdateType value) {
        this.lastupdate = value;
    }

}
