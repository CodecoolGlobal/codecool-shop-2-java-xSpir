
function sendAjax(name) {
    fetch('/testServlet')
        .then(response => response.json())
        .then(data => products(data,name));


    function products(data,name) {
        let tbody = document.querySelector('#reloaded-products');
        tbody.innerHTML = '';
        let contentText = '<div class="category-title">' +
            '<strong>' +
            name +
            '</strong>' +
            '    </div>'      +
            '<div id="products" class="row">'
        for (let product of data) {
            if (product.productCategory.name == name || product.supplier.name == name || name == "Every product"){
                let source = "/static/img/product_" + product.id + ".jpg"
                contentText +=
                    `
            <div className="col col-sm-12 col-md-6 col-lg-4">
            <div class="card">
                <img class="img" src=${source}>

                <div class="card-header">
                    <h4 class="card-title">${product.name}</h4>
                    <p class="card-text">${product.description}</p>
                </div>
                <div class="card-body">
                    <div class="card-text">
                        <p class="lead">${product.defaultPrice} ${product.defaultCurrency}</p>
                    </div>
                    <div class="card-text">
                        <a class="btn btn-success" href='/cart?add=${product.id}'">Add to cart</a>
                    </div>
                </div>
            </div>
        </div>`
            }
        }

        contentText += '</div>   '

        tbody.insertAdjacentHTML("afterbegin", contentText);
        // "beforebegin" | "afterbegin" | "beforeend" | "afterend";

    }
}