$(document).ready(function() {
  //  $('.data-table').dataTable();
    var Barnabas =function (){

       this.updateDataTable = function (selector)
        {
            $(selector).dataTable();
        };

        this.createRichTextArea = function (selector, options) {
            $(selector).summernote(options);
        };

    }

    window.barnabas = new Barnabas();

});