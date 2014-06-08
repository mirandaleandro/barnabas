$(document).ready(function() {
  //  $('.data-table').dataTable();
    var Barnabas =function (){

       this.updateDataTable = function (selector)
        {
            $(selector).dataTable();
        };

        this.createRichTextArea = function (selector, options) {
            $(selector).summernote(options);
            $(selector).next().find(".note-toolbar").append($(selector +' .appendable-btn-group'));

        };

        this.setSlider = function()
        {
            $(".vote-slider").slider({
                range: "min",
                min: 0,
                max: 100,
                value: 60,
                animate: true
            });
        }


    }

    window.barnabas = new Barnabas();

    barnabas.setSlider();

    barnabas.createRichTextArea('.additional-feedback-rich-textarea',{height:200});

    barnabas.createRichTextArea('.submit-idea-rich-textarea',{height: 400});



});