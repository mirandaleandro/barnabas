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

                $(document.body).on("click",".add-topic-button",function(){
                     barnabas.submitIdea.addTopic( $(".add-topic-input").val());
                });

                $(document.body).on("keypress",".add-topic-input",function (e) {
                    if (e.which == 13) {
                        $(".add-topic-button").trigger("click");
                        return false;
                    }
                });

                $(".add-topic-input").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: "/topicsList",
                            data: request,
                            success: function (data)
                            {
                                var topics = barnabas.submitIdea.topicsInForm().get();
                                data = data.diff(topics);
                                response(data);
                            },
                            error: function () {
                                response([]);
                            }
                        });},

                    minLength: 1,

                    response: function( event, ui ) { },

                    select: function( event, ui )
                    {
                        barnabas.submitIdea.addTopic(ui.item.value);
                    }
                });
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
                        barnabas.displayMessage("Idea submitted successfully.","success");
                        barnabas.clearForms(".submit-idea-form");
                    }
                });
            },
            addTopic: function(topicValue)
            {
                var placeholder = $(".topic-checkbox.topic-placeholder");
                var newCheckbox = placeholder.clone();
                var checkBoxesCount = $(".topic-checkbox").size();

                $(".checkboxes").append(newCheckbox);
                newCheckbox.removeClass("topic-placeholder");
                newCheckbox.find("label").append(topicValue);
                newCheckbox.find("input").val(topicValue);
                newCheckbox.find("input").attr("name","topics["+checkBoxesCount+"]");

                $(".add-topic-input").val("");
            },
            topicsInForm: function(){
                return $(".checkboxes input").map(function(){ return $(this).val(); })
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
        }

        this.setup = function()
        {
            Array.prototype.diff = function(a) {
                return this.filter(function(i) {return a.indexOf(i) < 0;});
            };

           Messenger.options = {
               extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
               theme: 'flat'
           }
        }

        this.displayMessage = function(message,type){
            Messenger().post({
                message: message,
                type: type,
                showCloseButton: true
            });
        }

        this.displayErrorMessage = function(message){
            this.displayMessage(message,"error");
        }
    }

    window.barnabas = new Barnabas();

    barnabas.setSlider();

    barnabas.createRichTextArea('.additional-feedback-rich-textarea',{height:200});

    barnabas.ideEvaluation.setup();
    barnabas.submitIdea.setup();
    barnabas.setup();

    console.log("loading barnabas");

});