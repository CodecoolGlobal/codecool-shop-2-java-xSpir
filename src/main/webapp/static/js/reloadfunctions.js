$("button").click(function() {
    var fired_button = $(this).val();
    alert(fired_button);
});

function test(parameter) {
    console.log(parameter)
        alert(parameter)
}

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
            console.log(product)
            console.log(name)
            console.log(product.supplier)
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
                        <a class="btn btn-success" href='/cart?add='+ ${product.id}">Add to cart</a>
                    </div>
                </div>
            </div>
        </div>
`
            }
        }

        contentText += '</div>   '

        tbody.insertAdjacentHTML("afterbegin", contentText);
        // "beforebegin" | "afterbegin" | "beforeend" | "afterend";

    }
}

// <div className="col col-sm-12 col-md-6 col-lg-4">
//     <div className="card">
//         <img className="" src="/static/img/product_1.jpg"/>
//
//         <div className="card-header">
//             <h4 className="card-title">${product.name}</h4>
//             <p className="card-text">${product.description}</p>
//         </div>
//         <div className="card-body">
//             <div className="card-text">
//                 <p className="lead">${product.defaultPrice}</p>
//             </div>
//             <div className="card-text">
//                 <a className="btn btn-success" href='/cart?add='+ ${product.id}">Add to cart</a>
//                     </div>
//                 </div>
//             </div>
//            </div>
//          </div>
//          </div>

// <td>${product.supplier}</td>
// <td count="${product.description}">${product.description}</td>

// <tr>
//     <td>${product.name}</td>
//     <td>${product.id}</td>
//     <td>${product.description}</td>
//     <td>${product.supplier.name}</td>
//     <td>${product.defaultPrice}</td>
//     <td>${product.productCategory.name}</td>


    //     // get inputs
//     var product = new Object();
//     product.id = $('#id').val();
//     console.log($('#id').val())
//     product.name = $('#name').val();
//     product.defaultPrice = $('#defaultPrice').val();
//     product.currencyString = $('#currencyString').val();
//     product.description = $('#description').val();
//     product.productCategory = $('#productCategory').val();
//     product.supplier = $('#supplier').val();
//
//
//         $.ajax({
//         url: "jsonservlet",
//         type: 'POST',
//         dataType: 'json',
//         data: JSON.stringify(product),
//         contentType: 'application/json',
//         mimeType: 'application/json',
//
//         success: function (data) {
//             $("tr:has(td)").remove();
//
//             $.each(data, function (index, product) {
//
//                 $("#added-articles").append($('<p>dfséfjsdkfséfdsjéfsfséj</p>')
//
//                 );
//
//                 // var td_categories = $("<td/>");
//                 // $.each(article.categories, function (i, tag) {
//                 //     var span = $("<span class='label label-info'
//                 //     style='margin:4px;padding:4px' />");
//                 //
//                 //     span.text(tag);
//                 //     td_categories.append(span);
//                 // });
//                 //
//                 // var td_tags = $("<td/>");
//                 // $.each(article.tags, function (i, tag) {
//                 //     var span = $("<span class='label'
//                 //     style='margin:4px;padding:4px' />");
//                 //     span.text(tag);
//                 //     td_tags.append(span);
//                 // });
//                 //
//                 // $("#added-articles").append($('<tr/>')
//                 //     .append($('<td/>')
//                 //         .html("<a href='"+article.url+"'>"+article.title+"</a>"))
//                 //     .append(td_categories)
//                 //     .append(td_tags)
//                 // );
//
//
//             });
//         },
//         error:function(data,status,er) {
//             alert("error: "+data+" status: "+status+" er:"+er);
//         }
//     });
// }