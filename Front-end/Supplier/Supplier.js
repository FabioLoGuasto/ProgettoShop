/**
 * Author Fabio Lo Guasto
 */
class Supplier{

    /**
     * 
     */    
    constructor(){}
    
    // Const url from ConstSupplier.js
    UrlLocalHost = locHost;
    
    // Const from ConstShop.js
    action = actionsModal;
    
    // Variable for value of table rows    
    selectedSupplierID = 0;
    selectedSupplierCode = 0;
    selectedCompanyName = " ";
    selectedSupplierNation = " ";
    scelta = " ";
    

    /**
     * Method => 
     * 
     */
    getSupplier(){
        
        var table = document.getElementById("table");
    
        var url = this.UrlLocalHost + "supplier/getAllSuppliers";
        console.log(url);
    
        fetch(url)
            .then((response) => response.json())
            .then((data) => {          

                console.log(data);
                this.getTable(data)
    
                /**
                 *  after that the table is popolated, show it modify with style.display
                 *  into file.html, the table have invisibile class that makes invisible the table. Here I remove the invisible class and show the table
                 */ 
                table.classList.remove('invisible');
                
                var insertBtnModal = document.getElementById("insertBtnModal");
                //insertBtnModal.style.display = "inline-block"; 
                insertBtnModal.classList.remove('invisible');
    
                // Deselected row if click outside the table 
                document.addEventListener('click', this.handleClickOutsideTable.bind(this));
        })
            .catch((error) => console.error(error));
    }
    
    
    /**
     * Create a table based on the value provided
     * @param {*} value 
     */
    getTable(value){
        var table = document.getElementById("table").getElementsByTagName('tbody')[0];
        
        while(table.rows.length > 0){
            table.deleteRow(0); 
        } 
    
        value.forEach(element => {
    
            var newRow = table.insertRow(table.length);
    
            // Element.___ è l'attributo della DtoGetSearchCNP.class sul backend
            // newRow.dataset.negID = element.id;
            //var c1 = newRow.insertCell(0);
            //c1.innerHTML = element.idUnivocoNegozio; 
            var c0 = newRow.insertCell(0); // Selected the cella
            c0.innerHTML = element.supplierCode; // Insert into the cella
            var c1 = newRow.insertCell(1);
            c1.innerHTML = element.companyName;
            var c2 = newRow.insertCell(2);
            c2.innerHTML = element.nation;          
    
            // Parte di selezione riga 
            newRow.addEventListener('click', function() {
                    
                // Here the color of the selected row is managed
                var selectedRow = document.querySelector('.table tbody .table-active');
                if (selectedRow) {
                    selectedRow.classList.remove('table-active');
                    selectedRow.style.backgroundColor = 'white';
                }
                newRow.classList.add('table-active');
                newRow.style.backgroundColor = '#a7a7a7';
    
                this.selectedSupplierID = element.idFornitore;
                this.selectedSupplierCode = element.supplierCode;
                this.selectedCompanyName = element.companyName;
                this.selectedSupplierNation = element.nation;
                
    
                console.log('ID supplier:', this.selectedSupplierID);
                console.log('Codice fornitore:', this.selectedSupplierCode);
                console.log('Nome fornitore:', this.selectedCompanyName);
                console.log('Nazione:', this.selectedSupplierNation);
                
    
                // Alla selezione della riga  mostro i pulsanti update & delete 
                var updateBtnModal = document.getElementById("updateBtnModal");
                updateBtnModal.style.display = "inline-block";
    
                var deleteBtnModal = document.getElementById("deleteBtnModal");
                deleteBtnModal.style.display = "inline-block";
    
              }.bind(this));
    
              
        });
    }
    
    
    /**
         * Deselect row if click is outside the table
         * @param {*} event 
         */
    handleClickOutsideTable(event) {
        var table = document.getElementById("table");
        var selectedRow = table.querySelector('.table-active');
    
        if (selectedRow && !table.contains(event.target)) {
            selectedRow.classList.remove('table-active');
            selectedRow.style.backgroundColor = 'white';
        }
    }
    
    
    /**
     * This metod show the value of the selected row for update and delete modal.
     * For insert, reset the input form, for insert new record.
     * @param {*} value : This value can be differente in base of enum 
     */
    show(value) {
    
        // check on valu of selected rows 
        console.log(this.selectedSupplierID);
        console.log(this.selectedSupplierCode);
        console.log(this.selectedCompanyName);
        console.log(this.selectedSupplierNation);
    
        // Seleziono i campi di input che voglio riempire 
        // var modID = document.forms.modal.ShopID;
        var modSupplCode = document.getElementById("supplCode");
        var modCompName = document.getElementById("compName");
        var modNation = document.getElementById("nation");
    
        // Variables to set the button name and the modal title dynamically
        var actionButton = document.getElementById("action");
        var modalTitle = document.getElementById("modalTitle");
        
    
        switch (value) {
            case this.action.INSERT:
    
                actionButton.textContent = "INSERT";
                modalTitle.textContent = "INSERT NEW SUPPLIER";
    
                // Insert value of the input field
                modSupplCode.value = "";
                modCompName.value = "";
                modNation.value = "";
                this.scelta = this.action.INSERT;
                break;
    
            case this.action.UPDATE:
    
                actionButton.textContent = "UPDATE";
                modalTitle.textContent = "UPDATE THE SUPPLIER";
    
                // Insert value of the input field
                modSupplCode.value = this.selectedSupplierCode;
                modCompName.value = this.selectedCompanyName;
                modNation.value = this.selectedSupplierNation;
                this.scelta = this.action.UPDATE;
                break;
    
            case this.action.DELETE:
    
                actionButton.textContent = "DELETE";
                modalTitle.textContent = "DELETE THE SUPPLIER";
    
                // Insert value of the input field
                modSupplCode.value = this.selectedSupplierCode;
                modCompName.value = this.selectedCompanyName;
                modNation.value = this.selectedSupplierNation;
                this.scelta = this.action.DELETE;
                break;
        }
    
    }
    
    
    /**
     * This method call other method in base of value of var scelta.
     */
    doAction() {
    
        switch (this.scelta) {
            case this.action.INSERT:
                this.newSupplier();
                break;
            case this.action.UPDATE:
                this.UpdateSupplier();
                break;
            case this.action.DELETE:
                this.deleteSupplier();
                break;
        }
    }
    
    
    /**
     * Method => 
     * Insert a new supplier
     */
    async newSupplier(){
        
        var newSupplier = {
            "supplierCode": document.getElementById("supplCode").value,
            "companyName": document.getElementById("compName").value,
            "nation": document.getElementById("nation").value
        };
    
        try{
            const res = await fetch(this.UrlLocalHost + "supplier/insertSupplier",{
                method : 'POST',
                headers : {
                    'Content-Type':'application/json'
                },
                body : JSON.stringify(newSupplier)
            });
    
            if (res.ok) {
                // Mostra un popup per notificare l'avvenuto inserimento
                alert('Inserimento avvenuto con successo!');
            } else {
                console.error('Errore durante l\'inserimento del record.');
            }
    
        }catch (e){
            console.log("Si è verifica un errore !" + e);
        }
    
        // Makes the new entry available
        this.getSupplier();
    
    }
    
    
    /**
     * Method => 
     * update of record select trought modal update
     */
    async UpdateSupplier(){
        
        // Prendo i valori dai campi di input tramite l'ID
        var elem1 = this.selectedSupplierID;
        var elem2 = document.getElementById("supplCode").value;
        var elem3 = document.getElementById("compName").value;
        var elem4 = document.getElementById("nation").value;
    
        console.log(elem1);
        console.log(elem2);
        console.log(elem3);
        console.log(elem4);
    
        var updSupplModal = { // Creo il JSON
            "idFornitore": elem1,
            "supplierCode": elem2,
            "companyName": elem3,
            "nation": elem4
        };
    
        try{
    
            const res = await fetch(this.UrlLocalHost + "supplier/updateSupplierById/" + encodeURIComponent(elem1),{
                method : 'PUT',
                headers : {
                    'Content-Type':'application/json'
                },
                body : JSON.stringify(updSupplModal) // converte un oggetto JavaScript in una stringa JSON.
            });
    
    
            if (res.ok) {
                // Mostra un popup per notificare l'avvenuto inserimento
                alert('Aggiornamento avvenuto con successo!');
            } else {
                console.error('Errore durante l\'inserimento del record.');
            }
    
        }catch (e){
            console.log("Si è verifica un errore !" + e);
        }
    }
    
    
    /**
     * Method => 
     * This method delete a supplier
     */
    async deleteSupplier(){
        
        // Prendo i valori dai campi di input tramite l'ID
        var elem1 = this.selectedSupplierID;
        var elem2 = document.getElementById("supplCode").value;
        var elem3 = document.getElementById("compName").value;
        var elem4 = document.getElementById("nation").value;
    
        console.log(elem1);
        console.log(elem2);
        console.log(elem3);
        console.log(elem4);
    
        try{
    
            const res = await fetch(this.UrlLocalHost + "supplier/deleteSupplierById/" + encodeURIComponent(elem1),{
                method : 'DELETE'});
    
            if (res.ok) {
                // Mostra un popup per notificare l'avvenuta cancellazione
                alert('cancellazione avvenuto con successo!');  
            } else {
                console.error('Errore durante l\'inserimento del record.');
            }
    
        } catch(e){
            console.log("Si è verifica un errore !" + e);
        }
    
    }
    
    
    
    }