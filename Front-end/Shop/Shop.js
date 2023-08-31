/**
 * Author Fabio Lo Guasto
 */
class Shop{

/**
 * When the page is rendered, 3 API are called
 * readCityList
 * readNameShopList
 * readProvinceList
 */    
constructor(){
    this.loadMethod();
}

// Const url from ConstShop.js
UrlLocalHost = locHost;

// Const from ConstShop.js
action = actionsModal;

// Variable for value of table rows    
selectedNegID = 0;
selectedShopNumber = 0;
selectedBranchName = " ";
selectedBranchLocality = " ";
selectedCivicNumber = 0;
selectedCity = " ";
selectedProvince = " ";
selectedPostalCode = 0;
selectedNation = " ";
scelta = " ";

// Variables for loadMethod() 
loadCity = " ";
loadNameShop = " ";
loadProvince = " ";

// Variables for toast
cityShop = document.getElementById('cityShop'); 
toastEl = document.getElementById('toastID');
toastBody = document.getElementById('toastBody');

/**
 *  Make 3 asynchronous calls to page rendering
 */
async loadMethod(){
    try{
        const rsResponses = await Promise.all([ 
        fetch(this.UrlLocalHost + "shop/getCity"),
        fetch(this.UrlLocalHost + "shop/getNamesShop"),
        fetch(this.UrlLocalHost + "shop/getprovinces")
        ]);
        
        this.loadCity = await rsResponses[0].json();
        this.loadNameShop = await rsResponses[1].json();
        this.loadProvince = await rsResponses[2].json();

        this.readCityList(this.loadCity);
        this.readNameShopList(this.loadNameShop);
        this.readProvinceList(this.loadProvince);

        //Call event for activates or deactivates the search button
        const selectCity = document.getElementById('cityShop');
        selectCity.addEventListener('input', this.updateSearchButtonState.bind(this));

    }catch(e){
        console.log("si è verificato un errore!");
    }
}


/**
 * Method => getCity() => DtoGetCityShop.class 
 * Populate a drop-down list with the cities provided in the `multiCity` parameter.
 * @param {*} multiCity 
 */
readCityList(multiCity) {
    console.log(multiCity);

    const dropdown = document.getElementById('cityShop');

    multiCity.forEach(elem => {
    console.log(elem);
        const option = document.createElement('option'); 
        option.value = elem.city; 
        option.text = elem.city; 
        dropdown.appendChild(option);
    });
}

/**
 * Method => getNamesShop() => DtoGetNameShop.class 
 * Populate a drop-down list with the names provided in the `multiName` parameter.
 * @param {*} multiName 
 */
readNameShopList(multiName) {
    console.log(multiName);

    const dropdown = document.getElementById('shopName');

    multiName.forEach(elem => {
    console.log(elem);
        const option = document.createElement('option'); 
        option.value = elem.name; 
        option.text = elem.name; 
        dropdown.appendChild(option);
    });
}

/**
 * Method => getprovinces() => DtoGetProvinceShop.class 
 * Populate a drop-down list with the provinces provided in the `multiProvince` parameter.
 * @param {*} multiProvince 
 */
readProvinceList(multiProvince) {
    console.log(multiProvince);

    const dropdown = document.getElementById('shopProvince');

    multiProvince.forEach(elem => {
    console.log(elem);
        const option = document.createElement('option'); 
        option.value = elem.province; 
        option.text = elem.province; 
        dropdown.appendChild(option);
    });
}


/**
 * Method => (getCityNameProvince) DtoGetSearchCNP.class 
 * Search for shops based on the parameters provided
 */
search(){
    
    var toast = new bootstrap.Toast(this.toastEl); // inizializzo il toast

    // Recupera i valori dal form
    var table = document.getElementById("table");
    var city = document.getElementById('cityShop').value;
    var name = document.getElementById('shopName').value;
    var province = document.getElementById('shopProvince').value;

    var url = this.UrlLocalHost + "shop/getCityNameProvince?city=" + encodeURIComponent(city);
    console.log(url);

    if(city == ""){
        this.cityShop.style.border = '1px solid red'; // The border is red if city == "" 
        this.toastBody.textContent = "FIELD CITY IS MANDATORY !";
        toast.show();
    }

    if(name !== ""){
        url = url + "&nome=" + encodeURIComponent(name);
        console.log(url);
    }

    if(province !== ""){
        url = url + "&province=" + encodeURIComponent(province);
        console.log(url);
    }

    fetch(url)
        .then((response) => response.json())
        .then((data) => {          

            // If there are no shops, show toast with dynamic argument
            if(data.length === 0){
                console.log("vuoto");
                this.toastBody.textContent = "THERE ARE NO STORES IN THIS CITY !"; 
                toast.show();
                table.classList.add('invisible'); // hide table if it's already active
                return // the program stops and the table is not shown
            }
            console.log(data);
            this.getTable(data)

            /**
             *  fter that the table is popolated, show it modify with style.display
             *  into file.html, the table have invisibile class that makes invisible the table. Here I remove the invisible class and show the table
             */ 
            table.classList.remove('invisible');
            
            var insertBtnModal = document.getElementById("insertBtnModal");
            //insertBtnModal.style.display = "inline-block"; 
            insertBtnModal.classList.remove('invisible');

            // Il colore del bordo torna allo stato originale
            var cityShop = document.getElementById('cityShop');
            cityShop.style.border = '';
            
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
        c0.innerHTML = element.numShop; // Insert into the cella
        var c1 = newRow.insertCell(1);
        c1.innerHTML = element.nameShop;
        var c2 = newRow.insertCell(2);
        c2.innerHTML = element.addressShop;
        var c3 = newRow.insertCell(3);
        c3.innerHTML = element.civic;
        var c4 = newRow.insertCell(4);
        c4.innerHTML = element.city;
        var c5 = newRow.insertCell(5);
        c5.innerHTML = element.province;
        var c6 = newRow.insertCell(6);
        c6.innerHTML = element.postalCode;
        var c7 = newRow.insertCell(7);
        c7.innerHTML = element.nation;

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

            this.selectedNegID = element.id;
            this.selectedShopNumber = element.numShop;
            this.selectedBranchName = element.nameShop;
            this.selectedBranchLocality = element.addressShop;
            this.selectedCivicNumber = element.civic;
            this.selectedCity = element.city;
            this.selectedProvince = element.province;
            this.selectedPostalCode = element.postalCode;
            this.selectedNation = element.nation;

            console.log('ID negozio:', this.selectedNegID);
            console.log('Numero negozio:', this.selectedShopNumber);
            console.log('Nome filiale:', this.selectedBranchName);
            console.log('Località filiale:', this.selectedBranchLocality);
            console.log('Numero Civico:', this.selectedCivicNumber);
            console.log('Citta:', this.selectedCity);
            console.log('Provincia:', this.selectedProvince);
            console.log('Codice postale:', this.selectedPostalCode);
            console.log('Nazione:', this.selectedNation);

            // Alla selezione della riga  mostro i pulsanti update & delete 
            var updateBtnModal = document.getElementById("updateBtnModal");
            updateBtnModal.style.display = "inline-block";

            var deleteBtnModal = document.getElementById("deleteBtnModal");
            deleteBtnModal.style.display = "inline-block";

          }.bind(this));

          
    });
}


  /**
     * This event activates or deactivates the search button
     * This event is called in loadMethod()
     */
  updateSearchButtonState() {
    const selectCity = document.getElementById('cityShop');
    const btnSearch = document.getElementById('btnSearch');

    // The button is disabled if selectCity is empty
    if(selectCity.value == ''){
        btnSearch.disabled = true;
    }

    // The button is active if selectCity are filled in
    if(selectCity.value !== ''){
        btnSearch.disabled = false;
    }

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
    console.log(this.selectedNegID);
    console.log(this.selectedShopNumber);
    console.log(this.selectedBranchName);
    console.log(this.selectedBranchLocality);
    console.log(this.selectedCivicNumber);
    console.log(this.selectedCity);
    console.log(this.selectedProvince);
    console.log(this.selectedPostalCode);
    console.log(this.selectedNation);

    // Seleziono i campi di input che voglio riempire 
    // var modID = document.forms.modal.ShopID;
    var modNameS = document.getElementById("ShopN");
    var modBranchN = document.getElementById("BranchN");
    var modBranchL = document.getElementById("BranchL");
    var modCivivNumber = document.getElementById("CivicN");
    var modCity = document.getElementById("City");
    var modProvince = document.getElementById("Prov");
    var modPostalCode = document.getElementById("PostalC");
    var modNation = document.getElementById("Nation");

    // Variables to set the button name and the modal title dynamically
    var actionButton = document.getElementById("action");
    var modalTitle = document.getElementById("modalTitle");
    

    switch (value) {
        case this.action.INSERT:

            actionButton.textContent = "INSERT";
            modalTitle.textContent = "INSERT NEW SHOP";

            // Insert value of the input field
            modNameS.value = "";
            modBranchN.value = "";
            modBranchL.value = "";
            modCivivNumber.value = "";
            modCity.value = "";
            modProvince.value = "";
            modPostalCode.value = "";
            modNation.value = "";
            this.scelta = this.action.INSERT;
            break;

        case this.action.UPDATE:

            actionButton.textContent = "UPDATE";
            modalTitle.textContent = "UPDATE THE SHOP";

            // Insert value of the input field
            modNameS.value = this.selectedShopNumber;
            modBranchN.value = this.selectedBranchName;
            modBranchL.value = this.selectedBranchLocality;
            modCivivNumber.value = this.selectedCivicNumber;
            modCity.value = this.selectedCity;
            modProvince.value = this.selectedProvince;
            modPostalCode.value = this.selectedPostalCode;
            modNation.value = this.selectedNation;
            this.scelta = this.action.UPDATE;
            break;

        case this.action.DELETE:

            actionButton.textContent = "DELETE";
            modalTitle.textContent = "DELETE THE SHOP";

            // Insert value of the input field
            modNameS.value = this.selectedShopNumber;
            modBranchN.value = this.selectedBranchName;
            modBranchL.value = this.selectedBranchLocality;
            modCivivNumber.value = this.selectedCivicNumber;
            modCity.value = this.selectedCity;
            modProvince.value = this.selectedProvince;
            modPostalCode.value = this.selectedPostalCode;
            modNation.value = this.selectedNation;
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
            this.newShop();
            break;
        case this.action.UPDATE:
            this.UpdateShop();
            break;
        case this.action.DELETE:
            this.deleteShop();
            break;
    }
}


