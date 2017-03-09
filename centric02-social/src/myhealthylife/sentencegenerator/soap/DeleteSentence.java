
package myhealthylife.sentencegenerator.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per deleteSentence complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="deleteSentence">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sentenceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteSentence", propOrder = {
    "sentenceId"
})
public class DeleteSentence {

    protected long sentenceId;

    /**
     * Recupera il valore della proprietÓ sentenceId.
     * 
     */
    public long getSentenceId() {
        return sentenceId;
    }

    /**
     * Imposta il valore della proprietÓ sentenceId.
     * 
     */
    public void setSentenceId(long value) {
        this.sentenceId = value;
    }

}
