/**
 * Author Fabio Lo Guasto
 */
class ArticoloFind extends Articolo{

    /**
     * When the page is rendered, 3 API are called
     * readPricesList
     * readSizesList
     * readModelsList
     */    
    constructor(){
        super();
        this.loadMethod();
    }
    
    // Const url from ConstArticolo.js
    urlLocHost = locHost;
    
    // Variables for loadMethod() 
    loadBrand = " ";
    loadSize = " ";
    loadModel = " ";
    
    // Variable for value of table rows    
    selectedLocality = " ";
    selectedName = " ";
    selectedCity = " ";
    selectedProvince = " ";

    // Variables for toast
    cityShop = document.getElementById('cityShop'); 
    toastEl = document.getElementById('toastID');
    toastBody = document.getElementById('toastBody');
    
    
    /**
     * 
     * @override
     * 
     * Make 3 asynchronous calls to page rendering
     */
    async loadMethod(){
        try{
            const rsResponses = await Promise.all([ 
            fetch(this.urlLocHost + "article/getBrands"),
            fetch(this.urlLocHost + "article/getSizes"),
            fetch(this.urlLocHost + "article/getModels")
            ]);
            
            this.loadBrand = await rsResponses[0].json();
            this.loadSize = await rsResponses[1].json();
            this.loadModel = await rsResponses[2].json();
    
            this.readBrandsList(this.loadBrand);
            this.readSizesList(this.loadSize);
            this.readModelsList(this.loadModel);
    
            //Call event for activates or deactivates the search button
            const selectBrands = document.getElementById('selectBrands');
            const selectSize = document.getElementById('selectSize');
            selectBrands.addEventListener('input', this.updateSearchButtonState.bind(this));
            selectSize.addEventListener('input', this.updateSearchButtonState.bind(this));

        }catch(e){
            console.log("si è verificato un errore!");
        }
    }
    
    /**
     * Method => getBrands() 
     * Populate a drop-down list with the brands provided in the `multiBrands` parameter.
     * @param {*} multiBrands 
     */
    readBrandsList(multiBrands) {
        console.log(multiBrands);
    
        const dropdown = document.getElementById('selectBrands');
    
        multiBrands.forEach(item => {
        console.log(item);
            const option = document.createElement('option'); 
            option.value = item.brand; 
            option.text = item.brand; 
            dropdown.appendChild(option);
        });
    
    }
    
    /**
     * Method => getSizes() 
     * Populate a drop-down list with the sizes provided in the `multisizes` parameter.
     * @param {*} multisizes 
     */
    readSizesList(multisizes) {
        console.log(multisizes);
    
        const dropdown = document.getElementById('selectSize');
    
        multisizes.forEach(item => {
        console.log(item);
            const option = document.createElement('option'); 
            option.value = item.size; 
            option.text = item.size; 
            dropdown.appendChild(option);
        });
    }
    
