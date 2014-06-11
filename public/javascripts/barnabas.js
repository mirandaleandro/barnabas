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

        this.ideEvaluation = {

            setup: function()
            {
                $(document.body).on("click",".voter-thumb",function(){
                    barnabas.ideEvaluation.displayAdditionalCommentsTextBox();
                });
            },
            displayAdditionalCommentsTextBox: function()
            {
                $(".additional-comments").slideDown();
            }
        }


    }

    window.barnabas = new Barnabas();

    barnabas.setSlider();

    barnabas.createRichTextArea('.additional-feedback-rich-textarea',{height:200});

    barnabas.createRichTextArea('.submit-idea-rich-textarea',{height: 400});

    barnabas.ideEvaluation.setup();

});