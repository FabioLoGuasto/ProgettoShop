/**
 * Author Fabio Lo Guasto
 */
class Articolo{

    constructor(){}


    /**
     * Method "abstract" without implementation.
     * @abstract
     */
    handleClickOutsideTable(event) {}


    /**
     * @abstract
     */
    async loadMethod(){}


    /**
     * @abstract
     */
    search(){}


    /**
     * @abstract
     */
    updateSearchButtonState() {}


    
    /**
     * This method is showed when ther'isn't article with brand or size selected. 
     * This method is called in ArticleFind and ArticleDetail.
     * @param {*} toastID ID of toast
     * @param {*} toastBodyID  ID of body toast
     * @param {*} message message of body toast. It may different in base of situation
     */
    showToast(toastID, toastBodyID, message){
        var IdToast = document.getElementById(toastID);
        var toastBody = document.getElementById(toastBodyID);
        var toastInit = new bootstrap.Toast(IdToast); // inizializzo il toast
        toastBody.textContent = message; 
        toastInit.show();
    }
    

}