    /**
     * Method => getModels() 
     * Populate a drop-down list with the model descriptions provided in the `multiModels` parameter.
     * @param {*} multiModels 
     */
    readModelsList(multiModels) {
        console.log(multiModels);
    
        const dropdown = document.getElementById('selectModel');
    
        multiModels.forEach(item => {
        console.log(item);
            const option = document.createElement('option'); 
            option.value = item.model; 
            option.text = item.model; 
            dropdown.appendChild(option);
        });
    }
    
    
    /**
     * 
     * @override
     * 
     * This event activates or deactivates the search button
     * This event is called in loadMethod()
     */
    updateSearchButtonState() {
        const selectBrands = document.getElementById('selectBrands');
        const selectSize = document.getElementById('selectSize');
        const btnSearch = document.getElementById('btnSearch');
    
        // The button is disabled if one of the two fields is empty
        if(selectBrands.value !== '' | selectSize.value !== ''){
            btnSearch.disabled = true;
        }
    
        // The button is active if both fields are filled in
        if(selectBrands.value !== '' && selectSize.value !== ''){
            btnSearch.disabled = false;
        }
    
    }
    
    
    /**
     * 
     * @override
     * 
     * Method => (getSearchLocCityNameProv) DtoSearchLocCityNameProv.class 
     * Search locality, name, city and province of shoe based on the parameters provided
     */
    search(){

        var toast = new bootstrap.Toast(this.toastEl); // inizializzo il toast
        
        // Take value from the form
        var table = document.getElementById("table");
        var brand = document.getElementById('selectBrands').value;
        var taglia = document.getElementById('selectSize').value;
        var modelDescription = document.getElementById('selectModel').value;
    
        var url = this.urlLocHost + "article/getSearchLocCityNameProv?brand=" + encodeURIComponent(brand) + "&taglia=" + encodeURIComponent(taglia);
        console.log(url);
        
        if(modelDescription !== ""){
            url = url + "&modelDescription=" + encodeURIComponent(modelDescription);
            console.log(url);
        }
    
        fetch(url)
            .then((response) => response.json())
            .then((data) => {

                // If there are no shops, show toast with dynamic argument
                if(data.length === 0){
                    console.log("vuoto");
                    this.toastBody.textContent = "THERE ARE NO SHOES WITH THESE CHOOSEN FIELDS !"; 
                    toast.show();
                    table.classList.add('invisible'); // hide table if it's already active
                    return // the program stops and the table is not shown
                }

                console.log(data);
                this.getTable(data);

                /**
                 *  After that the table is popolated, show it modify with style.display
                 *  into file.html, the table have invisibile class that makes invisible the table. Here I remove the invisible class and show the table
                 */ 
                table.classList.remove('invisible');

                // Deselected row if click outside the table 
                document.addEventListener('click', this.handleClickOutsideTable.bind(this));

        })
            .catch((error) => console.error(error));
    }
    
    
    /**
     * Create a table based on the value provided
     * @param {*} value 
     */
    getTable(multiRecordArticoli){
        console.log(multiRecordArticoli);
        var table = document.getElementById("table").getElementsByTagName('tbody')[0];
        
        while(table.rows.length > 0){
            table.deleteRow(0); 
        } 
    
        multiRecordArticoli.forEach(element => {
            var newRow = table.insertRow(table.length);
    
            var c0 = newRow.insertCell(0); // Selected the cella
            c0.innerHTML = element.localita; // Insert into the cella
            var c1 = newRow.insertCell(1);
            c1.innerHTML = element.name;
            var c2 = newRow.insertCell(2);
            c2.innerHTML = element.city;
            var c3 = newRow.insertCell(3);
            c3.innerHTML = element.province;
    
            newRow.addEventListener('click', function() {
    
                // Here the color of the selected row is managed
                var selectedRow = document.querySelector('.table tbody .table-active');
                if (selectedRow) {
                    selectedRow.classList.remove('table-active');
                    selectedRow.style.backgroundColor = 'white';
                }
                newRow.classList.add('table-active');
                newRow.style.backgroundColor = '#a7a7a7';

                this.selectedLocality = element.localita;
                this.selectedName = element.name;
                this.selectedCity = element.city;
                this.selectedProvince = element.province;
    
                console.log('Localita:', this.selectedLocality);
                console.log('Name:', this.selectedName);
                console.log('City:', this.selectedCity);
                console.log('Province:', this.selectedProvince);
    
            }.bind(this));

        });
    }


    /**
     * 
     * @override
     * 
     * Deselect row if click is outside the table.
     * event è necessario per avere informazioni sull'evento click. Esso contiene dettagli sull'evento stesso (x es il tipo di evento, cosa viene cliccato ecc..)
     * Quando avviene un click in qualsiasi punto handleClickOutsideTable verrà chiamato e passerà l'oggetto event come argomento.
     * event.target è una proprietà dell'oggetto evento e restituisce l'elemento specifico sul quale è stato fatto il click.
     * Il metodo cerca di capire se il click si trova all'interno della tabella o fuori tramite table.contains(event.target).
     * Se l'elemento su cui è avvenuto il click non è figlio dell'elemento della tabella, allora si presume che il click sia avvenuto al di fuori della tabella.
     * Il metodo .contains() è un metodo nativo degli oggetti del DOM e verifica se un elemento è discendente da un altro elemento(restituisce un valore booleano).
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
    
}