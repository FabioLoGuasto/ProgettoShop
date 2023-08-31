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


    /** ???
    showToast(message){
        var toastEl = document.getElementById('toastID');
        var t = document.getElementById('toastBody');
        var toast = new bootstrap.Toast(toastEl); // inizializzo il toast
        t.textContent = message; 
        toast.show();
    }
    */

}