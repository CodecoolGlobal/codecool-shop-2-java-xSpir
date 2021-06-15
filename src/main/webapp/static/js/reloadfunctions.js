function sendAjax() {
    fetch('/testServlet')
        .then(response => response.json())
        .then(data => products(data));

    function products(data) {
        let tbody = document.querySelector('#tbody');
        tbody.innerHTML = '';
        let contentText = '';
        for (let genre of data) {
            contentText +=
                `
      <tr>
        <td>${genre.name}</td>
        <td count="${genre.count}">${genre.count}</td>
      </tr>`
        }

        tbody.insertAdjacentHTML("beforeend", contentText);


    }
}
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