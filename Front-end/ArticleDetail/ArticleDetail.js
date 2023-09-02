/**
 * Author Fabio Lo Guasto
 */
class ArticoloDettaglio extends Articolo{

    /**
     * When the page is rendered, 3 API are called
     * readPricesList
     * readSizesList
     * readBrandsList
     */    
    constructor(){
        super();
        this.loadMethod();
    }
    
    // Const url from ConstArticolo.js
    urlLocHost = locHost;
    
    // Const from ConstArticolo.js
    action = actionsModal;
    
    // Variables for loadMethod() 
    loadBrand = " ";
    loadSize = " ";
    loadPrice = " ";
    
    // Variable for value of table rows    
    selectedID = 0;
    selectedCode = " ";
    selectedSize = 0;
    selectedNegozio = 0;
    selectedBrand = " ";
    selectedCategory = " ";
    selectedPrice = 0;
    selectedDiscount = 0;
    selectedSeason = " ";
    selectedSellOut = 0;
    selectedSupplier = 0;
    selectedModel = " ";
    selectedModelDescription = " ";
    scelta = "";

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
            fetch(this.urlLocHost + "article/getPrices")
            ]);
            
            this.loadBrand = await rsResponses[0].json();
            this.loadSize = await rsResponses[1].json();
            this.loadPrice = await rsResponses[2].json();
    
            this.readBrandsList(this.loadBrand);
            this.readSizesList(this.loadSize);
            this.readPricesList(this.loadPrice);
    
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
     * Method => getPrices() 
     * Populate a drop-down list with the prices provided in the `multiprices` parameter.
     * @param {*} multiprices 
     */
    readPricesList(multiprices) {
        console.log(multiprices);
    
        const dropdown = document.getElementById('selectPrice');
    
        multiprices.forEach(item => {
        console.log(item);
            const option = document.createElement('option'); 
            option.value = item.price; 
            option.text = item.price; 
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
     * Method => (getBSP) DtoGetRicercaBSP.class 
     * Search for articles based on the parameters provided
     */
    search(){

        // Recupera i valori dal form
        var table = document.getElementById("table");
        var brand = document.getElementById('selectBrands').value;
        var taglia = document.getElementById('selectSize').value;
        var prezzo = document.getElementById('selectPrice').value;
    
        var url = this.urlLocHost + "article/getBSP?brand=" + encodeURIComponent(brand) + "&size=" + encodeURIComponent(taglia);
        console.log(url);
        
        if(prezzo !== ""){
            url = url + "&price=" + encodeURIComponent(prezzo);
            console.log(url);
        }
    
        fetch(url)
            .then((response) => response.json())
            .then((data) => {

                // If there are no shops, show toast with dynamic argument
                if(data.length === 0){
                    console.log("vuoto");
                    let message = "NO SHOES WITH THIS SIZE !"; 
                    super.showToast("toastID", "toastBody", message); // Can i use this. instead of super. Don't need the instance of the parent class
                    table.classList.add('invisible'); // hide table if it's already active
                    return // the program stops and the table is not shown
                }

                console.log(data);
                this.getTable(data);
    
                /**
                 *  fter that the table is popolated, show it modify with style.display
                 *  into file.html, the table have invisibile class that makes invisible the table. Here I remove the invisible class and show the table
                 */ 
                table.classList.remove('invisible');
                
                var insertBtnModal = document.getElementById("insertBtnModal");
                insertBtnModal.style.display = "inline-block";

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
    
            // Element.___ è l'attributo della DtoGetSearchCNP.class sul backend
            //newRow.dataset.id = element.id;
            //var c1 = newRow.insertCell(0);
            //c1.innerHTML = element.id;
            var c0 = newRow.insertCell(0); // Selected the cella
            c0.innerHTML = element.code; // Insert into the cella
            var c1 = newRow.insertCell(1);
            c1.innerHTML = element.size;
            var c2 = newRow.insertCell(2);
            c2.innerHTML = element.negozio;
            var c3 = newRow.insertCell(3);
            c3.innerHTML = element.brand;
            var c4 = newRow.insertCell(4);
            c4.innerHTML = element.category;
            var c5 = newRow.insertCell(5);
            c5.innerHTML = element.price;
            var c6 = newRow.insertCell(6);
            c6.innerHTML = element.discount;
            var c7 = newRow.insertCell(7);
            c7.innerHTML = element.season;
            var c8 = newRow.insertCell(8);
            c8.innerHTML = element.sellOut;
            var c9 = newRow.insertCell(9);
            c9.innerHTML = element.supplier;
            var c10 = newRow.insertCell(10);
            c10.innerHTML = element.model;
            var c11 = newRow.insertCell(11);
            c11.innerHTML = element.modelDescription;
    
            newRow.addEventListener('click', function() {
    
                // Here the color of the selected row is managed
                var selectedRow = document.querySelector('.table tbody .table-active');
                if (selectedRow) {
                    selectedRow.classList.remove('table-active');
                    selectedRow.style.backgroundColor = 'white';
                }
                newRow.classList.add('table-active');
                newRow.style.backgroundColor = '#a7a7a7';
    
                this.selectedID = element.id;
                this.selectedCode = element.code;
                this.selectedSize = element.size;
                this.selectedNegozio = element.negozio;
                this.selectedBrand = element.brand;
                this.selectedCategory = element.category;
                this.selectedPrice = element.price;
                this.selectedDiscount = element.discount;
                this.selectedSeason = element.season;
                this.selectedSellOut = element.sellOut;
                this.selectedSupplier = element.supplier;
                this.selectedModel = element.model;
                this.selectedModelDescription = element.modelDescription;
    
                console.log('ID Articolo:', this.selectedID);
                console.log('Codice articolo:', this.selectedCode);
                console.log('Taglia articolo:', this.selectedSize);
                console.log('Numero negozio:', this.selectedNegozio);
                console.log('Brand:', this.selectedBrand);
                console.log('Categoria:', this.selectedCategory);
                console.log('Prezzo:', this.selectedPrice);
                console.log('Sconto:', this.selectedDiscount);
                console.log('Season:', this.selectedSeason);
                console.log('Venduto:', this.selectedSellOut);
                console.log('Fornitore:', this.selectedSupplier);
                console.log('Modello:', this.selectedModel);
                console.log('Descrizione modello:', this.selectedModelDescription);
    
                // Alla selezione della riga  mostro i pulsanti update & delete
                var updateBtnModal = document.getElementById("updateBtnModal");
                updateBtnModal.style.display = "inline-block";
    
                var deleteBtnModal = document.getElementById("deleteBtnModal");
                deleteBtnModal.style.display = "inline-block";
    
            }.bind(this));
        });
    }


    /**
     * 
     * @override
     * 
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
    
        // valori che arrivano dalla riga selezionata
        console.log(this.selectedID);
        console.log(this.selectedCode);
        console.log(this.selectedSize);
        console.log(this.selectedNegozio);
        console.log(this.selectedBrand);
        console.log(this.selectedCategory);
        console.log(this.selectedPrice);
        console.log(this.selectedDiscount);
        console.log(this.selectedSeason);
        console.log(this.selectedSellOut);
        console.log(this.selectedSupplier);
        console.log(this.selectedModel);
        console.log(this.selectedModelDescription);
    
        // Seleziono i campi di input che voglio riempire 
        // var modID = document.forms.modal.ShopID;
        var modCode = document.getElementById("CodMod");
        var modSize = document.getElementById("SizeMod");
        var modShop = document.getElementById("ShopMod");
        var modBrand = document.getElementById("BrandMod");
        var modCategory = document.getElementById("CatMod");
        var modPrice = document.getElementById("PricMod");
        var modDiscount = document.getElementById("DiscMod");
        var modSeason = document.getElementById("SeasMod");
        var modSell = document.getElementById("SellMod");
        var modSupplier = document.getElementById("SupMod");
        var modModel = document.getElementById("ModelMod");
        var modModelDescr = document.getElementById("ModelDescrMod");
    
        // Variables to set the button name and the modal title dynamically 
        var actionButton = document.getElementById("action");
        var modalTitle = document.getElementById("modalTitle");
    
        switch (value) {
            case this.action.INSERT:
    
                actionButton.textContent = "INSERT";
                modalTitle.textContent = "INSERT NEW SHOP";
    
                // Insert value of the input field
                modCode.value = "";
                modSize.value = 0;
                modShop.value = 0;
                modBrand.value = "";
                modCategory.value = "";
                modPrice.value = 0;
                modDiscount.value = 0;
                modSeason.value = "";
                modSell.value = 0;
                modSupplier.value = 0;
                modModel.value = "";
                modModelDescr.value = "";
                this.scelta = this.action.INSERT;
                break;
    
            case this.action.UPDATE:
    
                actionButton.textContent = "UPDATE";
                modalTitle.textContent = "UPDATE THE SHOP";
    
                // Inserisco i valori nei campi di input
                modCode.value = this.selectedCode;
                modSize.value = this.selectedSize;
                modShop.value = this.selectedNegozio;
                modBrand.value = this.selectedBrand;
                modCategory.value = this.selectedCategory;
                modPrice.value = this.selectedPrice;
                modDiscount.value = this.selectedDiscount;
                modSeason.value = this.selectedSeason;
                modSell.value = this.selectedSellOut;
                modSupplier.value = this.selectedSupplier;
                modModel.value = this.selectedModel;
                modModelDescr.value = this.selectedModelDescription;
                this.scelta = this.action.UPDATE;
                break;
    
            case this.action.DELETE:
    
                actionButton.textContent = "DELETE";
                modalTitle.textContent = "DELETE THE SHOP";
    
                // Inserisco i valori nei campi di input
                modCode.value = this.selectedCode;
                modSize.value = this.selectedSize;
                modShop.value = this.selectedNegozio;
                modBrand.value = this.selectedBrand;
                modCategory.value = this.selectedCategory;
                modPrice.value = this.selectedPrice;
                modDiscount.value = this.selectedDiscount;
                modSeason.value = this.selectedSeason;
                modSell.value = this.selectedSellOut;
                modSupplier.value = this.selectedSupplier;
                modModel.value = this.selectedModel;
                modModelDescr.value = this.selectedModelDescription;
                this.scelta = this.action.DELETE;
    
                break;
        }
    
    }
    
    
    /**
     * This metod call other method in base of value of var scelta.
     */
    doAction() {
    
        switch (this.scelta) {
            case this.action.INSERT:
                this.newArticle();
                break;
            case this.action.UPDATE:
                this.UpdateArticle();
                break;
            case this.action.DELETE:
                this.deleteArticle();
                break;
        }
    }
    
    /**
     * Method =>(insertArticle)
     * Insert a new article
     * 
     */
    async newArticle(){
        
        var article = {
            "code":                         document.getElementById('CodMod').value,
            "size":                         document.getElementById('SizeMod').value,
            "negozioId": 
                    {"idUnivocoNegozio" :   document.getElementById('ShopMod').value},
            "brand":                        document.getElementById('BrandMod').value,
            "category":                     document.getElementById('CatMod').value,
            "price":                        document.getElementById('PricMod').value,
            "discount":                     document.getElementById('DiscMod').value,
            "season":                       document.getElementById('SeasMod').value,
            "sellOut":                      document.getElementById('SellMod').value,
            "supplierId":
                {"idFornitore" :            document.getElementById('SupMod').value},
            "transactionId" :               null,
            "model":                        document.getElementById('ModelMod').value,
            "modelDescription":             document.getElementById('ModelDescrMod').value
        };
    
        console.log(this.urlLocHost + "article/insertArticle");
    
        try{
            const res = await fetch(this.urlLocHost + "article/insertArticle",{
                method : 'POST',
                headers : {
                    'Content-Type':'application/json'
                },
                body : JSON.stringify(article)
            });
    
            if (res.ok) {
                // show popup after insert
                alert('Inserimento avvenuto con successo!');
            } else {
                console.error('Errore durante l\'inserimento del record.');
            }
    
        }catch (e){
            console.log("Si è verifica un errore !" + e);
        }
    
    }
    
    /**
     * Method =>(updateArticleById)
     * Update article
     */ 
    async UpdateArticle(){ 
        
        // Prendo i valori dai campi di input tramite l'ID
        var elem1 = this.selectedID;
        var elem2 = document.getElementById("CodMod").value;
        var elem3 = document.getElementById("SizeMod").value;
        var elem4 = document.getElementById("ShopMod").value;
        var elem5 = document.getElementById("BrandMod").value;
        var elem6 = document.getElementById("CatMod").value;
        var elem7 = document.getElementById("PricMod").value;
        var elem8 = document.getElementById("DiscMod").value;
        var elem9 = document.getElementById("SeasMod").value;
        var elem10 = document.getElementById("SellMod").value;
        var elem11 = document.getElementById("SupMod").value;
        var elem12 = document.getElementById("ModelMod").value;
        var elem13 = document.getElementById("ModelDescrMod").value;
    
        console.log(elem1);
        console.log(elem2);
        console.log(elem3);
        console.log(elem4);
        console.log(elem5);
        console.log(elem6);
        console.log(elem7);
        console.log(elem8);
        console.log(elem9);
        console.log(elem10);
        console.log(elem11);
        console.log(elem12);
        console.log(elem13);
    
        // Create JSON
        var updShop = { 
            "idUnivocoNegozio":             this.selectedID,
            "code":                         document.getElementById("CodMod").value,
            "size":                         document.getElementById("SizeMod").value,
            "negozioId": 
                    {"idUnivocoNegozio" :   document.getElementById('ShopMod').value},
            "brand":                        document.getElementById('BrandMod').value,
            "category":                     document.getElementById('CatMod').value,
            "price":                        document.getElementById('PricMod').value,
            "discount":                     document.getElementById('DiscMod').value,
            "season":                       document.getElementById('SeasMod').value,
            "sellOut":                      document.getElementById('SellMod').value,
            "supplierId":
                {"idFornitore" :            document.getElementById('SupMod').value},
            "model":                        document.getElementById('ModelMod').value,
            "modelDescription":             document.getElementById('ModelDescrMod').value
        };
    
        try{
    
            const res = await fetch(this.urlLocHost + "article/updateArticleById/" + encodeURIComponent(elem1),{
                method : 'PUT',
                headers : {
                    'Content-Type':'application/json'
                },
                body : JSON.stringify(updShop) // converte un oggetto JavaScript in una stringa JSON.
            });
    
    
            if (res.ok) {
                // show popup after update
                alert('Aggiornamento avvenuto con successo!');
            } else {
                console.error('Errore durante l\'inserimento del record.');
            }
    
        }catch (e){
            console.log("Si è verifica un errore !" + e);
        }
    
    }
    
    /**
     * Method =>(deleteArticleById)
     * Delete article
     */
    async deleteArticle(){
        
        // Prendo i valori dai campi di input tramite l'ID
        var elem1 = this.selectedID;
        var elem2 = document.getElementById("CodMod").value;
        var elem3 = document.getElementById("SizeMod").value;
        var elem4 = document.getElementById("ShopMod").value;
        var elem5 = document.getElementById("BrandMod").value;
        var elem6 = document.getElementById("CatMod").value;
        var elem7 = document.getElementById("PricMod").value;
        var elem8 = document.getElementById("DiscMod").value;
        var elem9 = document.getElementById("SeasMod").value;
        var elem10 = document.getElementById("SellMod").value;
        var elem11 = document.getElementById("SupMod").value;
        var elem12 = document.getElementById("ModelMod").value;
        var elem13 = document.getElementById("ModelDescrMod").value;
    
        console.log(elem1);
        console.log(elem2);
        console.log(elem3);
        console.log(elem4);
        console.log(elem5);
        console.log(elem6);
        console.log(elem7);
        console.log(elem8);
        console.log(elem9);
        console.log(elem10);
        console.log(elem11);
        console.log(elem12);
        console.log(elem13);
    
        console.log(this.urlLocHost + "article/deleteArticleById/" + encodeURIComponent(elem1));
        try{
            const res = await fetch(this.urlLocHost + "article/deleteArticleById/" + encodeURIComponent(elem1),{
                method : 'DELETE'});
    
            if (res.ok) {
                 // show popup after delete
                alert('cancellazione avvenuto con successo!'); 
            } else {
                console.error('Errore durante l\'inserimento del record.');
            }
    
        } catch(e){
            console.log("Si è verifica un errore !" + e);
        }
    
    }
    
    
    
}

