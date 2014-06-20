$(document).ready(function() {
  //  $('.data-table').dataTable();
    var Barnabas =function (){


        this.updateDataTable = function (selector)
        {
            $(selector).dataTable();
        };

        this.createRichTextArea = function (selector, options) {

            var summerText = $(selector);

            summerText.summernote(options);

            var buttons = summerText.closest(".summernote").find(".appendable-btn-group");

            summerText.next().find(".note-toolbar").append(buttons);

        };

        this.dashboard = {
            setup: function(){
            }
        }

        this.getInspired = {
            setup:function(){
                barnabas.getInspired.setupTagCloud();
                barnabas.getInspired.setupTables();
            },
            setupTagCloud: function(){

                var topics = $('.topics-tagcloud a');

                if(topics.size() > 0){

                    topics.tagcloud({
                        size: {start: 11, end: 28, unit: 'pt'},
                        color: {start: '#F39C12', end: '#34495E'}
                    });
                }
            },
            setupTables: function(){
              //  barnabas.updateDataTable(".explore-hot-idea .explore-researcher .explore-research");
            }
        }

        this.ideaEvaluation = {

            setup: function()
            {
                barnabas.createRichTextArea('.additional-feedback-rich-textarea',{height:200});

                $(document.body).on("click",".submit-add-resources-button",function(){
                    barnabas.ideaEvaluation.processResourceForm($(".add-resource-form"));
                });

                $(document.body).on("click",".resource-vote a",barnabas.ideaEvaluation.likeResource);

                $(document.body).on("click",".voter-thumb",barnabas.ideaEvaluation.processVote);

                $(document.body).on("click",".collaborate-action button, .follow-action button",barnabas.ideaEvaluation.followOrCollaborate);

                $(document.body).on("submit",".submit-discussion-form", function(e){
                    e.preventDefault();
                });
                $(document.body).on("click",".submit-discussion", function(){
                    var clickedButton = $(this);
                    var form = clickedButton.closest("form");
                    var anonymous = form.find(".anonymous-input");
                    anonymous.val(clickedButton.data("anonymous"));
                              debugger;
                    barnabas.ideaEvaluation.submitDiscussion($(".submit-discussion-form"));

                });

                $(document.body).on("submit",".add-resource-form", function(e){
                    e.preventDefault();
                });

                barnabas.ideaEvaluation.setSlider();

            },
            submitDiscussion: function(form){
                var summernoteTextarea = $('.additional-feedback-rich-textarea');
                summernoteTextarea.html(summernoteTextarea.code());

                $.ajax({
                    type:"POST",
                    url: "/forms/updateDiscussion",
                    data: form.serialize(),
                    success: function (data)
                    {
                        barnabas.displayMessage("The comment was updated successfully","success");
                    },
                    error: function (data)
                    {
                        debugger;
                        barnabas.displayDefaultErrorMessage();
                    }
                });

            },
            followOrCollaborate: function(){
                var clickedButton = $(this);
                var actionContainer = clickedButton.closest(".relationship-action");
                var url = actionContainer.data("link");

                $.ajax({
                    url:url,
                    success: function(data)
                    {
                        actionContainer.replaceWith(data);
                    },
                    error: function(data) {
                        barnabas.displayDefaultErrorMessage();
                    }
                });

            },
            processResourceForm: function(form){

                $.ajax({
                    type:"POST",
                    url: "/forms/addResource",
                    data: form.serialize(),
                    success: function (data)
                    {
                        $(".ideas-table").replaceWith(data);
                        barnabas.displayMessage("The resource was added successfully","success");
                    },
                    error: function (data)
                    {
                        barnabas.displayDefaultErrorMessage();
                    }
                });
            },
            likeResource: function(){
                var clickedButton = $(this);
                var resourceVote = clickedButton.closest(".resource-vote");
                var ideaResourceId = resourceVote.data("idea-resource-id");

                var requestParams = {ideaResourceId:ideaResourceId }

                $.ajax({
                    data: requestParams,
                    type: "POST",
                    url: "/forms/likeResource",
                    success: function(data)
                    {
                        $("td[data-idea-resource-id='" + ideaResourceId +"']").replaceWith(data);
                        barnabas.displayMessage("Thanks for your vote");
                    },
                    error: function(data) {
                        barnabas.displayDefaultErrorMessage();
                    }
                });

            },
            processVote: function(){
                var voteThumb = $(this);
                var voteThumbContainer = voteThumb.closest(".vote-thumb-container");
                var votePool = voteThumb.closest(".vote-pool");
                var url = voteThumb.data("link");

                if(voteThumbContainer.hasClass("vote"))
                {
                   barnabas.displayErrorMessage("Error! You can't undo your vote.");
                }
                else
                {
                    $.ajax({
                        url:url,
                        success: function(data)
                        {
                            votePool.replaceWith(data);
                            barnabas.ideaEvaluation.setSlider();
                        },
                        error: function(data) {
                            barnabas.displayDefaultErrorMessage();
                        }
                    });
                }
            },
            setSlider: function()
            {
                var slider = $(".vote-slider")

                var sliderValue = slider.data("value");

                slider.slider({
                    range: "min",
                    min: 0,
                    max: 100,
                    value: sliderValue,
                    animate: true
                });
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

                $('.idea-title').maxlength({
                    threshold: 100,
                    warningClass: "label green",
                    limitReachedClass: "label orange"
                });
            },
            configureTextEditor: function()
            {
                var selector = '.submit-idea-rich-textarea';

                barnabas.createRichTextArea(selector,
                {
                    height: 400,
                    onImageUpload: function(files, editor, welEditable) {
                        barnabas.sendFile(files[0], editor, welEditable);
                    }
                });

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

        this.search = {

            setup: function(){
                barnabas.search.setupPagination(".search-results-pagination");
            },
            setupPagination:function(selector){
//                ul class="search-results-pagination" data-start="3" data-visible="5" data-href="/bolhas" data-total-pages="15" ></ul>
                var paginationElement = $(selector);
                if(paginationElement.size() > 0)
                {
                    var start = paginationElement.data("start");
                    var visibleElements = paginationElement.data("visible");
                    var href = paginationElement.data("href");
                    var totalPages = paginationElement.data("total-pages");

                    paginationElement.twbsPagination({
                        totalPages: totalPages,
                        visiblePages: visibleElements,
                        href: href,
                        startPage:start
                    });
                }

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

        this.displayDefaultErrorMessage = function(){
            barnabas.displayErrorMessage("Oh snap! We could not process your request. If the problem persists, please contact the administrator");
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

            barnabas.updateDataTable(".data-table");

            barnabas.search.setup();
            barnabas.ideaEvaluation.setup();
            barnabas.submitIdea.setup();
            barnabas.getInspired.setup();
        }
    }

    window.barnabas = new Barnabas();

    barnabas.setup();

    console.log("loading barnabas");


    window.teste = function(s,f)
    {

        var adjustment = 3-s > 0 ? 3-s : 0;

        for(i = s-2; i < Math.min(s+3+adjustment, f) ; i++){
            if(i > 0)
            {
                if(i == s)
                {
                    console.log(i + " - I'm s");
                }else
                {
                    console.log(i);
                }
            }
        }
    }
});