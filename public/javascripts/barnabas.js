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

        this.submitIdea = {
            setup: function()
            {
                barnabas.submitIdea.configureTextEditor();
                $(document.body).on("submit",".submit-idea",barnabas.submitIdea.submitForm)
            },
            configureTextEditor: function()
            {
                var selector = '.submit-idea-rich-textarea';

                $(selector).summernote({
                    height: 400,
                    onImageUpload: function(files, editor, welEditable) {
                        barnabas.sendFile(files[0], editor, welEditable);
                    }
                });

                $(selector).next().find(".note-toolbar").append($(selector +' .appendable-btn-group'));
            },
            submitForm: function(e){
                debugger;
                e.preventDefault();

                var form = $(".submit-idea-form");

                var summernoteTextarea = $('.submit-idea-form .submit-idea-rich-textarea');
                summernoteTextarea.html(summernoteTextarea.code());

                var data = form.serialize();

                $.ajax({
                    type: "POST",
                    url: form.attr("action"),
                    data: data,
                    success: function()
                    {
                        alert("haaa!!! pegadinha do malandro");
                        barnabas.clearForms(".submit-idea-form");
                    }
                });
            }

        }

        this.sendFile = function (file, editor, welEditable) {

            data = new FormData();

            data.append("file", file);
            $.ajax({
                data: data,
                type: "POST",
                url: "/summernote/uploadImage",
                cache: false,
                contentType: false,
                processData: false,
                success: function(url) {
                    editor.insertImage(welEditable, url);
                }
            });
        }

        this.clearForms = function(selector)
        {
            var forms = $(selector)

            forms.find("input[type=text], textarea").val("");
            forms.find(".summernote").code("");

            //TODO uncheck checkboxes
        }


    }

    window.barnabas = new Barnabas();

    barnabas.setSlider();

    barnabas.createRichTextArea('.additional-feedback-rich-textarea',{height:200});

    barnabas.ideEvaluation.setup();
    barnabas.submitIdea.setup();

    console.log("loading barnabas");

});