/**
 * Method => (insertShop)
 * Insert a new shop
 */
async newShop(){
    
    var newShop = {
        "shopNumber": document.getElementById("ShopN").value,
        "branchName": document.getElementById("BranchN").value,
        "branchLocality": document.getElementById("BranchL").value,
        "civicNumber": document.getElementById("CivicN").value,
        "city": document.getElementById("City").value,
        "province": document.getElementById("Prov").value,
        "postalCode": document.getElementById("PostalC").value,
        "nation": document.getElementById("Nation").value
    };

    try{
        const res = await fetch(this.UrlLocalHost + "shop/insertShop",{
            method : 'POST',
            headers : {
                'Content-Type':'application/json'
            },
            body : JSON.stringify(newShop)
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
    this.loadMethod();

}


/**
 * Method => (updateShopById) 
 * update of record select trought modal update
 */
async UpdateShop(){
    
    // Prendo i valori dai campi di input tramite l'ID
    var elem1 = this.selectedNegID;
    var elem2 = document.getElementById("ShopN").value;
    var elem3 = document.getElementById("BranchN").value;
    var elem4 = document.getElementById("BranchL").value;
    var elem5 = document.getElementById("CivicN").value;
    var elem6 = document.getElementById("City").value;
    var elem7 = document.getElementById("Prov").value;
    var elem8 = document.getElementById("PostalC").value;
    var elem9 = document.getElementById("Nation").value;

    console.log(elem1);
    console.log(elem2);
    console.log(elem3);
    console.log(elem4);
    console.log(elem5);
    console.log(elem6);
    console.log(elem7);
    console.log(elem8);
    console.log(elem9);


    var updShopModal = { // Creo il JSON
        "idUnivocoNegozio": elem1,
        "shopNumber": elem2,
        "branchName": elem3,
        "branchLocality": elem4,
        "civicNumber": elem5,
        "city": elem6,
        "province": elem7,
        "postalCode": elem8,
        "nation": elem9
    };

    try{

        const res = await fetch(this.UrlLocalHost + "shop/updateShopById?id=" + encodeURIComponent(elem1),{
            method : 'PUT',
            headers : {
                'Content-Type':'application/json'
            },
            body : JSON.stringify(updShopModal) // converte un oggetto JavaScript in una stringa JSON.
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
 * Method => (deleteShopById) 
 * This method delete a shop
 */
async deleteShop(){
    
    // Prendo i valori dai campi di input tramite l'ID
    var elem1 = this.selectedNegID;
    var elem2 = document.getElementById("ShopN").value;
    var elem3 = document.getElementById("BranchN").value;
    var elem4 = document.getElementById("BranchL").value;
    var elem5 = document.getElementById("CivicN").value;
    var elem6 = document.getElementById("City").value;
    var elem7 = document.getElementById("Prov").value;
    var elem8 = document.getElementById("PostalC").value;
    var elem9 = document.getElementById("Nation").value;

    console.log(elem1);
    console.log(elem2);
    console.log(elem3);
    console.log(elem4);
    console.log(elem5);
    console.log(elem6);
    console.log(elem7);
    console.log(elem8);
    console.log(elem9);

    try{

        const res = await fetch(this.UrlLocalHost + "shop/deleteShopById?id=" + encodeURIComponent(elem1),{
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