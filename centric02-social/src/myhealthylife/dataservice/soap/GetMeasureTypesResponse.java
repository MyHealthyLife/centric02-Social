
package myhealthylife.dataservice.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getMeasureTypesResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getMeasureTypesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="measureTypes" type="{http://soap.dataservice.myhealthylife/}measureTypeList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMeasureTypesResponse", propOrder = {
    "measureTypes"
})
public class GetMeasureTypesResponse {

    protected MeasureTypeList measureTypes;

    /**
     * Recupera il valore della proprietà measureTypes.
     * 
     * @return
     *     possible object is
     *     {@link MeasureTypeList }
     *     
     */
    public MeasureTypeList getMeasureTypes() {
        return measureTypes;
    }

    /**
     * Imposta il valore della proprietà measureTypes.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureTypeList }
     *     
     */
    public void setMeasureTypes(MeasureTypeList value) {
        this.measureTypes = value;
    }

}
