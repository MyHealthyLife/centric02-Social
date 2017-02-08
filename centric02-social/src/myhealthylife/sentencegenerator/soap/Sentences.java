
package myhealthylife.sentencegenerator.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Sentences", targetNamespace = "http://soap.sentencegenerator.myhealthylife/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Sentences {


    /**
     * 
     * @return
     *     returns myhealthylife.sentencegenerator.soap.SentenceList
     */
    @WebMethod
    @WebResult(name = "sentenceList", targetNamespace = "")
    @RequestWrapper(localName = "readSentenceList", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadSentenceList")
    @ResponseWrapper(localName = "readSentenceListResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadSentenceListResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/readSentenceListRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/readSentenceListResponse")
    public SentenceList readSentenceList();

    /**
     * 
     * @param sentenceId
     * @return
     *     returns myhealthylife.sentencegenerator.soap.Sentence
     */
    @WebMethod
    @WebResult(name = "sentence", targetNamespace = "")
    @RequestWrapper(localName = "readSentence", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadSentence")
    @ResponseWrapper(localName = "readSentenceResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadSentenceResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/readSentenceRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/readSentenceResponse")
    public Sentence readSentence(
        @WebParam(name = "sentenceId", targetNamespace = "")
        long sentenceId);

    /**
     * 
     * @param sentence
     */
    @WebMethod
    @RequestWrapper(localName = "createSentence", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.CreateSentence")
    @ResponseWrapper(localName = "createSentenceResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.CreateSentenceResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/createSentenceRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/createSentenceResponse")
    public void createSentence(
        @WebParam(name = "sentence", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Sentence> sentence);

    /**
     * 
     * @param sentenceId
     * @return
     *     returns long
     */
    @WebMethod
    @WebResult(name = "idSentence", targetNamespace = "")
    @RequestWrapper(localName = "deleteSentence", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.DeleteSentence")
    @ResponseWrapper(localName = "deleteSentenceResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.DeleteSentenceResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/deleteSentenceRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/deleteSentenceResponse")
    public long deleteSentence(
        @WebParam(name = "sentenceId", targetNamespace = "")
        long sentenceId);

    /**
     * 
     * @param sentence
     */
    @WebMethod
    @RequestWrapper(localName = "updateSentence", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.UpdateSentence")
    @ResponseWrapper(localName = "updateSentenceResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.UpdateSentenceResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/updateSentenceRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/updateSentenceResponse")
    public void updateSentence(
        @WebParam(name = "sentence", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<Sentence> sentence);

    /**
     * 
     * @return
     *     returns myhealthylife.sentencegenerator.soap.Sentence
     */
    @WebMethod
    @WebResult(name = "sentence", targetNamespace = "")
    @RequestWrapper(localName = "readRandomSentence", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadRandomSentence")
    @ResponseWrapper(localName = "readRandomSentenceResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadRandomSentenceResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/readRandomSentenceRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/readRandomSentenceResponse")
    public Sentence readRandomSentence();

    /**
     * 
     * @param sentenceType
     * @return
     *     returns myhealthylife.sentencegenerator.soap.Sentence
     */
    @WebMethod
    @WebResult(name = "sentence", targetNamespace = "")
    @RequestWrapper(localName = "readRandomSentenceByType", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadRandomSentenceByType")
    @ResponseWrapper(localName = "readRandomSentenceByTypeResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadRandomSentenceByTypeResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/readRandomSentenceByTypeRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/readRandomSentenceByTypeResponse")
    public Sentence readRandomSentenceByType(
        @WebParam(name = "sentenceType", targetNamespace = "")
        String sentenceType);

    /**
     * 
     * @param motive
     * @param sentenceType
     * @return
     *     returns myhealthylife.sentencegenerator.soap.Sentence
     */
    @WebMethod
    @WebResult(name = "sentence", targetNamespace = "")
    @RequestWrapper(localName = "readRandomSentenceByTypeAndTrend", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadRandomSentenceByTypeAndTrend")
    @ResponseWrapper(localName = "readRandomSentenceByTypeAndTrendResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadRandomSentenceByTypeAndTrendResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/readRandomSentenceByTypeAndTrendRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/readRandomSentenceByTypeAndTrendResponse")
    public Sentence readRandomSentenceByTypeAndTrend(
        @WebParam(name = "sentenceType", targetNamespace = "")
        String sentenceType,
        @WebParam(name = "motive", targetNamespace = "")
        Boolean motive);

    /**
     * 
     * @return
     *     returns myhealthylife.sentencegenerator.soap.SentenceTypeList
     */
    @WebMethod
    @WebResult(name = "sentenceTypeList", targetNamespace = "")
    @RequestWrapper(localName = "readSentenceTypeList", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadSentenceTypeList")
    @ResponseWrapper(localName = "readSentenceTypeListResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.ReadSentenceTypeListResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/readSentenceTypeListRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/readSentenceTypeListResponse")
    public SentenceTypeList readSentenceTypeList();

    /**
     * 
     * @param motive
     * @param typeName
     * @return
     *     returns myhealthylife.sentencegenerator.soap.SentenceType
     */
    @WebMethod
    @WebResult(name = "sentenceType", targetNamespace = "")
    @RequestWrapper(localName = "createSentenceType", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.CreateSentenceType")
    @ResponseWrapper(localName = "createSentenceTypeResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.CreateSentenceTypeResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/createSentenceTypeRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/createSentenceTypeResponse")
    public SentenceType createSentenceType(
        @WebParam(name = "typeName", targetNamespace = "")
        String typeName,
        @WebParam(name = "motive", targetNamespace = "")
        Boolean motive);

    /**
     * 
     * @param sentenceId
     * @param typeId
     * @return
     *     returns myhealthylife.sentencegenerator.soap.Sentence
     */
    @WebMethod
    @WebResult(name = "sentence", targetNamespace = "")
    @RequestWrapper(localName = "setSentenceType", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.SetSentenceType")
    @ResponseWrapper(localName = "setSentenceTypeResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.SetSentenceTypeResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/setSentenceTypeRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/setSentenceTypeResponse")
    public Sentence setSentenceType(
        @WebParam(name = "sentenceId", targetNamespace = "")
        long sentenceId,
        @WebParam(name = "typeId", targetNamespace = "")
        long typeId);

    /**
     * 
     * @param typeId
     * @return
     *     returns long
     */
    @WebMethod
    @WebResult(name = "idSentenceType", targetNamespace = "")
    @RequestWrapper(localName = "deleteSentenceType", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.DeleteSentenceType")
    @ResponseWrapper(localName = "deleteSentenceTypeResponse", targetNamespace = "http://soap.sentencegenerator.myhealthylife/", className = "myhealthylife.sentencegenerator.soap.DeleteSentenceTypeResponse")
    @Action(input = "http://soap.sentencegenerator.myhealthylife/Sentences/deleteSentenceTypeRequest", output = "http://soap.sentencegenerator.myhealthylife/Sentences/deleteSentenceTypeResponse")
    public long deleteSentenceType(
        @WebParam(name = "typeId", targetNamespace = "")
        long typeId);

}
